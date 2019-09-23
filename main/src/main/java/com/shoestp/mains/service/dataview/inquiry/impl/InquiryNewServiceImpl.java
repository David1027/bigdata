package com.shoestp.mains.service.dataview.inquiry.impl;

import com.google.common.hash.BloomFilter;
import com.shoestp.mains.controllers.dataview.inquiry.pojo.InquiryType;
import com.shoestp.mains.dao.dataview.inquirynew.InquiryNewRepository;
import com.shoestp.mains.entitys.dataview.inquirynew.DataViewInquiryNew;
import com.shoestp.mains.entitys.dataview.inquirynew.enums.InquiryTypeEnum;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.service.dataview.inquiry.InquiryNewService;
import com.shoestp.mains.service.dataview.inquiry.impl.pojo.ProductDataPojo;
import com.shoestp.mains.service.dataview.inquiry.impl.pojo.SupplierDataPojo;
import com.shoestp.mains.service.metadata.InquiryInfoService;
import com.shoestp.mains.service.metadata.UserInfoService;
import com.shoestp.mains.service.metadata.WebVisitInfoService;
import com.shoestp.mains.service.urlmatchdatautil.URLMatchDataUtilService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InquiryNewServiceImpl implements InquiryNewService {
  private static final Logger logger = LogManager.getLogger(InquiryNewServiceImpl.class);
  @Resource private InquiryNewRepository inquiryNewRepository;
  @Resource private InquiryInfoService meta_inquiryInfoService;
  @Resource private UserInfoService userInfoService;
  @Resource private WebVisitInfoService webVisitInfoService;
  @Resource private URLMatchDataUtilService urlMatchDataUtilService;

  /** 获取产品的 Id */
  private static Pattern productId = Pattern.compile("/[\\w-]+_p([\\d]+).html");
  /** 获取商家的 Id */
  private static Pattern supplierId = Pattern.compile(".*?\\?pkey=(\\d+)");

  /**
   * Gets inquiry date view by key word. 根据关键词及类型获取数据暂时层的 List
   *
   * @param keyword the keyword
   * @param type
   * @return the inquiry date view by key word
   * @author lijie
   * @date 2019 /09/12
   * @since *
   */
  @Override
  public Page<DataViewInquiryNew> getInquiryDateViewByKeyWordAndType(
      String keyword, Integer start, Integer limit, InquiryType type) {
    Pageable pageable = null;
    if (start != null && limit != null) {
      pageable = PageRequest.of(((start / limit)), limit);
    }
    InquiryTypeEnum _type = InquiryTypeEnum.valueOf(type.name());
    return Page.build(
        inquiryNewRepository.findAllByNameAndTypeGroupByNameAndImage(keyword, _type, pageable));
  }

  /**
   * Schedulers 统计计划任务
   *
   * @author lijie
   * @date 2019 /09/12
   * @since .
   */
  @Override
  public void schedulers() {
    /** 获取最后的统计时间 */
    LocalDateTime[] time = getLastTime(InquiryTypeEnum.SUPPLIER);
    supplier(time[0], time[1]);
    time = getLastTime(InquiryTypeEnum.PRODUCT);
    product(time[0], time[1]);
    time = getLastTime(InquiryTypeEnum.LANDING);
    landing(time[0], time[1]);
  }

  private LocalDateTime[] getLastTime(InquiryTypeEnum type) {
    Optional<DataViewInquiryNew> result =
        inquiryNewRepository.findTopByTypeOrderByStatisticalTimeDesc(type);
    LocalDateTime lastTime = DateTimeUtil.now();
    LocalDateTime startTime;
    if (result.isPresent()) {
      startTime = result.get().getStatisticalTime();
    } else {
      startTime = LocalDateTime.of(2000, 1, 1, 0, 0);
    }
    return new LocalDateTime[] {startTime, lastTime};
  }

  private Pattern landingName = Pattern.compile(".*?landing/(\\w+)/.*");

  private void landing(LocalDateTime startTime, LocalDateTime lastTime) {
    Page<WebVisitInfo> landingList =
        webVisitInfoService.getAllByPageTypeAndStartTimeAndEndTime(
            startTime, lastTime, null, null, AccessTypeEnum.LANDING);
    ConcurrentHashMap<String, SupplierDataPojo> landingMap = new ConcurrentHashMap<>();
    landingList
        .getList()
        .forEach(
            webVisitInfo -> {
              logger.debug("Landing Page Url:{}", webVisitInfo.getUri());
              Integer id = urlMatchDataUtilService.getSupplierPkeyByUrl(webVisitInfo.getUri());
              SupplierDataPojo pojo;
              if (id == 0) {
                pojo =
                    getSupplierDataPojo(landingMap.get(webVisitInfo.getTitle()), webVisitInfo, id);
              } else {
                pojo = getSupplierDataPojo(landingMap.get(id), webVisitInfo, id);
              }
              if (id == 0) {
                landingMap.put(webVisitInfo.getTitle(), pojo);
              } else {
                landingMap.put(String.valueOf(id), pojo);
              }
            });
    meta_inquiryInfoService
        .getInquiry(com.shoestp.mains.enums.inquiry.InquiryTypeEnum.LANDING, startTime, lastTime)
        .getList()
        .forEach(
            inquiryInfo -> {
              Integer id = null;
              try {
                id =
                    urlMatchDataUtilService.getSupplierPkeyByUrl(
                        new URL(inquiryInfo.getUrl()).getPath());
              } catch (MalformedURLException e) {
                e.printStackTrace();
              }
              SupplierDataPojo pojo;
              if (id == 0 || id == null) {
                Matcher matcher = landingName.matcher(inquiryInfo.getUrl());
                if (matcher.find()) {
                  pojo = landingMap.get(matcher.group(1));
                } else {
                  pojo = landingMap.get(inquiryInfo.getName());
                }
              } else {
                pojo = landingMap.get(id);
              }
              if (pojo == null) {
                pojo = SupplierDataPojo.getInstance();
                pojo.setInquiryCount(1L);
                pojo.setPv(1);
                pojo.setUv(1);
              } else {
                pojo.addUV();
                pojo.addInquiry();
                pojo.add();
              }
              if (id == 0) {
                /** TODO 2019/9/19 8:55 下午 这里最好和 user 表关联 */
                Matcher matcher = landingName.matcher(inquiryInfo.getUrl());
                if (matcher.find()) {
                  pojo.setTitle(matcher.group(1));
                } else {
                  pojo.setTitle(inquiryInfo.getName());
                }
                landingMap.put(pojo.getTitle(), pojo);
              } else {
                pojo.setTitle(userInfoService.getUserInfo(id, null).getName());
                landingMap.put(String.valueOf(id), pojo);
              }
            });
    landingMap.forEach(
        (integer, pojo) -> {
          DataViewInquiryNew dataViewInquiryNew = new DataViewInquiryNew();
          dataViewInquiryNew.setName(pojo.getTitle());
          dataViewInquiryNew.setImage(pojo.getImage());
          dataViewInquiryNew.setType(InquiryTypeEnum.LANDING);
          dataViewInquiryNew.setPv(Long.valueOf(pojo.getPv()));
          dataViewInquiryNew.setUv(Long.valueOf(pojo.getUv()));
          dataViewInquiryNew.setInquiryCount(Long.valueOf(pojo.getInquiryCount()));
          dataViewInquiryNew.setStatisticalTime(lastTime);
          inquiryNewRepository.save(dataViewInquiryNew);
        });
  }

  private void product(LocalDateTime startTime, LocalDateTime lastTime) {
    Page<WebVisitInfo> productList =
        webVisitInfoService.getAllByPageTypeAndStartTimeAndEndTime(
            startTime, lastTime, null, null, AccessTypeEnum.DETAIL);
    ConcurrentHashMap<String, ProductDataPojo> productMap = new ConcurrentHashMap<>();
    /** 产品的浏览记录 */
    productList
        .getList()
        .parallelStream()
        .forEach(
            webVisitInfo -> {
              Matcher matcher = productId.matcher(webVisitInfo.getUri());
              if (matcher.find()) {
                String id = matcher.group(1);
                logger.debug(
                    "Url:{},商品名称:{},商品 ID:{}",
                    webVisitInfo.getUri(),
                    webVisitInfo.getTitle(),
                    matcher.group(1));
                ProductDataPojo productDataPojo = productMap.get(id);
                if (productDataPojo == null) {
                  productDataPojo = ProductDataPojo.getInstance();
                }
                BloomFilter<String> filter = productDataPojo.getIpFilter();
                /** 注意这里 Ip 过滤器按照一天的周期来运行,如果定时任务,小于一天,要根据具体的 uv 规则来,所以后续可能先初始化 ip 过滤器 */
                if (!filter.mightContain(webVisitInfo.getIp())) {
                  productDataPojo.addUV();
                  filter.put(webVisitInfo.getIp());
                }
                productDataPojo.add(webVisitInfo.getTitle());
                productMap.put(id, productDataPojo);
              }
            });
    /** 得到数据 */
    logger.debug("统计产品数据:{}", productMap);
    meta_inquiryInfoService
        .getInquiry(com.shoestp.mains.enums.inquiry.InquiryTypeEnum.COMMODITY, startTime, lastTime)
        .getList()
        .parallelStream()
        .forEach(
            inquiryInfo -> {
              ProductDataPojo productDataPojo = productMap.get(inquiryInfo.getPkey());
              if (productDataPojo == null) {
                productDataPojo = ProductDataPojo.getInstance();
                productDataPojo.setTitle(inquiryInfo.getName());
              }
              if (productDataPojo.getImage() == null || productDataPojo.getImage().length() < 1) {
                productDataPojo.setImage(inquiryInfo.getImg());
              }
              productDataPojo.add();
              productDataPojo.addUV();
              productDataPojo.addInquery();
              productMap.put(String.valueOf(inquiryInfo.getPkey()), productDataPojo);
            });

    productMap.forEach(
        (s, productDataPojo) -> {
          DataViewInquiryNew dataViewInquiryNew = new DataViewInquiryNew();
          dataViewInquiryNew.setName(productDataPojo.getTitle());
          dataViewInquiryNew.setImage(productDataPojo.getImage());
          dataViewInquiryNew.setType(InquiryTypeEnum.PRODUCT);
          dataViewInquiryNew.setPv(Long.valueOf(productDataPojo.getNumber()));
          dataViewInquiryNew.setUv(Long.valueOf(productDataPojo.getUv()));
          dataViewInquiryNew.setInquiryCount(Long.valueOf(productDataPojo.getInquiryCount()));
          dataViewInquiryNew.setStatisticalTime(lastTime);
          inquiryNewRepository.save(dataViewInquiryNew);
        });
  }

  private SupplierDataPojo getSupplierDataPojo(
      SupplierDataPojo pojo, WebVisitInfo webVisitInfo, Integer id) {
    if (pojo == null) {
      pojo = SupplierDataPojo.getInstance();
      UserInfo userInfo = userInfoService.getUserInfo(Integer.valueOf(id), null);
      logger.debug("Supplier Name:{}", userInfo);
      if (id.equals(0)) {
        pojo.setTitle(webVisitInfo.getTitle());
      } else {
        pojo.setTitle(userInfo == null ? "商家 Id:" + id : userInfo.getName());
      }
      pojo.setImage(webVisitInfo.getImg());
    }
    BloomFilter<String> filter = pojo.getIpFilter();
    if (!filter.mightContain(webVisitInfo.getIp())) {
      pojo.addUV();
      filter.put(webVisitInfo.getIp());
    }
    ;
    pojo.add();
    return pojo;
  }

  private void supplier(LocalDateTime startTime, LocalDateTime lastTime) {
    /** 统计商家店铺的浏览记录 */
    Page<WebVisitInfo> infoPage =
        webVisitInfoService.getAllByPageTypeAndStartTimeAndEndTime(
            startTime, lastTime, null, null, AccessTypeEnum.SUPPLIER_PAGE);
    /** 存入的 Integer 为 Id */
    ConcurrentHashMap<String, SupplierDataPojo> supplierMap = new ConcurrentHashMap<>();
    for (WebVisitInfo webVisitInfo : infoPage.getList()) {
      Matcher matcher = supplierId.matcher(webVisitInfo.getUri());
      if (matcher.find()) {
        String id = matcher.group(1);
        logger.debug("url:{} supplier Id:{}", webVisitInfo.getUri(), id);
        SupplierDataPojo pojo =
            getSupplierDataPojo(supplierMap.get(id), webVisitInfo, Integer.valueOf(id));

        supplierMap.put(id, pojo);
      } else {
        logger.info(
            "出现属于[SUPPLIER_PAGE]类型的访问记录,取提取不到供应商 Id 的链接:{},\r\nId:{}",
            webVisitInfo.getUrl(),
            webVisitInfo.getId());
      }
    }
    logger.debug("Supplier Data:{}:", supplierMap);
    meta_inquiryInfoService
        .getInquiry(com.shoestp.mains.enums.inquiry.InquiryTypeEnum.SUPPLIER, startTime, lastTime)
        .getList()
        .parallelStream()
        .forEach(
            inquiryInfo -> {
              /** 获取所有供应商询盘 */
              UserInfo supplier = inquiryInfo.getRecipient_user();
              if (supplier == null) {
                return;
              }
              SupplierDataPojo pojo = supplierMap.get(supplier.getUserId());
              if (pojo == null) {
                pojo = SupplierDataPojo.getInstance();
                pojo.setTitle(supplier.getName());
                pojo.setPv(1);
                pojo.setUv(1);
              }
              pojo.addInquiry();
              supplierMap.put(String.valueOf(supplier.getUserId()), pojo);
            });

    supplierMap.forEach(
        (s, pojo) -> {
          DataViewInquiryNew dataViewInquiryNew = new DataViewInquiryNew();
          dataViewInquiryNew.setName(pojo.getTitle());
          dataViewInquiryNew.setImage(pojo.getImage());
          dataViewInquiryNew.setType(InquiryTypeEnum.SUPPLIER);
          dataViewInquiryNew.setPv(Long.valueOf(pojo.getPv()));
          dataViewInquiryNew.setUv(Long.valueOf(pojo.getUv()));
          dataViewInquiryNew.setInquiryCount(Long.valueOf(pojo.getInquiryCount()));
          dataViewInquiryNew.setStatisticalTime(lastTime);
          inquiryNewRepository.save(dataViewInquiryNew);
        });
  }
}

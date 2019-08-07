package com.shoestp.mains.schedulers.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.start2do.utils.iputils.City;

import com.shoestp.mains.dao.metadata.FavoriteDao;
import com.shoestp.mains.dao.metadata.UserInfoDao;
import com.shoestp.mains.dao.sellerdataview.product.ProductDao;
import com.shoestp.mains.dao.sellerdataview.supplier.SupplierDao;
import com.shoestp.mains.dao.sellerdataview.user.SellerUserDao;
import com.shoestp.mains.dao.shoestpdata.InquiryInfoDao;
import com.shoestp.mains.dao.shoestpdata.SearchDao;
import com.shoestp.mains.dao.shoestpdata.WebVisitInfoDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.SearchWordInfo;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.sellerdataview.product.SellerDataViewProduct;
import com.shoestp.mains.entitys.sellerdataview.supplier.SellerDataViewSupplier;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.schedulers.BaseSchedulers;
import com.shoestp.mains.service.metadata.CountryService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.dataview.metadata.Data;

@Component
public class SellerConver extends BaseSchedulers {
  private static final Logger logger = LogManager.getLogger(SellerConver.class);

  @PostConstruct
  public void init() {
    if (enable) {
      setScheduleBuilder(
          SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever());
      setJobNmae(SellerConver.class.getName());
    }
  }

  @Value("${shoestp.scheduler.sellerconver.enable}")
  private Boolean enable = false;
  /** * 定时60分钟 */
  @Value("${shoestp.scheduler.sellerconver.timing}")
  private int timing = 60;

  @Bean(name = "SellerConver")
  public JobDetail jobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(getJobNmae()).storeDurably().build();
  }

  @Bean(name = "SellerConverTrigger")
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail())
        .withIdentity(getJobNmae() + "Trigger")
        .withSchedule(getScheduleBuilder())
        .build();
  }

  @Autowired private InquiryInfoDao inquiryInfoDao;
  @Autowired private ProductDao pdtDao;
  @Autowired private SupplierDao supDao;
  @Autowired private SellerUserDao usrDao;
  @Autowired private WebVisitInfoDao webInfoDao;
  @Autowired private FavoriteDao favoriteDao;
  @Autowired private SearchDao searchDao;
  @Autowired private UserInfoDao userInfoDao;

  @Resource(name = "pltCountryService")
  private CountryService countryServiceImpl;

  private List<PltCountry> countryList;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      logger.info("Task Running:{}", getJobNmae());
      countryList = countryServiceImpl.getCountryList();
      Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-05-01 00:00:00");
      Date endTime = new Date();
      Date lastTime = null;
      lastTime = getLastTime(1);
      if (lastTime != null) {
        startTime = lastTime;
      }
      count(startTime, endTime, 1);

      lastTime = getLastTime(2);
      if (lastTime != null) {
        startTime = lastTime;
      }
      count(startTime, endTime, 2);

      lastTime = getLastTime(3);
      if (lastTime != null) {
        startTime = lastTime;
      }
      count(startTime, endTime, 3);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void count(Date startTime, Date endTime, int type) throws ParseException {
    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startDate = sim.parse(sim.format(startTime));
    Long timeDiffForDay = DateTimeUtil.timeDiffForDay(startTime, endTime);
    if (timeDiffForDay <= 1) {
      if (DateTimeUtil.timeDiffForHour(startTime, endTime) >= 1) {
        pdtConver(startTime, endTime);
        supplierConver(startTime, endTime);
        userConver(startTime, endTime);
      } else {
        return;
      }
    } else {
      for (int i = 1; i < timeDiffForDay; i++) {
        Date endDate = DateTimeUtil.getTimesnight(startDate);
        switch (type) {
          case 1:
            pdtConver(startDate, endDate);
            break;
          case 2:
            supplierConver(startDate, endDate);
            break;
          case 3:
            userConver(startDate, endDate);
            break;
          default:
            break;
        }
        startDate = DateTimeUtil.countDate(startDate, 5, 1);
        Long timeDiffForDay1 = DateTimeUtil.timeDiffForDay(startDate, endTime);
        if (timeDiffForDay1 <= 1) {
          switch (type) {
            case 1:
              pdtConver(startDate, endTime);
              break;
            case 2:
              supplierConver(startDate, endTime);
              break;
            case 3:
              userConver(startDate, endTime);
              break;
            default:
              break;
          }
        }
      }
    }
  }

  public Date getLastTime(int type) throws ParseException {
    switch (type) {
      case 1:
        Optional<SellerDataViewProduct> pdt = pdtDao.findTopByOrderByCreateTimeDesc();
        if (pdt.isPresent()) {
          return pdt.get().getCreateTime();
        }
        break;
      case 2:
        Optional<SellerDataViewSupplier> sup = supDao.findTopByOrderByCreateTimeDesc();
        if (sup.isPresent()) {
          return sup.get().getCreateTime();
        }
        break;
      case 3:
        Optional<SellerDataViewUser> usr = usrDao.findTopByOrderByCreateTimeDesc();
        if (usr.isPresent()) {
          return usr.get().getCreateTime();
        }
        break;
      default:
        break;
    }
    return null;
  }

  public static Integer getPdtId(String path) {
    int o = path.lastIndexOf("_p");
    int i = path.lastIndexOf(".");
    return Integer.valueOf(path.substring(o + 2, i));
  }

  public String[] getCountry(String lag, String data) {
    String[] str = {"no set", "未知"};
    if (data.equals("(not set)")) {
      return str;
    }
    for (PltCountry item : countryList) {
      if (lag.equals("en")) {
        if (data.indexOf(item.getEngName()) != -1) {
          str[0] = item.getEngName();
          str[1] = item.getName();
          return str;
        }
      } else {
        if (data.indexOf(item.getName()) != -1) {
          str[0] = item.getEngName();
          str[1] = item.getName();
          return str;
        }
      }
    }
    return str;
  }

  public String jointPdtUrl(String name, String pkey) {
    String url = "https://www.shoestp.com/" + name + "_p" + pkey + ".html";
    return url;
  }

  public static void main(String[] args) throws ParseException {
    /*String[] s = {"哈哈", "拒绝"};
    JSONArray array = new JSONArray();
    array.put("哈哈");
    array.put("嗯嗯");
    System.out.println(array.toString());*/
    /*Long timeDiffForDay =
        DateTimeUtil.timeDiffForHour(
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-05-27 14:11:00"), new Date());
    String s = DateTimeUtil.formatDateToString(new Date(), "yyyyMMddHHmm");
    System.out.println(s);*/
    /*List<Integer> l = new ArrayList<>();
    l.add(1);
    System.out.println(l.isEmpty());*/
    Map<String, Integer> m = new HashMap<>();
    m.put("ss", 1);
    e(m);
    for (Entry<String, Integer> item : m.entrySet()) {
      System.out.println(item.getKey());
    }
  }

  public static void e(Map<String, Integer> m) {
    m.put("zz", 2);
  }

  // TODO
  public void pdtConver(Date startTime, Date endTime) {
    Map<Data, SellerDataViewProduct> pdtMap = new HashMap<>();
    // 获取所有产品的浏览数
    List<Object> pdtPageViews = webInfoDao.getPdtPageViews(startTime, endTime);
    for (Object item : pdtPageViews) {
      Object[] o = (Object[]) item;
      Data d = new Data(getCountry("zh_CN", o[2].toString())[1], getPdtId(o[0].toString()));
      if (pdtMap.containsKey(d)) {
        pdtMap.get(d).setViewCount(pdtMap.get(d).getViewCount() + Integer.valueOf(o[5].toString()));
      } else {
        SellerDataViewProduct pdt = new SellerDataViewProduct();
        pdt.setProductUrl(o[0].toString());
        pdt.setProductName(o[1].toString());
        pdt.setProductImg(o[3].toString());
        pdt.setSupplierId(Integer.valueOf(o[4].toString()));
        pdt.setViewCount(Integer.valueOf(o[5].toString()));
        pdt.setInquiryCount(0);
        pdt.setVisitorCount(0);
        pdt.setCollectCount(0);
        pdtMap.put(d, pdt);
      }
    }
    // 获取所有产品的询盘数
    List<Object> pdtInquiry =
        inquiryInfoDao.getPdtInquiry(InquiryTypeEnum.COMMODITY.toString(), startTime, endTime);
    for (Object item : pdtInquiry) {
      Object[] o = (Object[]) item;
      Data d = new Data(getCountry("zh_CN", o[0].toString())[1], Integer.valueOf(o[3].toString()));
      if (pdtMap.containsKey(d)) {
        pdtMap
            .get(d)
            .setInquiryCount(pdtMap.get(d).getInquiryCount() + Integer.valueOf(o[5].toString()));
      } else {
        SellerDataViewProduct pdt = new SellerDataViewProduct();
        pdt.setProductUrl(jointPdtUrl(o[2].toString(), o[3].toString()));
        pdt.setProductName(o[2].toString());
        pdt.setProductImg(o[1].toString());
        pdt.setSupplierId(Integer.valueOf(o[4].toString()));
        pdt.setInquiryCount(Integer.valueOf(o[5].toString()));
        pdt.setVisitorCount(0);
        pdt.setViewCount(0);
        pdt.setCollectCount(0);
        pdtMap.put(d, pdt);
      }
    }
    // 获取所有商品的访问人数
    List<Object> pdtVisitorCount = webInfoDao.getPdtVisitorCount(startTime, endTime);
    for (Object item : pdtVisitorCount) {
      Object[] o = (Object[]) item;
      Data d = new Data(getCountry("zh_CN", o[2].toString())[1], getPdtId(o[0].toString()));
      if (pdtMap.containsKey(d)) {
        pdtMap.get(d).setVisitorCount(pdtMap.get(d).getVisitorCount() + 1);
      } else {
        SellerDataViewProduct pdt = new SellerDataViewProduct();
        pdt.setProductUrl(o[0].toString());
        pdt.setProductName(o[1].toString());
        pdt.setProductImg(o[3].toString());
        pdt.setSupplierId(Integer.valueOf(o[4].toString()));
        pdt.setVisitorCount(1);
        pdt.setInquiryCount(0);
        pdt.setViewCount(0);
        pdt.setCollectCount(0);
        pdtMap.put(d, pdt);
      }
    }
    // 获取所有产品的收藏
    List<Object> pdtFavorite = favoriteDao.getPdtFavorite(startTime, endTime);
    for (Object item : pdtFavorite) {
      Object[] o = (Object[]) item;
      Data d = new Data(getCountry("zh_CN", o[0].toString())[1], Integer.valueOf(o[3].toString()));
      if (pdtMap.containsKey(d)) {
        pdtMap
            .get(d)
            .setCollectCount(pdtMap.get(d).getCollectCount() + Integer.valueOf(o[5].toString()));
      } else {
        SellerDataViewProduct pdt = new SellerDataViewProduct();
        pdt.setProductUrl(jointPdtUrl(o[2].toString(), o[3].toString()));
        pdt.setProductName(o[2].toString());
        pdt.setProductImg(o[1].toString());
        pdt.setSupplierId(Integer.valueOf(o[4].toString()));
        pdt.setCollectCount(Integer.valueOf(o[5].toString()));
        pdt.setVisitorCount(0);
        pdt.setInquiryCount(0);
        pdt.setViewCount(0);
        pdtMap.put(d, pdt);
      }
    }
    List<SellerDataViewProduct> pdtList = new ArrayList<>();
    for (Entry<Data, SellerDataViewProduct> item : pdtMap.entrySet()) {
      SellerDataViewProduct pdt = item.getValue();
      pdt.setCountry(item.getKey().getKey());
      pdt.setMarketCount(pdt.getViewCount() + pdt.getVisitorCount() + pdt.getInquiryCount());
      pdt.setCreateTime(endTime);
      pdtList.add(pdt);
    }
    pdtDao.saveAll(pdtList);
  }

  public void supplierConver(Date startTime, Date endTime) {
    // 获取所有登录用户的访客数
    List<Object> visitorCount = webInfoDao.getVisitorCount(startTime, endTime);
    // 获取所有未登录用户的访客数
    List<Object> notLoginVisitorCount = webInfoDao.getNotLoginVisitorCount(startTime, endTime);
    // 获取所有登陆用户的访问量
    List<Object> pageViewsCount = webInfoDao.getPageViewsCount(startTime, endTime);
    // 获取所有未登陆用户的访问量
    List<Object> notLoginPageViewsCount = webInfoDao.getNotLoginPageViewsCount(startTime, endTime);
    // 获取所有登录用户的询盘量
    List<Object> inquiryCountGroupByCountry =
        inquiryInfoDao.getInquiryCountGroupByCountry(startTime, endTime);
    // 获取所有未登录用户的询盘量
    List<Object> notLoginInquiryCountGroupByCountry =
        inquiryInfoDao.getNotLoginInquiryCountGroupByCountry(startTime, endTime);
    // 获取登录用户的询盘人数
    List<Object> inquiryList = inquiryInfoDao.getPeopleNumGroupByCountry(startTime, endTime);
    // 获取未登录用户的询盘人数
    List<Object> notLoginInquiryList = inquiryInfoDao.getNotLoginPeopleNum(startTime, endTime);
    Map<Data, SellerDataViewSupplier> map = new HashMap<>();
    getVisitorCount(map, visitorCount, endTime);
    getVisitorCount(map, notLoginVisitorCount, endTime);
    getViewCount(map, pageViewsCount, endTime);
    getViewCount(map, notLoginPageViewsCount, endTime);
    getInquiryCount(map, inquiryCountGroupByCountry, endTime);
    getInquiryCount(map, notLoginInquiryCountGroupByCountry, endTime);
    getInquiryNumber(map, inquiryList, endTime);
    getInquiryNumber(map, notLoginInquiryList, endTime);
    List<SellerDataViewSupplier> list = new ArrayList<>();
    for (Entry<Data, SellerDataViewSupplier> item : map.entrySet()) {
      SellerDataViewSupplier sup = item.getValue();
      sup.setCountry(item.getKey().getKey());
      sup.setSupplierId(item.getKey().getNumber());
      list.add(sup);
    }
    supDao.saveAll(list);
  }

  public void getVisitorCount(
      Map<Data, SellerDataViewSupplier> map, List<Object> obj, Date endTime) {
    for (Object item : obj) {
      Object[] o = (Object[]) item;
      String country = o[0].toString();
      Integer supplier = Integer.valueOf(o[1].toString());
      Data d = new Data(getCountry("zh_CN", country)[1], supplier);
      if (map.containsKey(d)) {
        SellerDataViewSupplier sup = map.get(d);
        sup.setVisitorCount(sup.getVisitorCount() + 1);
        map.put(d, sup);
      } else {
        SellerDataViewSupplier sup = new SellerDataViewSupplier();
        sup.setVisitorCount(1);
        sup.setViewCount(0);
        sup.setInquiryCount(0);
        sup.setInquiryNumber(0);
        sup.setCreateTime(endTime);
        map.put(d, sup);
      }
    }
  }

  public void getViewCount(Map<Data, SellerDataViewSupplier> map, List<Object> obj, Date endTime) {
    for (Object item : obj) {
      Object[] o = (Object[]) item;
      String country = o[0].toString();
      Integer supplier = Integer.valueOf(o[1].toString());
      Data d = new Data(getCountry("zh_CN", country)[1], supplier);
      if (map.containsKey(d)) {
        SellerDataViewSupplier sup = map.get(d);
        sup.setViewCount(sup.getViewCount() + Integer.valueOf(o[2].toString()));
        map.put(d, sup);
      } else {
        SellerDataViewSupplier sup = new SellerDataViewSupplier();
        sup.setVisitorCount(0);
        sup.setViewCount(Integer.valueOf(o[2].toString()));
        sup.setInquiryCount(0);
        sup.setInquiryNumber(0);
        sup.setCreateTime(endTime);
        map.put(d, sup);
      }
    }
  }

  public void getInquiryCount(
      Map<Data, SellerDataViewSupplier> map, List<Object> obj, Date endTime) {
    for (Object item : obj) {
      Object[] o = (Object[]) item;
      String country = o[0].toString();
      Integer supplier = Integer.valueOf(o[1].toString());
      Data d = new Data(getCountry("zh_CN", country)[1], supplier);
      if (map.containsKey(d)) {
        SellerDataViewSupplier sup = map.get(d);
        sup.setInquiryCount(sup.getInquiryCount() + Integer.valueOf(o[2].toString()));
        map.put(d, sup);
      } else {
        SellerDataViewSupplier sup = new SellerDataViewSupplier();
        sup.setVisitorCount(0);
        sup.setViewCount(0);
        sup.setInquiryCount(Integer.valueOf(o[2].toString()));
        sup.setInquiryNumber(0);
        sup.setCreateTime(endTime);
        map.put(d, sup);
      }
    }
  }

  public void getInquiryNumber(
      Map<Data, SellerDataViewSupplier> map, List<Object> obj, Date endTime) {
    for (Object item : obj) {
      Object[] o = (Object[]) item;
      String country = o[0].toString();
      Integer supplier = Integer.valueOf(o[1].toString());
      Data d = new Data(getCountry("zh_CN", country)[1], supplier);
      if (map.containsKey(d)) {
        SellerDataViewSupplier sup = map.get(d);
        sup.setInquiryNumber(sup.getInquiryNumber() + 1);
        map.put(d, sup);
      } else {
        SellerDataViewSupplier sup = new SellerDataViewSupplier();
        sup.setVisitorCount(0);
        sup.setViewCount(0);
        sup.setInquiryCount(0);
        sup.setInquiryNumber(1);
        sup.setCreateTime(endTime);
        map.put(d, sup);
      }
    }
  }

  public void userConver(Date startTime, Date endTime) {
    List<Object> inquiry = inquiryInfoDao.getInquiryCount(startTime, endTime); // 所有登录用户的询盘数量
    List<Object> notInquiry =
        inquiryInfoDao.getNotLoginInquiryCount(startTime, endTime); // 所有未登录用户的询盘数量
    List<Data> userRelation = usrDao.getUserRelation(); // 至今访客已经访问过的商家
    List<Object> loginUserInfo = webInfoDao.getLoginUserInfo(startTime, endTime); // 所有登录用户的访问信息
    List<Object> notLoginUserInfo =
        webInfoDao.getNotLoginUserInfo(startTime, endTime); // 所有未登录用户的访问信息
    List<SearchWordInfo> searInfo =
        searchDao.findByCreateTimeBetween(startTime, endTime); // 所有访客的搜索信息
    /* List<Data> inquiryKeyword =
            inquiryInfoDao.getInquiryKeyword(
                InquiryTypeEnum.COMMODITY, startTime, endTime); // 所有用户发布商品询盘的keyword
    */
    Map<SellerDataViewUser, SellerDataViewUser> updMap = new HashMap<>();
    Map<SellerDataViewUser, SellerDataViewUser> insMap = new HashMap<>();
    if (userRelation.isEmpty()) {
      Data d = new Data("未知", 0);
      userRelation.add(d);
    }
    getInquiry(updMap, insMap, userRelation, inquiry, endTime);
    getInquiry(updMap, insMap, userRelation, notInquiry, endTime);
    getPageCount(updMap, insMap, userRelation, loginUserInfo, endTime);
    getPageCount(updMap, insMap, userRelation, notLoginUserInfo, endTime);
    for (Entry<SellerDataViewUser, SellerDataViewUser> item : updMap.entrySet()) {
      usrDao.updBySupAndSign(
          item.getKey().getSupplierId(),
          item.getKey().getSign(),
          item.getValue().getPageCount(),
          item.getValue().getInquiryCount());
    }
    List<SellerDataViewUser> sList = new ArrayList<>();
    for (Entry<SellerDataViewUser, SellerDataViewUser> item : insMap.entrySet()) {
      SellerDataViewUser value = item.getValue();
      value.setSign(item.getKey().getSign());
      value.setSupplierId(item.getKey().getSupplierId());
      sList.add(value);
    }
    usrDao.saveAll(sList);
//    for (SearchWordInfo search : searInfo) {
//      if (search.getUserId() == -1) {
        // TODO 调用修改 发送ip
//        usrDao.updBySign(search.getIp(), search.getKeyword());
//      } else {
//         TODO 调用修改 发送userId
//        usrDao.updBySign(search.getUserId().toString(), search.getKeyword());
//      }
//    }
    /* for (Data d : inquiryKeyword) {
      // TODO 调用修改 发送userId
      usrDao.updBySign(d.getNumber().toString(), d.getKey());
    }*/
  }

  public void getInquiry(
      Map<SellerDataViewUser, SellerDataViewUser> updMap,
      Map<SellerDataViewUser, SellerDataViewUser> insMap,
      List<Data> userRelation,
      List<Object> inquiry,
      Date endTime) {
    for (Object item : inquiry) {
      Object[] o = (Object[]) item;
      boolean b = false;
      for (Data d : userRelation) {
        if (Integer.valueOf(o[1].toString()).equals(d.getNumber())
            && o[2].toString().equals(d.getKey())) {
          SellerDataViewUser su = new SellerDataViewUser();
          su.setSign(d.getKey());
          su.setSupplierId(d.getNumber());
          if (updMap.containsKey(su)) {
            SellerDataViewUser vsu = updMap.get(su);
            vsu.setInquiryCount(vsu.getInquiryCount() + 1);
            updMap.put(su, vsu);
          } else {
            SellerDataViewUser vsu = new SellerDataViewUser();
            vsu.setSign(d.getKey());
            vsu.setSupplierId(d.getNumber());
            vsu.setInquiryCount(1);
            vsu.setPageCount(0);
            vsu.setKeyWords("");
            vsu.setCreateTime(endTime);
            // TODO 获取用户的国家省份姓名
            addUserField(vsu, d.getKey());
            updMap.put(su, vsu);
          }
          b = true;
        }
      }
      if (!b) {
        SellerDataViewUser su = new SellerDataViewUser();
        su.setSign(o[2].toString());
        su.setSupplierId(Integer.valueOf(o[1].toString()));
        if (insMap.containsKey(su)) {
          SellerDataViewUser vsu = insMap.get(su);
          vsu.setInquiryCount(vsu.getInquiryCount() + 1);
          insMap.put(su, vsu);
        } else {
          SellerDataViewUser vsu = new SellerDataViewUser();
          vsu.setSign(o[2].toString());
          vsu.setSupplierId(Integer.valueOf(o[1].toString()));
          vsu.setInquiryCount(1);
          vsu.setPageCount(0);
          vsu.setKeyWords("");
          vsu.setCreateTime(endTime);
          // TODO 验证用户是否存在后  获取用户的国家省份姓名
          addUserField(vsu, o[2].toString());
          insMap.put(su, vsu);
        }
      }
    }
  }

  public void getPageCount(
      Map<SellerDataViewUser, SellerDataViewUser> updMap,
      Map<SellerDataViewUser, SellerDataViewUser> insMap,
      List<Data> userRelation,
      List<Object> loginUserInfo,
      Date endTime) {
    for (Object item : loginUserInfo) {
      Object[] o = (Object[]) item;
      boolean b = false;
      for (Data d : userRelation) {
        if (Integer.valueOf(o[2].toString()).equals(d.getNumber())
            && o[0].toString().equals(d.getKey())) {
          SellerDataViewUser su = new SellerDataViewUser();
          su.setSign(d.getKey());
          su.setSupplierId(d.getNumber());
          if (updMap.containsKey(su)) {
            SellerDataViewUser vsu = updMap.get(su);
            vsu.setPageCount(vsu.getPageCount() + Integer.valueOf(o[3].toString()));
            updMap.put(su, vsu);
          } else {
            SellerDataViewUser vsu = new SellerDataViewUser();
            vsu.setSign(d.getKey());
            vsu.setSupplierId(d.getNumber());
            vsu.setPageCount(Integer.valueOf(o[3].toString()));
            vsu.setInquiryCount(0);
            vsu.setKeyWords("");
            vsu.setCreateTime(endTime);
            // TODO 获取用户的国家省份姓名
            addUserField(vsu, d.getKey());
            updMap.put(su, vsu);
          }
          b = true;
        }
      }
      if (!b) {
        SellerDataViewUser su = new SellerDataViewUser();
        su.setSign(o[0].toString());
        su.setSupplierId(Integer.valueOf(o[2].toString()));
        if (insMap.containsKey(su)) {
          SellerDataViewUser vsu = insMap.get(su);
          vsu.setPageCount(vsu.getPageCount() + Integer.valueOf(o[3].toString()));
          insMap.put(su, vsu);
        } else {
          SellerDataViewUser vsu = new SellerDataViewUser();
          vsu.setSign(o[0].toString());
          vsu.setSupplierId(Integer.valueOf(o[2].toString()));
          vsu.setPageCount(Integer.valueOf(o[3].toString()));
          vsu.setInquiryCount(0);
          vsu.setKeyWords("");
          vsu.setCreateTime(endTime);
          // TODO 验证用户是否存在后  获取用户的国家省份姓名
          addUserField(vsu, o[0].toString());
          insMap.put(su, vsu);
        }
      }
    }
  }

  public void addUserField(SellerDataViewUser vsu, String obj) {
    if (!isNumeric0(obj)) {
      String[] cp = City.find(obj);
      vsu.setCountry(cp[0]);
      if (cp[0].equals("中国")) vsu.setProvince(cp[1]);
      vsu.setVisitorName("访客" + obj);
    } else {
      UserInfo user = getUser(Integer.valueOf(obj));
      if (user == null) {
        vsu.setCountry("未知");
        vsu.setVisitorName("访客");
      } else {
//        vsu.setCountry(user.getCountry());
//        if (user.getCountry().equals("中国")) vsu.setProvince(user.getProvince());
        vsu.setVisitorName(user.getName());
      }
    }
  }

  public UserInfo getUser(Integer id) {
    Optional<UserInfo> user = userInfoDao.findByUserId(id);
    if (!user.isPresent()) {
      return null;
    } else {
      return user.get();
    }
  }

  public static boolean isNumeric0(String str) {
    for (int i = str.length(); --i >= 0; ) {
      int chr = str.charAt(i);
      if (chr < 48 || chr > 57) return false;
    }
    return true;
  }
}

package com.shoestp.mains.schedulers.seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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

import com.shoestp.mains.dao.metadata.FavoriteDao;
import com.shoestp.mains.dao.sellerdataview.product.ProductDao;
import com.shoestp.mains.dao.sellerdataview.supplier.SupplierDao;
import com.shoestp.mains.dao.sellerdataview.user.SellerUserDao;
import com.shoestp.mains.dao.shoestpdata.InquiryInfoDao;
import com.shoestp.mains.dao.shoestpdata.WebVisitInfoDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.sellerdataview.product.SellerDataViewProduct;
import com.shoestp.mains.entitys.sellerdataview.supplier.SellerDataViewSupplier;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.schedulers.BaseSchedulers;
import com.shoestp.mains.service.metadata.CountryService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.dataview.metadata.Data;

// @Component
public class SellerConver extends BaseSchedulers {

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

  @Resource(name = "pltCountryService")
  private CountryService countryServiceImpl;

  private List<PltCountry> countryList;

  protected void executeInternal1(JobExecutionContext context) throws JobExecutionException {
    /*InquiryInfo i = new InquiryInfo();
    i.setMoney(0);
    i.setKeyword("{\"keyword\":[\"呵呵\"]}");
    inquiryInfoDao.save(i);*/
    /* String[] s = {"哈哈", "拒绝"};
    List<InquiryInfo> findAll = inquiryInfoDao.findAll();
    for (InquiryInfo i : findAll) {
      JSONObject json = new JSONObject(i.getKeyword());
      JSONArray array = new JSONArray();
      array.put(s);
      System.out.println(array);
      JSONArray object = (JSONArray) json.get("keyword");
      System.out.println(object);
    }
    JSONArray array = new JSONArray();
    array.put("哈哈");
    array.put("嗯嗯");
    InquiryInfo i = new InquiryInfo();
    i.setMoney(0);
    i.setKeyword("{\"keyword\":" + array.toString() + "}");
    inquiryInfoDao.save(i);*/
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      countryList = countryServiceImpl.getCountryList();
      Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-05-01 00:00:00");
      Date endTime = new Date();
      Date lastTime = null;
      lastTime = getLastTime(1);
      if (lastTime != null) {
        startTime = lastTime;
      }
      count(startTime, endTime, 1);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void count(Date startTime, Date endTime, int type) throws ParseException {
    Long timeDiffForDay = DateTimeUtil.timeDiffForDay(startTime, endTime);
    if (timeDiffForDay <= 1) {
      if (DateTimeUtil.timeDiffForHour(startTime, endTime) >= 1) {
        pdtConver(startTime, endTime);
      } else {
        return;
      }
    } else {
      for (int i = 1; i < timeDiffForDay; i++) {
        Date endDate = DateTimeUtil.getTimesnight(startTime);
        switch (type) {
          case 1:
            pdtConver(startTime, endDate);
            break;
          default:
            break;
        }
        startTime = DateTimeUtil.countDate(startTime, 5, 1);
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
    List<Integer> l = new ArrayList<>();
    l.add(1);
    System.out.println(l.isEmpty());
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
        pdt.setViewCount(0);
        pdtMap.put(d, pdt);
      }
    }
    // 获取所有商品的访问人数
    List<Object> pdtVisitorCount = webInfoDao.getPdtVisitorCount(startTime, endTime);
    for (Object item : pdtVisitorCount) {
      Object[] o = (Object[]) item;
      Data d = new Data(getCountry("zh_CN", o[2].toString())[1], getPdtId(o[0].toString()));
      if (pdtMap.containsKey(d)) {
        pdtMap
            .get(d)
            .setVisitorCount(pdtMap.get(d).getVisitorCount() + Integer.valueOf(o[5].toString()));
      } else {
        SellerDataViewProduct pdt = new SellerDataViewProduct();
        pdt.setProductUrl(o[0].toString());
        pdt.setProductName(o[1].toString());
        pdt.setProductImg(o[3].toString());
        pdt.setSupplierId(Integer.valueOf(o[4].toString()));
        pdt.setVisitorCount(Integer.valueOf(o[5].toString()));
        pdt.setInquiryCount(0);
        pdt.setViewCount(0);
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
      pdtDao.save(pdt);
    }
    supplierConver(pdtList, startTime, endTime);
  }

  public void supplierConver(List<SellerDataViewProduct> pdtList, Date startTime, Date endTime) {
    Map<String, List<SellerDataViewProduct>> map =
        pdtList.stream().collect(Collectors.groupingBy(SellerDataViewProduct::getCountry));
    // 获取询盘人数
    List<Object> inquiryList = inquiryInfoDao.getPeopleNumGroupByCountry(startTime, endTime);
    List<Object> copyInquiry = new ArrayList<>();
    for (Object i : inquiryList) {
      copyInquiry.add(i);
    }
    Iterator<Object> it = inquiryList.iterator();
    List<SellerDataViewSupplier> sellerDataViewSupplierList = new ArrayList<>();
    for (Entry<String, List<SellerDataViewProduct>> item : map.entrySet()) {
      SellerDataViewSupplier sup = new SellerDataViewSupplier();
      sup.setCountry(item.getKey());
      Map<Integer, List<SellerDataViewProduct>> supMap =
          item.getValue().stream()
              .collect(Collectors.groupingBy(SellerDataViewProduct::getSupplierId));
      for (Entry<Integer, List<SellerDataViewProduct>> supItem : supMap.entrySet()) {
        sup.setSupplierId(supItem.getKey());
        for (SellerDataViewProduct supList : supItem.getValue()) {
          sup.setViewCount(sup.getViewCount() + supList.getViewCount());
          sup.setVisitorCount(sup.getVisitorCount() + supList.getVisitorCount());
          sup.setInquiryCount(sup.getInquiryCount() + supList.getInquiryCount());
        }
      }
      sup.setCreateTime(endTime);
      sellerDataViewSupplierList.add(sup);
    }
    for (SellerDataViewSupplier suplist : sellerDataViewSupplierList) {
      while (it.hasNext()) {
        Object[] o = (Object[]) it.next();
        if (suplist.getCountry().equals(o[0])
            && suplist.getSupplierId().equals(Integer.valueOf(o[1].toString()))) {
          suplist.setInquiryNumber(suplist.getInquiryNumber() + 1);
          it.remove();
        }
      }
    }
    for (Object item : inquiryList) {
      Object[] o = (Object[]) item;
      SellerDataViewSupplier sup = new SellerDataViewSupplier();
      sup.setCountry(o[0].toString());
      sup.setSupplierId(Integer.valueOf(o[1].toString()));
      sup.setViewCount(0);
      sup.setVisitorCount(0);
      sup.setInquiryCount(0);
      sup.setInquiryNumber(1);
      sup.setCreateTime(endTime);
      sellerDataViewSupplierList.add(sup);
    }
    supDao.saveAll(sellerDataViewSupplierList);
  }

  public void userConver(List<Object> inquiry, Date startTime, Date endTime) {
    List<Data> userRelation = usrDao.getUserRelation();
    List<Object> loginUserInfo = webInfoDao.getLoginUserInfo(startTime, endTime);
    List<Object> notLoginUserInfo = webInfoDao.getNotLoginUserInfo(startTime, endTime);
    Map<SellerDataViewUser, SellerDataViewUser> map = new HashMap<>();
    for (Object item : inquiry) {
      Object[] o = (Object[]) item;
    }
  }
}

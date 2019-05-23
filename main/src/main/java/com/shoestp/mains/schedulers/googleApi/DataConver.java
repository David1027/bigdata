package com.shoestp.mains.schedulers.googleApi;

import com.shoestp.mains.dao.dataView.flow.FlowDao;
import com.shoestp.mains.dao.dataView.flow.FlowPageDao;
import com.shoestp.mains.dao.dataView.inquiry.InquiryDao;
import com.shoestp.mains.dao.dataView.inquiry.InquiryRankDao;
import com.shoestp.mains.dao.dataView.realcountry.RealCountryDao;
import com.shoestp.mains.dao.dataView.user.UserAreaDao;
import com.shoestp.mains.dao.dataView.user.UserDao;
import com.shoestp.mains.dao.dataView.user.UserSexDao;
import com.shoestp.mains.dao.metaData.GoogleBrowseInfoDao;
import com.shoestp.mains.dao.metaData.UserInfoDao;
import com.shoestp.mains.dao.shoestpData.InquiryInfoDao;
import com.shoestp.mains.dao.shoestpData.WebVisitInfoDao;
import com.shoestp.mains.entitys.dataView.country.DataViewCountry;
import com.shoestp.mains.entitys.dataView.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataView.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataView.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.dataView.inquiry.DataViewInquiryRank;
import com.shoestp.mains.entitys.dataView.user.DataViewUser;
import com.shoestp.mains.entitys.dataView.user.DataViewUserArea;
import com.shoestp.mains.entitys.dataView.user.DataViewUserSex;
import com.shoestp.mains.entitys.metaData.GoogleBrowseInfo;
import com.shoestp.mains.entitys.metaData.InquiryInfo;
import com.shoestp.mains.entitys.metaData.PltCountry;
import com.shoestp.mains.entitys.metaData.WebVisitInfo;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import com.shoestp.mains.schedulers.BaseSchedulers;
import com.shoestp.mains.service.metaData.CountryService;
import com.shoestp.mains.views.dataView.metaData.DataView;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
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

@Component
public class DataConver extends BaseSchedulers {
  private static final Logger logger = LogManager.getLogger(DataConver.class);
  @Autowired private FlowDao flowDao;
  @Autowired private FlowPageDao flowPageDao;
  @Autowired private WebVisitInfoDao webDao;
  @Autowired private InquiryInfoDao inquiryInfoDao;
  @Autowired private InquiryDao inquiryDao;
  @Autowired private GoogleBrowseInfoDao googleDao;
  @Autowired private InquiryRankDao inquiryRankDao;
  @Autowired private UserInfoDao userInfoDao;
  @Autowired private RealCountryDao countryDao;
  @Autowired private UserDao userDao;
  @Autowired private UserAreaDao userAreaDao;
  @Autowired private UserSexDao userSexDao;

  @Resource(name = "pltCountryService")
  private CountryService countryServiceImpl;

  @Value("${shoestp.scheduler.dataconver.enable}")
  private Boolean enable = false;
  /** * 定时60分钟 */
  @Value("${shoestp.scheduler.dataconver.timing}")
  private int timing = 60;

  private Integer session;
  private List<PltCountry> countryList;

  @PostConstruct
  public void init() {
    logger.info("Task Info: Name->{} Timing->{} isRun->{}", getJobNmae(), timing, enable);
    setScheduleBuilder(
        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever());
    setJobNmae(DataConver.class.getName());
    ydms=new SimpleDateFormat("yyyyMMddHHmm");
    if (enable) {
      sourcePage.put(
          "迪盛着陆页",
          Arrays.asList(
              "/activity/html/ds/", "/m/disheng", "/activity/html/romania/disheng/index.html"));
      sourcePage.put(
          "巨纳着陆页",
          Arrays.asList("/activity/html/jn/", "/m/juna", "/activity/html/romania/juna/index.html"));
      sourcePage.put("耐瑞着陆页", Arrays.asList("/activity/html/ny/index.html"));
      sourcePage.put(
          "shoestp着陆页",
          Arrays.asList(
              "/activity/html/leatherShoes/",
              "/activity/html/shoes/",
              "/activity/html/footwear/",
              "/m/landing/vipFoot",
              "/m/landing/nomalFoot",
              "/m/landing/leather",
              "/m/landing/wmShoes"));
      // sourcePage.put("罗马尼亚相关着陆页", Arrays.asList("/activity/html/romania/"));
      sourcePage.put("爱莱发着陆页", Arrays.asList("/activity/html/romania/allaifa/index.html"));
      sourcePage.put("乔奈着陆页", Arrays.asList("/activity/html/romania/baoluopate/index.html"));
      sourcePage.put("丰盛着陆页", Arrays.asList("/activity/html/romania/fengsheng/index.html"));
      sourcePage.put("华利欧着陆页", Arrays.asList("/activity/html/romania/hualiou/index.html"));
      sourcePage.put("华友着陆页", Arrays.asList("/activity/html/romania/huayou/index.html"));
      sourcePage.put("剑鲨着陆页", Arrays.asList("/activity/html/romania/jiansha/index.html"));
      sourcePage.put("杰克巴乔着陆页", Arrays.asList("/activity/html/romania/jiekebaqiao/index.html"));
      sourcePage.put("康睿着陆页", Arrays.asList("/activity/html/romania/kangrui/index.html"));
      sourcePage.put("康益达着陆页", Arrays.asList("/activity/html/romania/kangyida/index.html"));
      sourcePage.put("千百梦着陆页", Arrays.asList("/activity/html/romania/qianbaimeng/index.html"));
      sourcePage.put("新纪元着陆页", Arrays.asList("/activity/html/romania/xinjiyuan/index.html"));
      sourcePage.put("亿力洲着陆页", Arrays.asList("/activity/html/romania/yilizhou/index.html"));
      sourcePage.put("展豪着陆页", Arrays.asList("/activity/html/romania/zhanhao/index.html"));
    }
  }

  @Bean(name = "DataConver")
  public JobDetail JobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(getJobNmae()).storeDurably().build();
  }

  @Bean(name = "DataConverTrigger")
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(JobDetail())
        .withIdentity(getJobNmae() + "Trigger")
        .withSchedule(getScheduleBuilder())
        .build();
  }

  private  SimpleDateFormat ydms ;

  public static final List<String> PC =
      Arrays.asList("(not set)", "Chrome OS", "Linux", "Macintosh", "Windows");

  private static List<String> MOBILE =
      Arrays.asList(
          "Android",
          "IOS",
          "BlackBerry",
          "Firefox OS",
          "Samsung",
          "Tizen",
          "Windows Phone",
          "Xbox");

  /** * 来源渠道 */
  private Map<String, List<String>> sourcePage = new HashMap<>();

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
      SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
      LocalDateTime date = LocalDateTime.now();
      // Date createTime = LocalDateTimeToUdate(date);
      String startTime = "20181119000000";
      long day = 0; // 天数差
      String endTime = date.format(ofPattern) + "00";
      countryList = countryServiceImpl.getCountryList();
      if (flowDao.getFlowTopOne().isPresent()) {
        // startTime = date.plusHours(-1).format(ofPattern) + "00";
        startTime = ydms.format(flowDao.getFlowTopOne().get().getCreateTime()) + "00";
        LocalDateTime finallyLocaDateTime =
            UDateToLocalDateTime(flowDao.getFlowTopOne().get().getCreateTime());
        Duration duration = Duration.between(date, finallyLocaDateTime);
        if (duration.toHours() == 0) {
          return;
        }
      } else {
        Duration duration =
            Duration.between(
                date,
                LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        day = duration.toDays();
      }
      if (day < 0) {
        // 之前的数据按天插入
        LocalDateTime startDate = LocalDateTime.parse("201811180000", ofPattern);
        LocalDateTime endDate = LocalDateTime.parse("201811182359", ofPattern);
        for (int i = 0; i >= day; i--) {
          LocalDateTime plusDays = startDate.plusDays(1);
          LocalDateTime plusDays2 = endDate.plusDays(1);
          List<GoogleBrowseInfo> browseInfoList =
              googleDao.queryByStartTimeAndEndTime(
                  plusDays.format(ofPattern), plusDays2.format(ofPattern));
          List<InquiryInfo> InquiryInfoList =
              inquiryInfoDao.findByCreateTimeBetween(
                  LocalDateTimeToUdate(startDate), LocalDateTimeToUdate(endDate));
          startDate = plusDays;
          endDate = plusDays2;
          if (i - 1 == day) {
            endDate = date;
          }
          Date s = LocalDateTimeToUdate(startDate);
          Date e = LocalDateTimeToUdate(endDate);
          if (!browseInfoList.isEmpty()) {
            filtration(browseInfoList, s, e);
          }
          if (!InquiryInfoList.isEmpty()) {
            inquiryRankConver(InquiryInfoList, s, e);
          }
          inquiryConver(s, e);
          countryConver(s, e);
          userSexConver(s, e);
        }
      } else {
        Date s = sim.parse(startTime);
        Date e = sim.parse(endTime);
        List<GoogleBrowseInfo> browseInfoList =
            googleDao.queryByStartTimeAndEndTime(startTime, endTime);
        List<InquiryInfo> InquiryInfoList = inquiryInfoDao.findByCreateTimeBetween(s, e);
        if (!browseInfoList.isEmpty()) {
          filtration(browseInfoList, s, e);
        }
        if (!InquiryInfoList.isEmpty()) {
          inquiryRankConver(InquiryInfoList, s, e);
        }
        inquiryConver(s, e);
        countryConver(s, e);
        userSexConver(s, e);
      }
    } catch (Exception e) {
      e.printStackTrace();
      // TODO
    }
  }

  public SourceTypeEnum judgeSource(String source) {
    if (source.indexOf("google") != -1) {
      // 来自google
      return SourceTypeEnum.GOOGLE;
    } else if (source.equals("(direct)")) {
      // 直接访问
      return SourceTypeEnum.INTERVIEW;
    } else if (source.indexOf("baidu") != -1) {
      // 来自百度
      return SourceTypeEnum.BAIDU;
    } else {
      // 其他
      return SourceTypeEnum.OTHER;
    }
  }

  public String judgePage(String page) {
    String pe = "自然搜索";
    for (Entry<String, List<String>> p : sourcePage.entrySet()) {
      for (int i = 0; i < p.getValue().size(); i++) {
        if (page.indexOf(p.getValue().get(i)) != -1) {
          return p.getKey();
        }
      }
    }
    return pe;
  }

  public DeviceTypeEnum judgeSystem(String system) {
    if (PC.contains(system)) {
      // PC
      return DeviceTypeEnum.PC;
    }
    if (MOBILE.contains(system)) {
      return DeviceTypeEnum.WAP;
    }
    return DeviceTypeEnum.PC;
  }

  public AccessTypeEnum judgePageType(String pagePath) {
    if (pagePath.equals("/") || pagePath.equals("/m/")) {
      // 首页
      return AccessTypeEnum.INDEX;
    } else if (pagePath.startsWith("/home/usr_UsrConsult_publishView")
        || pagePath.startsWith("/m/RFQ")) {
      // RFQ
      return AccessTypeEnum.RFQ;
    } else if ((pagePath.indexOf("_p") != -1 && pagePath.endsWith(".html"))
        || pagePath.indexOf("/m/product/prodetail") != -1) {
      // 产品详情页
      return AccessTypeEnum.DETAIL;
    } else if (pagePath.startsWith("/home/pdt_PdtProduct")
        || pagePath.indexOf("/m/product/prolist") != -1) {
      // 产品列表页
      return AccessTypeEnum.LIST;
    } else if (pagePath.startsWith("/home/usr_UsrConsult_productPublishView")
        || pagePath.startsWith("/m/product/inquery")) {
      // 商品询盘
      return AccessTypeEnum.INQUIRY;
    } else if (pagePath.startsWith("/home/rfq_RFQConsult_ExpoRivaSchuhshow")
        || pagePath.startsWith("/home/rfq_RFQConsult_exhibitionshow")
        || pagePath.startsWith("/home/rfq_RFQConsult_guangjiaohuishow")) {
      // DON‘TMISS
      return AccessTypeEnum.DONTMISS;
    } else if (pagePath.startsWith("/home/usr_UsrPurchase_userIndex")
        || pagePath.startsWith("/m/setting")) {
      // 个人中心首页
      return AccessTypeEnum.MYCENTER;
    } else {
      return AccessTypeEnum.OTHER;
    }
  }

  public Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
    ZoneId zone = ZoneId.systemDefault();
    Instant instant = localDateTime.atZone(zone).toInstant();
    return Date.from(instant);
  }

  public LocalDateTime UDateToLocalDateTime(Date date) {
    Instant instant = date.toInstant();
    ZoneId zone = ZoneId.systemDefault();
    return LocalDateTime.ofInstant(instant, zone);
  }

  /**
   * -过滤数据
   *
   * @param browseInfoList
   * @param date
   */
  public void filtration(List<GoogleBrowseInfo> browseInfoList, Date startTime, Date endTime) {
    if (!browseInfoList.isEmpty()) {
      // 非空
      Map<DataView, List<GoogleBrowseInfo>> map = new HashMap<>();
      Map<AccessTypeEnum, List<GoogleBrowseInfo>> mapPage = new HashMap<>();

      for (GoogleBrowseInfo info : browseInfoList) {
        DataView data = new DataView();
        data.setDeviceType(judgeSystem(info.getSystem()));
        data.setSourcePage(judgePage(info.getPagePath()));
        data.setSourceType(judgeSource(info.getSourcePage()));
        AccessTypeEnum accTypeEnum = judgePageType(info.getPagePath());

        if (map.containsKey(data)) {
          map.get(data).add(info);
        } else {
          List<GoogleBrowseInfo> infos = new ArrayList<>();
          infos.add(info);
          map.put(data, infos);
        }
        if (mapPage.containsKey(accTypeEnum)) {
          mapPage.get(accTypeEnum).add(info);
        } else {
          List<GoogleBrowseInfo> infos = new ArrayList<>();
          infos.add(info);
          mapPage.put(accTypeEnum, infos);
        }
      }
      flowConver(map, startTime, endTime);
      flowPageConver(mapPage, startTime, endTime);
    }
  }

  public int getClickNum(AccessTypeEnum acc, Date startTime, Date endTime) {
    if (acc.equals(AccessTypeEnum.INDEX)) {
      return webDao.getClikeNum(startTime, endTime, "/home/usr_UsrPurchase");
    } else if (acc.equals(AccessTypeEnum.RFQ)) {
      // rfq
      return webDao.getClikeNum(startTime, endTime, "/home/usr_UsrConsult_publishView");
    } else if (acc.equals(AccessTypeEnum.DETAIL)) {
      // 商品详情
      return webDao.getClikeNum(startTime, endTime, "/home/pdt_PdtProduct_gtProductsInfo");
    } else if (acc.equals(AccessTypeEnum.LIST)) {
      // 商品列表
      return webDao.getClikeNum(startTime, endTime, "/home/pdt_PdtProduct");
    } else if (acc.equals(AccessTypeEnum.INQUIRY)) {
      // 商品询盘
      return webDao.getClikeNum(startTime, endTime, "/home/usr_UsrConsult_productPublishView");
    } else if (acc.equals(AccessTypeEnum.DONTMISS)) {
      // dontmiss
      return webDao.getClikeNum(
          startTime,
          endTime,
          "/home/rfq_RFQConsult_ExpoRivaSchuhshow",
          "/home/rfq_RFQConsult_exhibitionshow",
          "/home/rfq_RFQConsult_guangjiaohuishow");
    } else if (acc.equals(AccessTypeEnum.MYCENTER)) {
      // 个人中心
      return webDao.getClikeNum(startTime, endTime, "/home/usr_UsrPurchase_userIndex");
    }
    return 0;
  }

  public int getInquiry(SourceTypeEnum souType, String source, Date startTime, Date endTime) {
    return inquiryInfoDao.queryInquiryCount(startTime, endTime, souType, source);
  }

  /**
   * -计算数量
   *
   * @param num 数量
   * @return
   */
  public int countNum(String num) {
    int i = Integer.parseInt(num);
    if (i > 1) {
      return i - 1;
    } else {
      return 0;
    }
  }

  public static int countBounce(String bounceRate, String sessions) {
    BigDecimal b = new BigDecimal(bounceRate);
    BigDecimal s = new BigDecimal(sessions);
    BigDecimal bms = b.divide(BigDecimal.valueOf(100)).multiply(s);
    int ibms = bms.intValue();
    if (bms.compareTo(BigDecimal.valueOf(ibms)) == 0) {
      return ibms;
    } else {
      ibms += 1;
      return ibms;
    }
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

  public static void main(String[] args) throws ParseException {
    //    LocalDateTime date = LocalDateTime.now();
    //    String format = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + "00";
    //    Duration duration =
    //        Duration.between(
    //            date,
    //            LocalDateTime.parse("20180101000000",
    // DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    //    System.out.println(duration.toDays());
    // System.out.println(0.5 * 2 % (int) (0.5 * 2) > 0 ? (int) (0.5 * 2) + 1 : (0.5 * 2));
    // System.out.println(Double.parseDouble("66.66666".));
    // System.out.println(countBounce("0.0", "10"));
    /*Date d = new SimpleDateFormat("yyyyMMddHHmmss").parse("20190516180300");
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d));*/
    /* Double d = 28.0;
    Double a = 32.0;
    DecimalFormat df = new DecimalFormat("#.00");
    String format = df.format(d / a);
    double parseDouble = Double.parseDouble(format);
    System.out.println(parseDouble);*/
    /* String s = "a";
    String b = "a";
    Map<String, Integer> m = new HashMap<>();
    m.put(s, 1);
    // m.
    System.out.println(m.containsKey(b));*/
    /*Map<DataViewCountry, DataViewCountry> map = new HashMap<>();
    DataViewCountry d = new DataViewCountry();
    DataViewCountry a = new DataViewCountry();
    DataViewCountry w = new DataViewCountry();
    DataViewCountry s = new DataViewCountry();
    d.setCountryEnglishName("zz");
    d.setCountryName("aa");
    a.setCountryImage("ad");
    w.setCountryEnglishName("zz");
    w.setCountryName("aa");
    s.setCountryEnglishName("zz");
    s.setCountryName("aa");
    map.put(d, a);
    map.put(w, a);
    System.out.println(map.containsKey(w));*/
    System.out.println(Integer.parseInt("2"));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // TODO
  public void countryConver(Date startTime, Date endTime) {
    Map<DataViewCountry, DataViewCountry> map = new HashMap<>();
    /* StringBuffer sql = new StringBuffer("");
    for (int i = 0; i < MOBILE.size(); i++) {
      sql.append("'");
      sql.append(MOBILE.get(i));
      sql.append("'");
      if (i != MOBILE.size() - 1) {
        sql.append(",");
      }
    }*/
    // 访客 浏览量
    List<Object> objGoogle =
        googleDao.getPageViewsAndSessionsGrupByCountrty(
            2, ydms.format(startTime), ydms.format(endTime));
    if (objGoogle.size() > 0) {
      for (Object item : objGoogle) {
        Object[] o = (Object[]) item;
        DataViewCountry country = new DataViewCountry();
        DataViewCountry count = new DataViewCountry();
        String[] getCountry = getCountry("en", (String) o[0]);
        country.setCountryEnglishName(getCountry[0]);
        country.setCountryName(getCountry[1]);
        Integer views = Double.valueOf(o[1].toString()).intValue();
        Integer sessions = Double.valueOf(o[2].toString()).intValue();
        Integer phone = 0;
        if (o[3] != null && !"null".equals(o[3])) {
          phone = Double.valueOf(o[3].toString()).intValue();
        }
        Integer pc = sessions - phone;
        count.setPageViewsCount(views);
        count.setVisitorCountPc(pc);
        count.setVisitorCountWap(phone);
        count.setVisitorCount(sessions);
        map.put(country, count);
      }
    }
    // 注册量
    List<Object> objUserInfo = userInfoDao.getCountryAndCount(startTime, endTime);
    if (objUserInfo.size() > 0) {
      for (Object item : objUserInfo) {
        Object[] o = (Object[]) item;
        String[] getCountry = getCountry("zh_CN", (String) o[0]);
        DataViewCountry country = new DataViewCountry();
        DataViewCountry count = new DataViewCountry();
        country.setCountryEnglishName(getCountry[0]);
        country.setCountryName(getCountry[1]);
        if (map.containsKey(country)) {
          count = map.get(country);
          count.setRegisterCount(Double.valueOf(o[1].toString()).intValue());
          map.put(country, count);
        } else {
          count.setPageViewsCount(0);
          count.setVisitorCountPc(0);
          count.setVisitorCountWap(0);
          count.setVisitorCount(0);
          count.setRegisterCount(Double.valueOf(o[1].toString()).intValue());
          map.put(country, count);
        }
      }
    }
    // 询盘量
    List<Object> objInquiryInfo = inquiryInfoDao.getCountGrupByCountry(startTime, endTime);
    if (objInquiryInfo.size() > 0) {
      for (Object item : objInquiryInfo) {
        Object[] o = (Object[]) item;
        String[] getCountry = getCountry("zh_CN", (String) o[0]);
        DataViewCountry country = new DataViewCountry();
        DataViewCountry count = new DataViewCountry();
        country.setCountryEnglishName(getCountry[0]);
        country.setCountryName(getCountry[1]);
        if (map.containsKey(country)) {
          count = map.get(country);
          count.setInquiryCount(Double.valueOf(o[1].toString()).intValue());
          map.put(country, count);
        } else {
          count.setPageViewsCount(0);
          count.setVisitorCountPc(0);
          count.setVisitorCountWap(0);
          count.setVisitorCount(0);
          count.setRegisterCount(0);
          count.setInquiryCount(Double.valueOf(o[1].toString()).intValue());
          map.put(country, count);
        }
      }
    }
    // RFQ数
    List<Object> objInquiryInfoRfq = inquiryInfoDao.getRfqCountGrupByCountry(startTime, endTime);
    if (objInquiryInfoRfq.size() > 0) {
      for (Object item : objInquiryInfoRfq) {
        Object[] o = (Object[]) item;
        String[] getCountry = getCountry("zh_CN", (String) o[0]);
        DataViewCountry country = new DataViewCountry();
        DataViewCountry count = new DataViewCountry();
        country.setCountryEnglishName(getCountry[0]);
        country.setCountryName(getCountry[1]);
        if (map.containsKey(country)) {
          count = map.get(country);
          count.setRfqCount(Double.valueOf(o[1].toString()).intValue());
          map.put(country, count);
        } else {
          count.setPageViewsCount(0);
          count.setVisitorCountPc(0);
          count.setVisitorCountWap(0);
          count.setVisitorCount(0);
          count.setRegisterCount(0);
          count.setInquiryCount(0);
          count.setRfqCount(Double.valueOf(o[1].toString()).intValue());
          map.put(country, count);
        }
      }
    }

    Integer visitorCount = 0;
    Integer viewCount = 0;
    Integer registerCount = 0;
    Integer inquiryCount = 0;
    Integer rfqCount = 0;

    for (Entry<DataViewCountry, DataViewCountry> item : map.entrySet()) {
      DataViewCountry key = item.getKey();
      DataViewCountry value = item.getValue();
      value.setCountryEnglishName(key.getCountryEnglishName());
      value.setCountryName(key.getCountryName());
      int c =
          (int) userInfoDao.countByCountryLikeAndCreateTimeLessThan(key.getCountryName(), endTime);
      value.setUserCount(c);
      value.setCreateTime(endTime);
      visitorCount += value.getVisitorCount();
      // viewCount += value.getPageViewsCount();
      registerCount += value.getRegisterCount();
      // inquiryCount += value.getInquiryCount();
      // rfqCount += value.getRfqCount();
      countryDao.save(value);
      userConver(visitorCount, registerCount, startTime, endTime);
      userAreaConver(key.getCountryName(), c, startTime, endTime);
    }
  }

  public void flowConver(Map<DataView, List<GoogleBrowseInfo>> map, Date startTime, Date endTime) {
    for (Entry<DataView, List<GoogleBrowseInfo>> entry : map.entrySet()) {
      DataView t = entry.getKey();
      List<GoogleBrowseInfo> infos = entry.getValue();
      Long df =
          infos.stream()
              .filter(
                  bean -> {
                    if (Integer.parseInt(bean.getVisitor()) > 0) {
                      return true;
                    }
                    return false;
                  })
              .collect(Collectors.counting());
      DataViewFlow flow = new DataViewFlow();
      flow.setCreateTime(endTime);
      flow.setDeviceType(t.getDeviceType());
      flow.setSourcePage(t.getSourcePage());
      flow.setSourceType(t.getSourceType());
      flow.setVisitorCount(df.intValue());
      flow.setInquiryCount(getInquiry(t.getSourceType(), t.getSourcePage(), startTime, endTime));
      flowDao.save(flow);
    }
  }

  public void flowPageConver(
      Map<AccessTypeEnum, List<GoogleBrowseInfo>> mapPage, Date startTime, Date endTime) {
    double sessionNum = 0; // 会话人数
    for (Entry<AccessTypeEnum, List<GoogleBrowseInfo>> entry : mapPage.entrySet()) {
      DataViewFlowPage flowPage = new DataViewFlowPage();
      Integer viewCount = 0; // 浏览量
      Integer visitorCount = 0; // 访客数
      Integer clickCount = 0; // 点击量
      // double jumpRate = 0; // 跳失率
      // double averageStayTime = 0; // 平均停留时长

      double bounceRateNum = 0; // 跳失人数
      double sumStayTime = 0; // 总停留时长
      DecimalFormat df = new DecimalFormat("#.00");
      for (GoogleBrowseInfo item : entry.getValue()) {
        viewCount += Integer.parseInt(item.getPageViews());
        if (!item.getSessions().equals("0")) {
          sessionNum += Integer.parseInt(item.getSessions());
          bounceRateNum += countBounce(item.getBounceRate(), item.getSessions());
        }
        if (!item.getVisitor().equals("0")) {
          visitorCount += Integer.parseInt(item.getVisitor());
        }
        if (!item.getPreviousPage().equals("(entrance)")
            && !item.getPagePath().equals(item.getPreviousPage())) {
          clickCount += Integer.parseInt(item.getVisitor());
        }
        sumStayTime += Double.parseDouble(item.getTimeOnPage());
      }
      flowPage.setAccessType(entry.getKey());
      flowPage.setViewCount(viewCount);
      flowPage.setVisitorCount(visitorCount);
      flowPage.setClickCount(clickCount);
      flowPage.setClickNumber(getClickNum(entry.getKey(), startTime, endTime));
      flowPage.setJumpRate(
          sessionNum == 0 ? 0 : Double.parseDouble(df.format(bounceRateNum / sessionNum)));
      flowPage.setAverageStayTime(
          viewCount == 0 ? 0 : Double.parseDouble(df.format(sumStayTime / viewCount)));
      flowPage.setClickRate(
          viewCount == 0
              ? 0
              : Double.parseDouble(df.format((double) clickCount / (double) viewCount)));
      flowPage.setCreateTime(endTime);
      flowPageDao.save(flowPage);
    }
    session = (int) sessionNum;
  }

  public void inquiryConver(Date startTime, Date endTime) {
    DataViewInquiry in = new DataViewInquiry();
    in.setVisitorCount(0);
    in.setInquiryCount((int) inquiryInfoDao.countByCreateTimeBetween(startTime, endTime));
    in.setInquiryNumber(inquiryInfoDao.getPeopleNum(startTime, endTime).size());
    in.setCreateTime(endTime);
    inquiryDao.save(in);
  }

  public void inquiryRankConver(List<InquiryInfo> InquiryInfoList, Date startTime, Date endTime) {
    Map<DataViewInquiryRank, List<InquiryInfo>> map = new HashMap<>();
    for (InquiryInfo item : InquiryInfoList) {
      DataViewInquiryRank rank = new DataViewInquiryRank();
      if (!item.getType().equals(InquiryTypeEnum.COMMODITY)
          && !item.getType().equals(InquiryTypeEnum.SEARCHTERM)
          && !item.getType().equals(InquiryTypeEnum.RFQ)) {
        rank.setInquiryType(InquiryTypeEnum.SUPPLIER);
      } else if (item.getType().equals(InquiryTypeEnum.COMMODITY)) {
        rank.setInquiryType(InquiryTypeEnum.COMMODITY);
      } else {
        continue;
      }
      rank.setPkey(item.getPkey());
      rank.setInquiryName(item.getName());
      rank.setImg(item.getImg());
      if (map.containsKey(rank)) {
        map.get(rank).add(item);
      } else {
        List<InquiryInfo> infos = new ArrayList<>();
        infos.add(item);
        map.put(rank, infos);
      }
    }
    for (Entry<DataViewInquiryRank, List<InquiryInfo>> item : map.entrySet()) {
      DataViewInquiryRank rank = item.getKey();
      Integer[] count = {0, 0};
      if (rank.getInquiryType().equals(InquiryTypeEnum.COMMODITY)) {
        // 商品
        count =
            googleDao.getPdtVisitCountAndPageViews(
                rank.getPkey(), ydms.format(startTime), ydms.format(endTime));
      } else {
        // 商家
        count =
            googleDao.getSupVisitCountAndPageViews(
                rank.getPkey(), ydms.format(startTime), ydms.format(endTime));
      }
      Map<String, List<InquiryInfo>> m =
          item.getValue().stream().collect(Collectors.groupingBy(InquiryInfo::getIp));
      rank.setInquiryNumber(m.size());
      rank.setInquiryCount(item.getValue().size());
      rank.setViewCount(count[0]);
      rank.setVisitorCount(count[1]);
      rank.setInquiryAmount(0);
      rank.setCreateTime(endTime);
      inquiryRankDao.save(rank);
    }
  }

  public void userConver(Integer visitCount, Integer registerCount, Date startTime, Date endTime) {
    List<WebVisitInfo> list = webDao.findByCreateTimeBetween(startTime, endTime);
    Map<String, List<WebVisitInfo>> m =
        list.stream().collect(Collectors.groupingBy(WebVisitInfo::getIp));
    DataViewUser user = new DataViewUser();
    user.setVisitorCount(visitCount);
    user.setRegisterCount(registerCount);
    // 新用户
    Integer newVisitorCount = m.size();
    // 老用户
    Integer oldVisitorCount = 0;
    for (WebVisitInfo item : list) {
      if (item.getUserId() > -1) {
        oldVisitorCount++;
      }
    }
    user.setNewVisitorCount(newVisitorCount);
    user.setOldVisitorCount(oldVisitorCount);
    user.setPurchaseCount(
        (int) userInfoDao.countByTypeAndCreateTimeLessThan(RegisterTypeEnum.PURCHASE, endTime));
    user.setSupplierCount(
        (int) userInfoDao.countByTypeAndCreateTimeLessThan(RegisterTypeEnum.SUPPLIER, endTime));
    user.setCreateTime(endTime);
    userDao.save(user);
  }

  public void userAreaConver(String country, int userCount, Date startTime, Date endTime) {
    DataViewUserArea area = new DataViewUserArea();
    area.setAreaCount(userCount);
    area.setArea(country);
    area.setCreateTime(endTime);
    userAreaDao.save(area);
  }

  public void userSexConver(Date startTime, Date endTime) {
    long man = userInfoDao.countBySexAndCreateTimeLessThan(SexEnum.MAN, endTime);
    long woman = userInfoDao.countBySexAndCreateTimeLessThan(SexEnum.WOMAN, endTime);
    long unknown = userInfoDao.countBySexAndCreateTimeLessThan(SexEnum.UNKNOWN, endTime);
    DataViewUserSex userMan = new DataViewUserSex();
    DataViewUserSex userWoman = new DataViewUserSex();
    DataViewUserSex userUnknown = new DataViewUserSex();
    userMan.setSex(SexEnum.MAN);
    userMan.setSexCount((int) man);
    userMan.setCreateTime(endTime);
    userWoman.setSex(SexEnum.WOMAN);
    userWoman.setSexCount((int) woman);
    userWoman.setCreateTime(endTime);
    userUnknown.setSex(SexEnum.UNKNOWN);
    userUnknown.setSexCount((int) unknown);
    userUnknown.setCreateTime(endTime);
    userSexDao.save(userMan);
    userSexDao.save(userWoman);
    userSexDao.save(userUnknown);
  }
}

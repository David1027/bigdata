package com.shoestp.mains.schedulers.googleApi;

import com.shoestp.mains.dao.DataView.flow.FlowDao;
import com.shoestp.mains.dao.DataView.flow.FlowPageDao;
import com.shoestp.mains.dao.shoestpData.InquiryInfoDao;
import com.shoestp.mains.dao.shoestpData.WebVisitInfoDao;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlow;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.repositorys.metaData.GoogleBrowseInfoRepository;
import com.shoestp.mains.schedulers.BaseSchedulers;
import com.shoestp.mains.views.DataView.metaData.DataView;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataConver extends BaseSchedulers {
  public DataConver() {
    super(
        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever(),
        DataConver.class.getName());
  }

  @Autowired private FlowDao flowDao;
  @Autowired private FlowPageDao flowPageDao;
  @Autowired private GoogleBrowseInfoRepository googleBrowseInfoDao;
  @Autowired private WebVisitInfoDao webDao;
  @Autowired private InquiryInfoDao inquiryInfoDao;

  private static int timing = 60; // 定时60分钟
  private static List<String> PC =
      Arrays.asList("(not set)", "Chrome OS", "Linux", "Macintosh", "Windows");
  private static List<String> MOBILE = Arrays.asList("Android", "IOS");
  /*private static List<String> PAGES =
  Arrays.asList(
      "/activity/html/ds/",
      "/activity/html/jn/",
      "/activity/html/leatherShoes/",
      "/activity/html/shoes/",
      "/activity/html/romania/",
      "/m/landing/leather",
      "/m/landing/wmShoes");*/

  private static Map<String, List<String>> sourcePage = new HashMap<>(); // 来源渠道

  static {
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

  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      /*getClickNum(
      AccessTypeEnum.INDEX,
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-01-01 00:00:00"),
      new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-05-18 00:00:00"));*/
      getInquiry(
          SourceTypeEnum.OTHER,
          "爱莱发着陆页",
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-01-01 00:00:00"),
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-05-18 00:00:00"));
    } catch (ParseException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // System.out.println(555);
  }

  /*  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {

      DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
      SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
      LocalDateTime date = LocalDateTime.now();
      // Date createTime = LocalDateTimeToUdate(date);
      String startTime = "20181119000000";
      long day = 0; // 天数差
      String endTime = date.format(ofPattern) + "00";
      if (flowDao.getFlowTopOne().isPresent()) {
        // startTime = date.plusHours(-1).format(ofPattern) + "00";
        startTime =
            new SimpleDateFormat("yyyyMMddHHmm")
                    .format(flowDao.getFlowTopOne().get().getCreateTime())
                + "00";
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
        for (int i = 0; i > day; i--) {
          LocalDateTime plusDays = startDate.plusDays(1);
          LocalDateTime plusDays2 = endDate.plusDays(1);
          List<GoogleBrowseInfo> browseInfoList =
              googleBrowseInfoDao.queryByStartTimeAndEndTime(
                  plusDays.format(ofPattern), plusDays2.format(ofPattern));
          startDate = plusDays;
          endDate = plusDays2;
          if (!browseInfoList.isEmpty()) {
            filtration(
                browseInfoList, LocalDateTimeToUdate(startDate), LocalDateTimeToUdate(endDate));
          } else {
            continue;
          }
        }
      } else {
        List<GoogleBrowseInfo> browseInfoList =
            googleBrowseInfoDao.queryByStartTimeAndEndTime(startTime, endTime);
        if (!browseInfoList.isEmpty()) {
          try {
            filtration(browseInfoList, sim.parse(startTime), sim.parse(endTime));
          } catch (ParseException e) {
            e.printStackTrace();
          }
        } else {
          return;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      // TODO: handle exception
    }
  }*/

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
      for (Entry<DataView, List<GoogleBrowseInfo>> entry : map.entrySet()) {
        DataView t = entry.getKey();
        List<GoogleBrowseInfo> infos = entry.getValue();
        Long df =
            infos.stream()
                .filter(
                    bean -> {
                      if (bean.getVisitor().equals("1")) {
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
      for (Entry<AccessTypeEnum, List<GoogleBrowseInfo>> entry : mapPage.entrySet()) {
        DataViewFlowPage flowPage = new DataViewFlowPage();
        Integer viewCount = 0; // 浏览量
        Integer visitorCount = 0; // 访客数
        Integer clickCount = 0; // 点击量
        // double jumpRate = 0; // 跳失率
        // double averageStayTime = 0; // 平均停留时长
        double sessionNum = 0; // 会话人数
        double bounceRateNum = 0; // 跳失人数
        double sumStayTime = 0; // 总停留时长
        DecimalFormat df = new DecimalFormat("#.00");
        for (GoogleBrowseInfo item : entry.getValue()) {
          if (!item.getSessions().equals("0")) {
            viewCount += countNum(item.getSessions());
            sessionNum += countNum(item.getSessions());
            bounceRateNum += countBounce(item.getBounceRate(), item.getSessions());
          } else {
            viewCount++;
          }
          if (!item.getVisitor().equals("0")) {
            visitorCount += countNum(item.getVisitor());
          }
          if (!item.getPreviousPage().equals("(entrance)")
              && !item.getPagePath().equals(item.getPreviousPage())) {
            clickCount += countNum(item.getVisitor());
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
            sessionNum == 0 ? 0 : Double.parseDouble(df.format(sumStayTime / sessionNum)));
        flowPage.setCreateTime(endTime);
        flowPageDao.save(flowPage);
      }
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
      return 1;
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

  public void save(Map<Integer, GoogleBrowseInfo> browseInfoMap) {}

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
    Double d = 28.0;
    Double a = 32.0;
    DecimalFormat df = new DecimalFormat("#.00");
    String format = df.format(d / a);
    double parseDouble = Double.parseDouble(format);
    System.out.println(parseDouble);
  }

  @Bean(name = "DataConver")
  public JobDetail JobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(getJobNmae()).storeDurably().build();
  }

  public void defaults() {
    SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever();
  }

  @Bean(name = "DataConverTrigger")
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(JobDetail())
        .withIdentity(getJobNmae() + "Trigger")
        .withSchedule(getScheduleBuilder())
        .build();
  }
}

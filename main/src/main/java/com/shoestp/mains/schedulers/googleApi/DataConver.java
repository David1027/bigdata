package com.shoestp.mains.schedulers.googleApi;

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

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.shoestp.mains.dao.DataView.flow.FlowDao;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlow;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.repositorys.metaData.GoogleBrowseInfoRepository;
import com.shoestp.mains.schedulers.BaseSchedulers;

// @Component
public class DataConver extends BaseSchedulers {

  public DataConver() {
    super(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever());
  }

  @Autowired private FlowDao flowDao;
  @Autowired private GoogleBrowseInfoRepository googleBrowseInfoDao;

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
    sourcePage.put("迪盛着陆页", Arrays.asList("/activity/html/ds/", "/m/disheng"));
    sourcePage.put("巨纳着陆页", Arrays.asList("/activity/html/jn/", "/m/juna"));
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
    sourcePage.put("罗马尼亚相关着陆页", Arrays.asList("/activity/html/romania/"));
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
    LocalDateTime date = LocalDateTime.now();
    Date createTime = LocalDateTimeToUdate(date);
    String startTime = "20181119000000";
    long day = 0; // 天数差
    String endTime = date.format(ofPattern) + "00";
    if (flowDao.getFlowTopOne().isPresent()) {
      // startTime = date.plusHours(-1).format(ofPattern) + "00";
      startTime =
          new SimpleDateFormat("yyyyMMddHHmm").format(flowDao.getFlowTopOne().get().getCreateTime())
              + "00";
    } else {
      Duration duration =
          Duration.between(
              date, LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
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
      }
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
    } else if ((pagePath.indexOf("_p") != -1 && pagePath.endsWith(".html"))
        || pagePath.indexOf("/m/product/prodetail") != -1) {
      // 产品详情页
      return AccessTypeEnum.DETAIL;
    } else if (pagePath.indexOf("/home/pdt_PdtProduct") != -1
        || pagePath.indexOf("/m/product/prolist") != -1) {
      // 搜索页
      return AccessTypeEnum.SEARCH;
    } else if (pagePath.indexOf("/html/SVS/svs.jsp") != -1 || pagePath.indexOf("/m/SVS") != -1) {
      // svs专题页
      return AccessTypeEnum.SVS;
    } else {
      // 其他页
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
   * @param
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
        flowDao.save(flow);
      }
      for (Entry<AccessTypeEnum, List<GoogleBrowseInfo>> entry : mapPage.entrySet()) {
        DataViewFlowPage flowPage = new DataViewFlowPage();
        Integer viewCount = 0; // 浏览量
        Integer visitorCount = 0; // 访客数
        double clickCount = 0; // 点击量
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
//        flowPage.setClickCount(clickCount);
        flowPage.setClickNumber(getClickNum(entry.getKey(), startTime, endTime));
        flowPage.setJumpRate(Double.parseDouble(df.format(bounceRateNum / sessionNum)));
        flowPage.setAverageStayTime(Double.parseDouble(df.format(sumStayTime / sessionNum)));
        flowPage.setCreateTime(endTime);
      }
    }
  }

  public int getClickNum(AccessTypeEnum acc, Date startTime, Date endTime) {
    if (acc.equals(AccessTypeEnum.INDEX)) {
      // 首页

    } else if (acc.equals(AccessTypeEnum.DETAIL)) {
      // 详情
    } else if (acc.equals(AccessTypeEnum.SEARCH)) {
      // 搜索
    } else if (acc.equals(AccessTypeEnum.SVS)) {
      // svs
    } else {
      // 其他
    }
    return 0;
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

  /** -按浏览量排行 */
  public void pageRanking() {}

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
    Date d = new SimpleDateFormat("yyyyMMddHHmmss").parse("20190516180300");
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d));
  }
}

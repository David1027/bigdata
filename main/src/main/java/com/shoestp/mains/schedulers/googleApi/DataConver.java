package com.shoestp.mains.schedulers.googleApi;

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

import com.shoestp.mains.dao.DataView.FlowDao;
import com.shoestp.mains.dao.MetaData.GoogleBrowseInfoDao;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlow;
import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.schedulers.BaseSchedulers;

public class DataConver extends BaseSchedulers {

  public DataConver(SimpleScheduleBuilder scheduleBuilder) {
    super(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever());
  }

  @Autowired private FlowDao flowDao;
  @Autowired private GoogleBrowseInfoDao googleBrowseInfoDao;

  private static int timing = 60; // 定时60分钟
  private static List<String> PC =
      Arrays.asList("(not set)", "Chrome OS", "Linux", "Macintosh", "Windows");
  private static List<String> MOBILE = Arrays.asList("Android", "IOS");
  private static List<String> PAGES =
      Arrays.asList(
          "/activity/html/ds/",
          "/activity/html/jn/",
          "/activity/html/leatherShoes/",
          "/activity/html/shoes/",
          "/activity/html/romania/",
          "/m/landing/leather",
          "/m/landing/wmShoes");

  private static Map<Integer, String> sourcePage = new HashMap<>(); // 来源渠道

  //  static {
  //    PC.add("(not set)");
  //    PC.add("Chrome OS");
  //    PC.add("Linux");
  //    PC.add("Macintosh");
  //    PC.add("Windows");
  //    sourcePage.put(0, "/activity/html/ds/");
  //    sourcePage.put(1, "/activity/html/jn/");
  //    sourcePage.put(2, "/activity/html/leatherShoes/");
  //    sourcePage.put(3, "/activity/html/shoes/");
  //    sourcePage.put(4, "/activity/html/romania/");
  //    sourcePage.put(5, "/m/landing/leather");
  //    sourcePage.put(6, "/m/landing/wmShoes");
  //  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    LocalDateTime date = LocalDateTime.now();
    Date createTime = LocalDateTimeToUdate(date);
    String startTime = "20180101000000";
    long day = 0; // 天数差
    String endTime = date.format(ofPattern) + "00";
    if (flowDao.getFlowTopOne().isPresent()) {
      startTime = date.plusHours(-1).format(ofPattern) + "00";
    } else {
      Duration duration =
          Duration.between(
              date, LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
      day = duration.toDays();
    }
    if (day < 0) {
      // 之前的数据按天插入
      LocalDateTime startDate = LocalDateTime.parse("201801010000", ofPattern);
      LocalDateTime endDate = LocalDateTime.parse("201801012359", ofPattern);
      createTime = LocalDateTimeToUdate(endDate);
      for (int i = 0; i > day; i--) {
        startDate.plusDays(1);
        endDate.plusDays(1);
        List<GoogleBrowseInfo> browseInfoList =
            googleBrowseInfoDao.queryByStartTimeAndEndTime(
                startDate.format(ofPattern), endDate.format(ofPattern));
      }
    } else {
      List<GoogleBrowseInfo> browseInfoList =
          googleBrowseInfoDao.queryByStartTimeAndEndTime(startTime, endTime);
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
    String pe = "else";
    for (String p : PAGES) {
      if (page.indexOf(p) != -1) {
        pe = p;
        break;
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

  public Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
    ZoneId zone = ZoneId.systemDefault();
    Instant instant = localDateTime.atZone(zone).toInstant();
    return Date.from(instant);
  }

  public void filtration(List<GoogleBrowseInfo> browseInfoList, Date date) {
    if (!browseInfoList.isEmpty()) {
      // 非空
      Map<DataView, List<GoogleBrowseInfo>> map = new HashMap<>();

      for (GoogleBrowseInfo info : browseInfoList) {
        DataView data = new DataView();
        data.setDeviceType(judgeSystem(info.getSystem()));
        data.setSourcePage(judgePage(info.getPagePath()));
        data.setSourceType(judgeSource(info.getSourcePage()));

        if (map.containsKey(data)) {
          map.get(data).add(info);
        } else {
          List<GoogleBrowseInfo> infos = new ArrayList<>();
          infos.add(info);
          map.put(data, infos);
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
        flow.setCreateTime(date);
        flow.setDeviceType(t.getDeviceType());
        flow.setSourcePage(t.getSourcePage());
        flow.setSourceType(t.getSourceType());
        flow.setVisitorCount(df.intValue());
      }
    }
  }

  public void save(Map<Integer, GoogleBrowseInfo> browseInfoMap) {}

  public static void main(String[] args) {
    //    LocalDateTime date = LocalDateTime.now();
    //    String format = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + "00";
    //    Duration duration =
    //        Duration.between(
    //            date,
    //            LocalDateTime.parse("20180101000000",
    // DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    //    System.out.println(duration.toDays());
  }
}

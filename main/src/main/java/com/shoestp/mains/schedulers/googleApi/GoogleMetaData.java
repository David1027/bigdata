package com.shoestp.mains.schedulers.googleApi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.DimensionFilterClause;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import com.shoestp.mains.dao.MetaData.GoogleBrowseInfoDao;
import com.shoestp.mains.dao.MetaData.GooglePagePropertyInfoDao;
import com.shoestp.mains.dao.MetaData.GoogleVisitorInfoDao;
import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;
import com.shoestp.mains.entitys.MetaData.GooglePageProperty;
import com.shoestp.mains.entitys.MetaData.GoogleVisitorInfo;
import com.shoestp.mains.schedulers.BaseSchedulers;

@Component
public class GoogleMetaData extends BaseSchedulers {

  public GoogleMetaData() {
    super(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever());
    // TODO Auto-generated constructor stub
  }

  @Autowired private AnalyticsReporting ar;
  @Autowired private GoogleBrowseInfoDao googleBrowseInfoDao;
  @Autowired private GoogleVisitorInfoDao googleVisitorInfoDao;
  @Autowired private GooglePagePropertyInfoDao googlePagePropertyInfoDao;

  private static int timing = 5; // 定时5分钟
  private static List<Dimension> browseDimensionList = new ArrayList<>();
  private static List<Metric> browseMetricList = new ArrayList<>();
  private static List<Dimension> visitorDimensionList = new ArrayList<>();
  private static List<Metric> visitorMetricList = new ArrayList<>();
  private static List<Metric> pageMetricList = new ArrayList<>();

  static {
    browseDimensionList.add(new Dimension().setName("ga:pagePath")); // 下级目录
    browseDimensionList.add(new Dimension().setName("ga:hostname")); // 域名
    browseDimensionList.add(new Dimension().setName("ga:dateHourMinute")); // 时间
    browseDimensionList.add(new Dimension().setName("ga:country")); // 访问国家
    browseDimensionList.add(new Dimension().setName("ga:previousPagePath")); // 上一个访问地址
    browseDimensionList.add(new Dimension().setName("ga:minute")); // 分钟
    browseMetricList.add(new Metric().setExpression("ga:sessions")); // 会话总数 ---访客数
    browseMetricList.add(new Metric().setExpression("ga:bounceRate")); // 跳失率
    browseMetricList.add(new Metric().setExpression("ga:avgTimeOnPage")); // 平均停留时间
    browseMetricList.add(new Metric().setExpression("ga:uniquePageviews")); // 页面唯一访问数
    visitorDimensionList.add(new Dimension().setName("ga:source")); // 访问源地址
    visitorDimensionList.add(new Dimension().setName("ga:operatingSystem")); // 设备
    visitorDimensionList.add(new Dimension().setName("ga:dateHourMinute")); // 时间
    visitorDimensionList.add(new Dimension().setName("ga:country")); // 访问国家
    visitorDimensionList.add(new Dimension().setName("ga:minute")); // 分钟
    visitorMetricList.add(new Metric().setExpression("ga:sessions")); // 会话总数 ---访客数
    pageMetricList.add(new Metric().setExpression("ga:bounceRate")); // 跳失率
    pageMetricList.add(new Metric().setExpression("ga:pageviews")); // 总访问数
    pageMetricList.add(new Metric().setExpression("ga:avgTimeOnPage")); // 平均停留时间
  }

  @Override
  protected void executeInternal(JobExecutionContext context)
      throws JobExecutionException { // TODO Auto-generated method stub
    queryData(1, browseMetricList, browseDimensionList); // 浏览数据
    sleep();
    queryData(2, visitorMetricList, visitorDimensionList); // 访问数据
    sleep();
    queryData(3, pageMetricList, new ArrayList<>()); // 页面数据
    // System.out.println("wdawdawdw");
  }

  public void queryData(int queryType, List<Metric> metric, List<Dimension> dimension) {
    Date date = new Date();
    boolean b = false; // 是否下一小时
    // 获取数据库里最后拉去数据的的日期
    /*String startDateString = getLastDate(queryType);
    Date parse = null;
    try {
      parse = new SimpleDateFormat("yyyy-MM-dd HH").parse(startDateString);
    } catch (ParseException e1) { // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    LocalDateTime startDate =
        LocalDateTime.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime today = LocalDateTime.now();
    if (today.compareTo(startDate) < 1) {
      return;
    }
    Integer minute = Integer.parseInt(startDate.format(DateTimeFormatter.ofPattern("mm")));
    String filter = "";
    if (!startDateString.equals("2018-01-01 00:00:00")) {
      filter = "ga:dateHourMinute=@" + startDate.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
      if (minute <= timing) {
        b = true;
        LocalDateTime newStartDate = startDate.plusHours(1);
        filter +=
            ",ga:dateHourMinute=@" + newStartDate.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
      }
    }*/
    try {
      // 本地测试用开启代理
      System.setProperty("http.proxySet", "true");
      System.setProperty("http.proxyHost", "127.0.0.1");
      System.setProperty("http.proxyPort", String.valueOf(1080));
      System.setProperty("https.proxyHost", "127.0.0.1");
      System.setProperty("https.proxyPort", String.valueOf(1080));
      DateRange dateRange = new DateRange();
      // dateRange.setStartDate(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      /*dateRange.setStartDate(startDateString.substring(0, 10));
            dateRange.setEndDate("today");
      */
      /////////// 分段获取初始数据
      /*dateRange.setStartDate("2018-01-01");
      dateRange.setEndDate("2019-01-15");*/
      dateRange.setStartDate("2019-01-16");
      dateRange.setEndDate("today");
      /////////// 分段获取初始数据
      List<DimensionFilterClause> dimList = new ArrayList<>();
      DimensionFilterClause dim = new DimensionFilterClause();
      // dim.set

      ReportRequest request =
          new ReportRequest()
              .setViewId("185165418")
              .setPageSize(500000)
              .setDateRanges(Arrays.asList(dateRange))
              .setMetrics(metric) // 10个
              .setDimensions(dimension);
      // .setFiltersExpression(filter); //过滤

      ArrayList<ReportRequest> requests = new ArrayList();
      requests.add(request);
      GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
      GetReportsResponse response = ar.reports().batchGet(getReport).execute();
      for (Report report : response.getReports()) {
        ColumnHeader header = report.getColumnHeader();
        //                List<String> dimensionHeaders = header.getDimensions();
        List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
        List<ReportRow> rows = report.getData().getRows();
        if (rows == null) {
          System.out.println("No data found");
          return;
        }
        // System.out.println(rows);
        System.out.println(rows.size());
        for (ReportRow row : rows) {
          List<String> dimensions = row.getDimensions();
          List<String> values = row.getMetrics().get(0).getValues();
          save(queryType, dimensions, values, date, 0, b, null);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void save(
      int type,
      List<String> dimensions,
      List<String> values,
      Date date,
      int minute,
      boolean b,
      Date startDate) {
    /* Date parse = null;
    Date parse2 = null;
    if (type == 1 || type == 2) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
      String format = simpleDateFormat.format(startDate);
      String substring = dimensions.get(2).substring(0, 10);
      try {
        parse = simpleDateFormat.parse(substring);
        parse2 = simpleDateFormat.parse(format);
      } catch (ParseException e) { // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }*/
    switch (type) {
      case 1:
        /*if (Integer.parseInt(dimensions.get(5)) < minute && !b) {
          break;
        } else if (Integer.parseInt(dimensions.get(5)) < minute && b) {
          if (parse.compareTo(parse2) == 0) {
            break;
          }
        }*/
        GoogleBrowseInfo browse = new GoogleBrowseInfo();
        browse.setPagePath(dimensions.get(0));
        browse.setHostName(dimensions.get(1));
        browse.setAccessTime(dimensions.get(2));
        browse.setCountry(dimensions.get(3));
        browse.setPreviousPage(dimensions.get(4));
        browse.setSessions(values.get(0));
        browse.setBounceRate(values.get(1));
        browse.setAvgTimeOnPage(values.get(2));
        browse.setVisitor(values.get(3));
        browse.setCreateTime(date);
        googleBrowseInfoDao.save(browse);
        break;
      case 2:
        /*if (Integer.parseInt(dimensions.get(4)) < minute && !b) {
          break;
        } else if (Integer.parseInt(dimensions.get(4)) < minute && b) {
          if (parse.compareTo(parse2) == 0) {
            break;
          }
        }*/
        GoogleVisitorInfo visitor = new GoogleVisitorInfo();
        visitor.setSource(dimensions.get(0));
        visitor.setSystem(dimensions.get(1));
        visitor.setAccessTime(dimensions.get(2));
        visitor.setCountry(dimensions.get(3));
        visitor.setCreateTime(date);
        int sessionNum = Integer.parseInt(values.get(0));
        if (sessionNum > 1) {
          for (int i = 0; i < sessionNum; i++) {
            googleVisitorInfoDao.save(visitor);
          }
        } else {
          googleVisitorInfoDao.save(visitor);
        }
        break;
      case 3:
        GooglePageProperty page = new GooglePageProperty();
        page.setBounceRate(values.get(0));
        page.setPageViews(values.get(1));
        page.setAvgTimeOnPage(values.get(2));
        page.setCreateTime(date);
        googlePagePropertyInfoDao.save(page);
        break;
      default:
        break;
    }
  }

  public void sleep() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public String getLastDate(int type) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String defaultDate = "2018-01-01 00:00:00";
    switch (type) {
      case 1:
        Optional<GoogleBrowseInfo> browseInfo =
            googleBrowseInfoDao.findTopByOrderByCreateTimeDesc();
        if (!browseInfo.isPresent()) {
          return defaultDate;
        } else {
          return format.format(browseInfo.get().getCreateTime());
        }
      case 2:
        Optional<GoogleVisitorInfo> visitorInfo =
            googleVisitorInfoDao.findTopByOrderByCreateTimeDesc();
        if (!visitorInfo.isPresent()) {
          return defaultDate;
        } else {
          return format.format(visitorInfo.get().getCreateTime());
        }
      case 3:
        Optional<GooglePageProperty> pageProperty =
            googlePagePropertyInfoDao.findTopByOrderByCreateTimeDesc();
        if (!pageProperty.isPresent()) {
          return defaultDate;
        } else {
          return format.format(pageProperty.get().getCreateTime());
        }
      default:
        break;
    }
    return defaultDate;
  }

  public static void main(String[] args) {
    /*  LocalDateTime today = LocalDateTime.now();
    LocalDateTime plusHours = today.plusHours(1);
    System.out.println(plusHours.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));*/
    String s = "2019-9-9 13";
    try {
      Date format = new SimpleDateFormat("yyyyMMddHH").parse(s);
      System.out.println(format);
    } catch (ParseException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
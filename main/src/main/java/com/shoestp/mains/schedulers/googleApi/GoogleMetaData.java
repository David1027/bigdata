package com.shoestp.mains.schedulers.googleApi;

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
import com.shoestp.mains.dao.metaData.GoogleBrowseInfoDao;
import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;
import com.shoestp.mains.entitys.MetaData.GooglePageProperty;
import com.shoestp.mains.repositorys.metaData.GooglePagePropertyInfoRepository;
import com.shoestp.mains.schedulers.BaseSchedulers;
import com.shoestp.mains.views.DataView.metaData.PageRankingView;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

// @Component
public class GoogleMetaData extends BaseSchedulers {

  public GoogleMetaData() {
    super(
        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever(),
        GoogleMetaData.class.getName());
  }

  @Autowired private AnalyticsReporting ar;
  @Autowired private GoogleBrowseInfoDao googleBrowseInfoDao;
  @Autowired private GooglePagePropertyInfoRepository googlePagePropertyInfoDao;

  private static int timing = 20; // 定时5分钟
  private static List<Dimension> browseDimensionList = new ArrayList<>();
  private static List<Metric> browseMetricList = new ArrayList<>();
  private static List<Metric> pageMetricList = new ArrayList<>();

  static {
    browseDimensionList.add(new Dimension().setName("ga:pagePath")); // 下级目录
    /*browseDimensionList.add(new Dimension().setName("ga:hostname")); // 域名 */
    browseDimensionList.add(new Dimension().setName("ga:dateHourMinute")); // 时间
    browseDimensionList.add(new Dimension().setName("ga:country")); // 访问国家
    // browseDimensionList.add(new Dimension().setName("ga:region")); // 省份
    browseDimensionList.add(new Dimension().setName("ga:previousPagePath")); // 上一个访问地址
    browseDimensionList.add(new Dimension().setName("ga:source")); // 访问源
    browseDimensionList.add(new Dimension().setName("ga:operatingSystem")); // 系统设备
    /*browseDimensionList.add(new Dimension().setName("ga:minute")); // 分钟 */
    browseMetricList.add(new Metric().setExpression("ga:sessions")); // 会话总数 ---访客数
    browseMetricList.add(new Metric().setExpression("ga:bounceRate")); // 跳失率
    browseMetricList.add(new Metric().setExpression("ga:timeOnPage")); // 平均停留时间
    browseMetricList.add(new Metric().setExpression("ga:uniquePageviews")); // 页面唯一访问数
    browseMetricList.add(new Metric().setExpression("ga:pageviews")); // 总浏览数
    pageMetricList.add(new Metric().setExpression("ga:bounceRate")); // 跳失率
    pageMetricList.add(new Metric().setExpression("ga:pageviews")); // 总浏览数
    pageMetricList.add(new Metric().setExpression("ga:avgTimeOnPage")); // 平均停留时间
    // pageMetricList.add(new Metric().setExpression("ga:timeOnPage")); // 会话总数 ---访客数
  }

  @Override
  protected void executeInternal(JobExecutionContext context)
      throws JobExecutionException { // TODO Auto-generated method stub
    // test();
    sleep(5000);
    queryData(1, browseMetricList, browseDimensionList); // 浏览数据
    // sleep();
    // queryData(3, pageMetricList, new ArrayList<>()); // 页面数据
    // System.out.println("wdawdawdw");
  }

  public void test() {
    for (PageRankingView pageRankingView : googleBrowseInfoDao.queryPageRanking(10)) {
      System.out.println(pageRankingView);
    }
  }

  public void queryData(int queryType, List<Metric> metric, List<Dimension> dimension) {
    Date date = new Date();
    boolean b = false; // 是否下一小时
    // 获取数据库里最后拉去数据的的日期
    // String filter = "ga:pagePath==/";
    String filter = "ga:hostname=@shoestp.com";
    String startDateString = getLastDate(queryType);
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
    if (!startDateString.equals("2018-11-18 00:00:00")) {
      filter +=
          ",ga:dateHourMinute=@" + startDate.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
      if (minute <= timing) {
        b = true;
        LocalDateTime newStartDate = startDate.plusHours(1);
        filter +=
            ",ga:dateHourMinute=@" + newStartDate.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
      }
    }
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
      dateRange.setStartDate("2019-05-15");
      /*dateRange.setEndDate("2019-01-15");
      dateRange.setStartDate("2019-01-16");*/
      dateRange.setEndDate("2019-05-16");
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
              .setDimensions(dimension) // 7个
              .setFiltersExpression(filter); // 过滤

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
          save(queryType, dimensions, values, date, minute, b, parse);
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
    Date parse = null;
    Date parse2 = null;
    if (type == 1 || type == 2) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
      String format = simpleDateFormat.format(startDate);
      String substring = dimensions.get(1).substring(0, 10);
      try {
        parse = simpleDateFormat.parse(substring);
        parse2 = simpleDateFormat.parse(format);
      } catch (ParseException e) { // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    switch (type) {
      case 1:
        String subStr =
            dimensions.get(1).substring(dimensions.get(1).length() - 2, dimensions.get(1).length());
        if (Integer.parseInt(subStr) < minute && !b) {
          break;
        } else if (Integer.parseInt(subStr) < minute && b) {
          if (parse.compareTo(parse2) == 0) {
            break;
          }
        }
        GoogleBrowseInfo browse = new GoogleBrowseInfo();
        browse.setPagePath(dimensions.get(0));
        browse.setAccessTime(dimensions.get(1));
        browse.setCountry(dimensions.get(2));
        // browse.setProvince(dimensions.get(3));
        browse.setPreviousPage(dimensions.get(3));
        browse.setSourcePage(dimensions.get(4));
        browse.setSystem(dimensions.get(5));
        browse.setSessions(values.get(0));
        browse.setBounceRate(values.get(1));
        browse.setTimeOnPage(values.get(2));
        browse.setVisitor(values.get(3));
        browse.setPageViews(values.get(4));
        browse.setCreateTime(date);
        googleBrowseInfoDao.save(browse);
        break;
      case 3:
        /* GooglePageProperty page = new GooglePageProperty();
        page.setBounceRate(values.get(0));
        page.setPageViews(values.get(1));
        page.setAvgTimeOnPage(values.get(2));
        page.setCreateTime(date);
        googlePagePropertyInfoDao.save(page);*/
        break;
      default:
        break;
    }
  }

  public void sleep(Integer time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public String getLastDate(int type) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String defaultDate = "2018-11-18 00:00:00";
    switch (type) {
      case 1:
        Optional<GoogleBrowseInfo> browseInfo =
            googleBrowseInfoDao.findTopByOrderByCreateTimeDesc();
        if (!browseInfo.isPresent()) {
          return defaultDate;
        } else {
          return format.format(browseInfo.get().getCreateTime());
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
    String s = "2019-9-9213";
    System.out.println(s.substring(s.length() - 2, s.length()));
  }

  @Bean(name = "GoogleMetaData")
  public JobDetail JobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(getJobNmae()).storeDurably().build();
  }

  public void defaults() {
    SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever();
  }

  @Bean(name = "GoogleMetaDataTrigger")
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(JobDetail())
        .withIdentity(getJobNmae() + "Trigger")
        .withSchedule(getScheduleBuilder())
        .build();
  }
}

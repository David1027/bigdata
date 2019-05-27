package com.shoestp.mains.schedulers.googleapi;

import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.*;
import com.shoestp.mains.dao.metadata.GoogleBrowseInfoDao;
import com.shoestp.mains.entitys.metadata.GoogleBrowseInfo;
import com.shoestp.mains.entitys.metadata.GooglePageProperty;
import com.shoestp.mains.repositorys.metadata.GooglePagePropertyInfoRepository;
import com.shoestp.mains.schedulers.BaseSchedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//@Component
public class GoogleMetaData extends BaseSchedulers {

  @Value("${proxy.enable}")
  private Boolean enable_Proxy;

  @Value("${proxy.host}")
  private String host;

  @Value("${proxy.port}")
  private String port;
  /** * 定时5分钟 */
  @Value("${shoestp.scheduler.googlemete.timing}")
  private int timing = 5;

  @Value("${shoestp.scheduler.googlemete.enable}")
  private Boolean enable = false;

  private static final Logger logger = LogManager.getLogger(GoogleMetaData.class);

  @Autowired private AnalyticsReporting ar;
  @Autowired private GoogleBrowseInfoDao googleBrowseInfoDao;
  @Autowired private GooglePagePropertyInfoRepository googlePagePropertyInfoDao;

  private List<Dimension> browseDimensionList = new ArrayList<>();
  private List<Metric> browseMetricList = new ArrayList<>();
  private List<Metric> pageMetricList = new ArrayList<>();

  @PostConstruct
  public void inti() {
    logger.info("Task Info: Name->{} Timing->{} isRun->{}", getJobNmae(), timing, enable);
    logger.info("Proxy Info[{}]:Host->{}  Port-> {}", enable_Proxy, host, port);
    if (enable) {
      setScheduleBuilder(
          SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(timing).repeatForever());
      setJobNmae(GoogleMetaData.class.getName());
      // 下级目录
      browseDimensionList.add(new Dimension().setName("ga:pagePath"));
      // 域名
      /*browseDimensionList.add(new Dimension().setName("ga:hostname")); */
      // 时间
      browseDimensionList.add(new Dimension().setName("ga:dateHourMinute"));
      // 访问国家
      browseDimensionList.add(new Dimension().setName("ga:country"));
      // 省份
      // browseDimensionList.add(new Dimension().setName("ga:region"));
      // 上一个访问地址
      browseDimensionList.add(new Dimension().setName("ga:previousPagePath"));
      // 访问源
      browseDimensionList.add(new Dimension().setName("ga:source"));
      // 系统设备
      browseDimensionList.add(new Dimension().setName("ga:operatingSystem"));
      /*browseDimensionList.add(new Dimension().setName("ga:minute")); // 分钟 */
      // 会话总数 ---访客数
      browseMetricList.add(new Metric().setExpression("ga:sessions"));
      // 跳失率
      browseMetricList.add(new Metric().setExpression("ga:bounceRate"));
      // 平均停留时间
      browseMetricList.add(new Metric().setExpression("ga:timeOnPage"));
      // 页面唯一访问数
      browseMetricList.add(new Metric().setExpression("ga:uniquePageviews"));
      // 总浏览数
      browseMetricList.add(new Metric().setExpression("ga:pageviews"));
      // 跳失率
      pageMetricList.add(new Metric().setExpression("ga:bounceRate"));
      // 总浏览数
      pageMetricList.add(new Metric().setExpression("ga:pageviews"));
      // 平均停留时间
      pageMetricList.add(new Metric().setExpression("ga:avgTimeOnPage"));
      // 会话总数 ---访客数
      // pageMetricList.add(new Metric().setExpression("ga:timeOnPage"));
    }
  }

  @Override
  protected void executeInternal(JobExecutionContext context)
      throws JobExecutionException { // TODO Auto-generated method stub
    sleep(5000);
    queryData(1, browseMetricList, browseDimensionList); // 浏览数据
    // queryData(3, pageMetricList, new ArrayList<>()); // 页面数据
  }

  public void queryData(int queryType, List<Metric> metric, List<Dimension> dimension) {
    Date date = new Date();
    // 是否下一小时
    boolean b = false;
    // 获取数据库里最后拉去数据的的日期
    // String filter = "ga:pagePath==/";
    String filter = "ga:hostname=@shoestp.com";
    String startDateString = getLastDate(queryType);
    Date parse = null;
    try {
      parse = new SimpleDateFormat("yyyy-MM-dd HH").parse(startDateString);
    } catch (ParseException e1) {
      logger.error(e1);
      e1.printStackTrace();
    }
    LocalDateTime startDate =
        LocalDateTime.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    LocalDateTime today = LocalDateTime.now();
    if (today.compareTo(startDate) < 1) {
      return;
    }
    Integer minute = Integer.parseInt(startDate.format(DateTimeFormatter.ofPattern("mm")));
    if (!"2018-11-18 00:00:00".equals(startDateString)) {
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
      if (enable_Proxy) {
        System.setProperty("java.net.useSystemProxies", String.valueOf(enable_Proxy));
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port);
        System.setProperty("https.proxyHost", host);
        System.setProperty("https.proxyPort", port);
      }
      DateRange dateRange = new DateRange();
      dateRange.setStartDate(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      dateRange.setEndDate("today");
      List<DimensionFilterClause> dimList = new ArrayList<>();
      DimensionFilterClause dim = new DimensionFilterClause();
      ReportRequest request =
          new ReportRequest()
              .setViewId("185165418")
              .setPageSize(500000)
              .setDateRanges(Arrays.asList(dateRange))
              // 10个
              .setMetrics(metric)
              // 7个
              .setDimensions(dimension)
              // 过滤
              .setFiltersExpression(filter);

      ArrayList<ReportRequest> requests = new ArrayList();
      requests.add(request);
      GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);
      GetReportsResponse response = ar.reports().batchGet(getReport).execute();
      for (Report report : response.getReports()) {
        ColumnHeader header = report.getColumnHeader();
        List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
        List<ReportRow> rows = report.getData().getRows();
        if (rows == null) {
          logger.debug("No data found");
          return;
        }
        logger.debug("Google Meta Data Rows {}", rows.size());
        for (ReportRow row : rows) {
          List<String> dimensions = row.getDimensions();
          List<String> values = row.getMetrics().get(0).getValues();
          save(queryType, dimensions, values, date, minute, b, parse);
        }
      }
    } catch (IOException e) {
      logger.error(e);
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
      } catch (ParseException e) {
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
        browse.setSysType(DataConver.PC.contains(dimensions.get(5)) ? 1 : 0);
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
    } catch (InterruptedException e) {
      logger.error(e);
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

  @Bean(name = "GoogleMetaData")
  public JobDetail jobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(getJobNmae()).storeDurably().build();
  }

  @Bean(name = "GoogleMetaDataTrigger")
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail())
        .withIdentity(getJobNmae() + "Trigger")
        .withSchedule(getScheduleBuilder())
        .build();
  }
}

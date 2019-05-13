package com.shoestp.mains.utils;

import static org.apache.tika.metadata.MSOffice.APPLICATION_NAME;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2018/12/3 Time: 14:34 */
@Component
public class GoogleAnalyticsUtils {
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private static final String VIEW_ID = "185165418";

  public void main(String[] args) {
    try {

      System.setProperty("http.proxySet", "true");
      System.setProperty("http.proxyHost", "127.0.0.1");
      System.setProperty("http.proxyPort", String.valueOf(1080));
      // 针对https也开启代理
      System.setProperty("https.proxyHost", "127.0.0.1");
      System.setProperty("https.proxyPort", String.valueOf(1080));
      AnalyticsReporting service = initializeAnalyticsReporting();
      GetReportsResponse response = getReport(service);
      printResponse(response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes an Analytics Reporting API V4 service object.
   *
   * @return An authorized Analytics Reporting API V4 service object.
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @Bean
  public AnalyticsReporting initializeAnalyticsReporting()
      throws GeneralSecurityException, IOException {
    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GoogleCredential credential =
        GoogleCredential.fromStream(
                this.getClass().getResourceAsStream("/GoogleAnalytics/client_id.json"))
            .createScoped(AnalyticsReportingScopes.all());

    // Construct the Analytics Reporting service object.
    return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

  public GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
    // Create the DateRange object.
    DateRange dateRange = new DateRange();
    dateRange.setStartDate("7DaysAgo");
    dateRange.setEndDate("today");

    // Create the Metrics object.
    Metric sessions = new Metric().setExpression("ga:sessions").setAlias("sessions");
    Dimension adMatchedQuery = new Dimension().setName("ga:adMatchedQuery");
    Dimension path = new Dimension().setName("ga:pagePath");
    Dimension keyword = new Dimension().setName("ga:keyword");
    //        Dimension cohortPageviewsPerUser = new
    // Dimension().setName("ga:cohortPageviewsPerUser");

    // Create the ReportRequest object.
    ReportRequest request =
        new ReportRequest()
            .setViewId(VIEW_ID)
            .setDateRanges(Arrays.asList(dateRange))
            .setMetrics(Arrays.asList(sessions))
            .setDimensions(Arrays.asList(path, adMatchedQuery, keyword));

    ArrayList<ReportRequest> requests = new ArrayList();
    requests.add(request);

    // Create the GetReportsRequest object.
    GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);

    // Call the batchGet method.
    GetReportsResponse response = service.reports().batchGet(getReport).execute();

    // Return the response.
    return response;
  }

  public void printResponse(GetReportsResponse response) {
    for (Report report : response.getReports()) {
      ColumnHeader header = report.getColumnHeader();
      List<String> dimensionHeaders = header.getDimensions();
      List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
      List<ReportRow> rows = report.getData().getRows();
      if (rows == null) {
        System.out.println("No data found for " + VIEW_ID);
        return;
      }

      for (ReportRow row : rows) {
        List<String> dimensions = row.getDimensions();
        List<DateRangeValues> metrics = row.getMetrics();
        System.out.println("==========");
        for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
          System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
        }
        for (int j = 0; j < metrics.size(); j++) {
          System.out.print("Date Range (" + j + "): ");
          DateRangeValues values = metrics.get(j);
          for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
            System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
          }
        }
      }
    }
  }
}

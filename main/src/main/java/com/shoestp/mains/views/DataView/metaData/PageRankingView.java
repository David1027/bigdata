package com.shoestp.mains.views.DataView.metaData;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PageRankingView {

  private String pagePath;
  private BigDecimal totalPageViews;
  private BigDecimal totalSession;
  private BigDecimal totalTime;

  public PageRankingView(
      String pagePath, BigDecimal totalPageViews, BigDecimal totalSession, BigDecimal totalTime) {
    this.pagePath = pagePath;
    this.totalPageViews = totalPageViews;
    this.totalSession = totalSession;
    this.totalTime = totalTime;
  }
}

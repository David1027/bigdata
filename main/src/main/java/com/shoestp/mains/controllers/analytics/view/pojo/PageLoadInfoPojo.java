package com.shoestp.mains.controllers.analytics.view.pojo;

import lombok.Data;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 14:03 */
@Data
public class PageLoadInfoPojo {
  private Long navigationStart;
  private Long unloadEventStart;
  private Long unloadEventEnd;
  private Long redirectStart;
  private Long redirectEnd;
  private Long fetchStart;
  private Long domainLookupStart;
  private Long domainLookupEnd;
  private Long connectStart;
  private Long connectEnd;
  private Long secureConnectionStart;
  private Long requestStart;
  private Long responseStart;
  private Long responseEnd;
  private Long domLoading;
  private Long domInteractive;
  private Long domContentLoadedEventStart;
  private Long domContentLoadedEventEnd;
  private Long domComplete;
  private Long loadEventStart;
  private Long loadEventEnd;
}

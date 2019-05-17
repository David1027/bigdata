package com.shoestp.mains.views.DataView.flow;

import lombok.Data;

/**
 * @description: 流量概况参数+占比前端展示类
 * @author: lingjian @Date: 2019/5/14 16:47
 */
@Data
public class PageParameterView {
  /** 平均浏览量 */
  private Double viewAvgCount;
  /** 平均浏览量与昨日的比较值 */
  private Double viewCompareYesterday;
  /** 平均浏览量与上周同一日的比较值 */
  private Double viewCompareWeek;
  /** 跳失率 */
  private Double jumpRate;
  /** 跳失率与昨日的比较值 */
  private Double jumpCompareYesterday;
  /** 跳失率与上周同一日的比较值 */
  private Double jumpCompareWeek;
  /** 平均停留时长 */
  private Double averageStayTime;
  /** 跳失率与昨日的比较值 */
  private Double timeCompareYesterday;
  /** 跳失率与上周同一日的比较值 */
  private Double timeCompareWeek;
}

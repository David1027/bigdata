package com.shoestp.mains.controllers.xwt.dataview.plat.vo.flow;

import lombok.Data;

/**
 * @description: 页面VO
 * @author: lingjian
 * @create: 2020/1/6 9:23
 */
@Data
public class XwtFlowPageVO {

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
  /** 平均停留时长与昨日的比较值 */
  private Double timeCompareYesterday;
  /** 平均停留时长与上周同一日的比较值 */
  private Double timeCompareWeek;
}

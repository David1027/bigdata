package com.shoestp.mains.views.DataView.real;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 实时表
 * @author: lingjian
 * @create: 2019/5/8 9:40
 */
@Data
public class DataViewRealView {
  /** 访客数 */
  private Integer visitorCount;
  /** 访客数与昨日的比较值 */
  private Double visitorCompareYesterday;
  /** 访客数与上周同一日的比较值 */
  private Double visitorCompareWeek;
  /** 浏览量 */
  private Integer viewCount;
  /** 浏览量与昨日的比较值 */
  private Double viewCompareYesterday;
  /** 浏览量与上周同一日的比较值 */
  private Double viewCompareWeek;
  /** 注册量 */
  private Integer registerCount;
  /** 注册量与昨日的比较值 */
  private Double registerCompareYesterday;
  /** 注册量与上周同一日的比较值 */
  private Double registerCompareWeek;
  /** 询盘量 */
  private Integer inquiryCount;
  /** 询盘量与昨日的比较值 */
  private Double inquiryCompareYesterday;
  /** 询盘量与上周同一日的比较值 */
  private Double inquiryCompareWeek;
  /** RFQ数 */
  private Integer rfqCount;
  /** RFQ数与昨日的比较值 */
  private Double rfqCompareYesterday;
  /** RFQ数与上周同一日的比较值 */
  private Double rfqCompareWeek;
  /** 更新时间 */
  private Date updateTime;

}

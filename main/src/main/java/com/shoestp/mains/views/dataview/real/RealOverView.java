package com.shoestp.mains.views.dataview.real;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import lombok.Data;

/**
 * @description: 实时前端展示类
 * @author: lingjian
 * @create: 2019/5/8 9:40
 */
@Data
public class RealOverView {
  /** 访客数 */
  private Integer visitorCount;
  /** 访客数与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double visitorCompareYesterday;
  /** 访客数与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double visitorCompareWeek;
  /** 浏览量 */
  private Integer viewCount;
  /** 浏览量与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double viewCompareYesterday;
  /** 浏览量与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double viewCompareWeek;
  /** 注册量 */
  private Integer registerCount;
  /** 注册量与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double registerCompareYesterday;
  /** 注册量与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double registerCompareWeek;
  /** 询盘量 */
  private Integer inquiryCount;
  /** 询盘量与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double inquiryCompareYesterday;
  /** 询盘量与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double inquiryCompareWeek;
  /** RFQ数 */
  private Integer rfqCount;
  /** RFQ数与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double rfqCompareYesterday;
  /** RFQ数与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double rfqCompareWeek;
}

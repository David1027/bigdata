package com.shoestp.mains.views.dataView.real;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import lombok.Data;

/**
 * @description: 首页整体看板前端展示类
 * @author: lingjian @Date: 2019/5/22 13:44
 */
@Data
public class IndexOverView {
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
  /** 跳失率 */
  private Double jumpRate;
  /** 跳失率与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRateCompareYesterday;
  /** 跳失率与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRateCompareWeek;
}

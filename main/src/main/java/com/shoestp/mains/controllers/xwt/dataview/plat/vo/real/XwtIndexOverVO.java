package com.shoestp.mains.controllers.xwt.dataview.plat.vo.real;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;

import lombok.Data;

/**
 * @description: 首页整体看板前端展示类
 * @author: lingjian
 * @create: 2020/1/3 10:34
 */
@Data
public class XwtIndexOverVO {
  /** 访客数 */
  private Long visitorCount;

  /** 访客数与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double visitorCompareYesterday;

  /** 访客数与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double visitorCompareWeek;

  /** 浏览量 */
  private Long pageViewsCount;

  /** 浏览量与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double viewCompareYesterday;

  /** 浏览量与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double viewCompareWeek;

  /** 注册量 */
  private Long registerCount;

  /** 注册量与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double registerCompareYesterday;

  /** 注册量与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double registerCompareWeek;

  /** 跳失率 */
  private Double jumpRate;

  /** 跳失率与昨日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRateCompareYesterday;

  /** 跳失率与上周同一日的比较值 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRateCompareWeek;
}

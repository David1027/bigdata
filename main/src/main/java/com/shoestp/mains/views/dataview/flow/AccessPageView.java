package com.shoestp.mains.views.dataview.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import lombok.Data;

/**
 * @description: 页面分析时段分布前端展示类
 * @author: lingjian @Date: 2019/5/14 15:35
 */
@Data
public class AccessPageView {
  /** 页面类型：INDEX-首页, DETAIL-商品详情页, SEARCH-搜索结果页, SVS-SVS专题页, CATEGORY-商品分类页, OTHER-其他页 */
  private String accessType;
  /** 访客数 */
  private Integer visitorCount;
  /** 浏览量 */
  private Integer viewCount;
  /** 点击次数 */
  private Integer clickCount;
  /** 点击人数 */
  private Integer clickNumber;
  /** 点击率 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double clickRate;
  /** 跳失率 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRate;
  /** 平均停留时长 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double averageStayTime;
}

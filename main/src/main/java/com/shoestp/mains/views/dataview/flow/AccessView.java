package com.shoestp.mains.views.dataview.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import lombok.Data;

/**
 * @description: 页面分析前端展示类
 * @author: lingjian @Date: 2019/5/14 15:35
 */
@Data
public class AccessView {
  /** 页面类型：INDEX-首页, DETAIL-商品详情页, SEARCH-搜索结果页, SVS-SVS专题页, CATEGORY-商品分类页, OTHER-其他页 */
  private String accessType;
  /** 访客数 */
  private Integer visitorCount;
  /** 访客占比 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double visitorRate;
}

package com.shoestp.mains.views.dataview.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import lombok.Data;

/**
 * @description: 流量概况参数前端展示类
 * @author: lingjian @Date: 2019/5/14 16:47
 */
@Data
public class PageView {
  /** 平均浏览量 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double viewAvgCount;
  /** 跳失率 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRate;
  /** 平均停留时长 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double averageStayTime;
}

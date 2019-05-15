package com.shoestp.mains.views.DataView.flow;

import lombok.Data;

/**
 * @description: 流量概况参数前端展示类
 * @author: lingjian @Date: 2019/5/14 16:47
 */
@Data
public class PageView {
  /** 平均浏览量 */
  private Double viewAvgCount;
  /** 跳失率 */
  private Double jumpRate;
  /** 平均停留时长 */
  private Double averageStayTime;
}

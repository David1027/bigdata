package com.shoestp.mains.views.DataView.flow;

import lombok.Data;

/**
 * @description: 根据来源渠道获取访客数前端展示类
 * @author: lingjian @Date: 2019/5/14 14:21
 */
@Data
public class FlowSourcePageView {
  /** 流量类型 */
  private String sourceType;
  /** 来源渠道 */
  private String sourcePage;
  /** 访客数 */
  private Integer visitorCount;
}

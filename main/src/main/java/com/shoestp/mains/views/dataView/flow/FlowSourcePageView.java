package com.shoestp.mains.views.dataView.flow;

import lombok.Data;

/**
 * @description: 根据来源渠道获取访客数前端展示类
 * @author: lingjian @Date: 2019/5/14 14:21
 */
@Data
public class FlowSourcePageView {
  /** 来源渠道 */
  private String sourcePage;
  /** 访客数 */
  private Integer visitorCount;
  /** 询盘数 */
  private Integer inquiryCount;
}

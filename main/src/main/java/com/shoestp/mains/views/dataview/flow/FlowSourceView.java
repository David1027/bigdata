package com.shoestp.mains.views.dataview.flow;

import lombok.Data;

/**
 * @description: 根据流量来源获取访客数前端展示类
 * @author: lingjian @Date: 2019/5/10 11:28
 */
@Data
public class FlowSourceView {
  /** 流量来源 */
  private String sourceType;
  /** 访客数 */
  private Integer visitorCount;
  /** 询盘数 */
  private Integer inquiryCount;
}

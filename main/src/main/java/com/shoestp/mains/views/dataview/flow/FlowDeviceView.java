package com.shoestp.mains.views.dataview.flow;

import lombok.Data;

/**
 * @description: 设备来源前端展示类
 * @author: lingjian @Date: 2019/5/13 9:57
 */
@Data
public class FlowDeviceView {
  /** 设备来源 */
  private String deviceType;
  /** 访客数 */
  private Integer visitorCount;
}

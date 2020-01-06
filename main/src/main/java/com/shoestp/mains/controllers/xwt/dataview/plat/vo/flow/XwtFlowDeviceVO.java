package com.shoestp.mains.controllers.xwt.dataview.plat.vo.flow;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;

import lombok.Data;

/**
 * @description: 设备来源VO
 * @author: lingjian
 * @create: 2020/1/6 9:23
 */
@Data
public class XwtFlowDeviceVO {
  /** 设备来源 */
  private DeviceTypeEnum deviceType;
  /** 访客数 */
  private Long visitorCount;
}

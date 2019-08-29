package com.shoestp.mains.views.dataview.real;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;

import lombok.Data;

/**
 * @description: ip和设备类型的dto类
 * @author: lingjian
 * @create: 2019/8/29 9:25
 */
@Data
public class VisitorView {
  /** ip地址 */
  private String ip;
  /** 设备来源 */
  private DeviceTypeEnum deviceTypeEnum;
}

package com.shoestp.mains.views.transform;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;

import lombok.Data;

/**
 * @description: 设备和访客的VO
 * @author: lingjian
 * @create: 2020/1/1 9:24
 */
@Data
public class DeviceVisitorVO {
  /** 用户信息表id */
  private Integer memberId;
  /** 设备来源：PC-电脑端，WAP-移动端 */
  private DeviceTypeEnum deviceType;
}

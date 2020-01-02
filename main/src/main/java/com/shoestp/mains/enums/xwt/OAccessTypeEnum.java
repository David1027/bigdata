package com.shoestp.mains.enums.xwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 页面类型
 * @author: lingjian
 * @create: 2019/12/27 10:39
 */
@Getter
@AllArgsConstructor
public enum OAccessTypeEnum {
  /** 首页 */
  INDEX("首页"),
  /** 其他 */
  OTHER("其他");

  /** 描述 */
  private String description;
}

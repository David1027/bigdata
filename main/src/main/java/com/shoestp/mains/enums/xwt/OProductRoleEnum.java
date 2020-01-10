package com.shoestp.mains.enums.xwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 店铺产品角色
 * @author: lingjian
 * @create: 2019/12/30 14:53
 */
@Getter
@AllArgsConstructor
public enum OProductRoleEnum {
  /** 鞋企 */
  SHOP("鞋企"),
  /** 辅料商 */
  MATERIAL("辅料商");
  /** 描述 */
  private String description;
}

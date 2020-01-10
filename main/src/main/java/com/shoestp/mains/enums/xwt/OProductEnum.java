package com.shoestp.mains.enums.xwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 店铺产品枚举类
 * @author: lingjian
 * @create: 2019/12/30 14:53
 */
@Getter
@AllArgsConstructor
public enum OProductEnum {
  /** 鞋企产品 */
  SHOP_PRODUCT("鞋企产品"),
  /** 鞋企店铺 */
  SHOP_ENTERPRISE("鞋企店铺"),
  /** 辅料商产品 */
  MATERIAL_PRODUCT("辅料商产品"),
  /** 辅料商店铺 */
  MATERIAL_ENTERPRISE("辅料商店铺");
  /** 描述 */
  private String description;
}

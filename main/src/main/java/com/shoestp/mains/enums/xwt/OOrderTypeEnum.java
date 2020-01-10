package com.shoestp.mains.enums.xwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 订单类型
 * @author: lingjian
 * @create: 2020/1/9 11:33
 */
@Getter
@AllArgsConstructor
public enum OOrderTypeEnum {
  /** 设计作品订单 */
  DESIGN_ORDERS("设计作品订单"),
  /** 辅料商品订单 */
  MATERIAL_ORDERS("辅料商品订单"),
  /** 成品鞋商品订单 */
  SHOES_ORDERS("成品鞋商品订单");
  /** 描述 */
  private String description;
}

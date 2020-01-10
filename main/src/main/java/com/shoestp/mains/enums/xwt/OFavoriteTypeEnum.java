package com.shoestp.mains.enums.xwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 收藏作品类型
 * @author: lingjian
 * @create: 2020/1/9 11:33
 */
@Getter
@AllArgsConstructor
public enum OFavoriteTypeEnum {
  /** 设计作品 */
  DESIGN("设计作品"),
  /** 辅料商品 */
  MATERIAL("辅料商品"),
  /** 成品鞋商品 */
  SHOES("成品鞋商品");
  /** 描述 */
  private String description;
}

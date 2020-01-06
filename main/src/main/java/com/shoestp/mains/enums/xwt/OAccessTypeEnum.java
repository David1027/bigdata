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
  /** 首页 - /shopMall/ */
  XWT_INDEX("首页"),
  /** 订货会 */
  XWT_ORDERMEET("订货会"),
  /** 采购市场 */
  XWT_PURCHASE("采购市场"),
  /** 寻商机 */
  XWT_COOPERATE("寻商机"),
  /** 鞋设计 */
  XWT_DESIGN("鞋设计"),
  /** 流行趋势 */
  XWT_FASHION("流行趋势"),
  /** 鞋资讯 */
  XWT_NEWS("鞋资讯"),
  /** 其他 */
  OTHER("其他");

  /** 描述 */
  private String description;
}

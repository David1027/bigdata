package com.shoestp.mains.enums.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 页面来源
 * @author: lingjian @Date: 2019/5/14 16:08
 */
@Getter
@AllArgsConstructor
public enum AccessTypeEnum {
  /** 首页 */
  INDEX("首页"),
  /** RFQ页 */
  RFQ("RFQ页"),
  /** 商品详情页 */
  DETAIL("商品详情页"),
  /** 商品列表页 */
  LIST("商品列表页"),
  /** 商品询盘页 */
  INQUIRY("商品询盘页"),
  /** 首页的 DON‘TMISS 3个页 面 */
  DONTMISS("DON‘T MISS页"),
  /** 个人中心首页 */
  MYCENTER("个人中心首页"),
  /** 其他 */
  OTHER("其他页面"),
  /** /home/usr_UsrMain_completeReg */
  USER_REG("用户注册页"),
  /** /home/usr_UsrMessages_center */
  USER_MESSAGE_CENTER("用户中心"),
  /** /seller 商家端 */
  SELLER_ADMIN("商家后台"),
  SUPPLIER_PAGE(" 供应商店铺"),
  /** /platform 平台端 */
  PLATFORM_ADMIN("平台端"),

  LANDING("着陆页");

  private String name;
}

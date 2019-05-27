package com.shoestp.mains.views.sellerdataview.product;

import lombok.Data;

/**
 * @description: 实时排行前端展示类
 * @author: lingjian @Date: 2019/5/27 8:58
 */
@Data
public class MarketView {
  /** 商品名称 */
  private String productName;
  /** 商品图片 */
  private String productImg;
  /** 商品url地址 */
  private String productUrl;
  /** 国家 */
  private String country;
  /** 市场指数 */
  private Integer marketCount;
}

package com.shoestp.mains.controllers.xwt.dataview.seller.vo.product;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @description: 产品VO
 * @author: lingjian
 * @create: 2020/1/10 11:24
 */
@Data
public class XwtSellerProductVO {

  /** 关联店铺id */
  private Integer enterpriseId;
  /** 关联产品id */
  private Integer productId;
  /** 产品名称 */
  private String productName;
  /** 产品图片 */
  private String productImage;
  /** 产品url */
  private String url;
  /** 浏览量 */
  private Long viewCount;
  /** 访问量 */
  private Long visitorCount;
  /** 销售量 */
  private Long salesCount;
  /** 销售金额 */
  private BigDecimal salesPrice;
  /** 收藏量 */
  private Long collectCount;
  /** 市场指数 */
  private Long marketCount;
}

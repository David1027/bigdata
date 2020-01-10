package com.shoestp.mains.controllers.xwt.dataview.seller.vo.enterprise;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @description: 店铺VO
 * @author: lingjian
 * @create: 2020/1/9 16:51
 */
@Data
public class XwtSellerEnterPriseVO {

  /** 关联店铺id */
  private Integer enterpriseId;
  /** 浏览量 */
  private Long viewCount;
  /** 访问量 */
  private Long visitorCount;
  /** 销售量 */
  private Long salesCount;
  /** 销售金额 */
  private BigDecimal salesPrice;
}

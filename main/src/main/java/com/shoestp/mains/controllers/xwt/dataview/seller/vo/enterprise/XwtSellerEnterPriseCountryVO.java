package com.shoestp.mains.controllers.xwt.dataview.seller.vo.enterprise;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.Data;

/**
 * @description: 店铺国家VO
 * @author: lingjian
 * @create: 2020/1/9 16:51
 */
@Data
public class XwtSellerEnterPriseCountryVO {

  /** 关联店铺id */
  private Integer enterpriseId;

  /** 国家id */
  private Integer countryId;

  /** 访问量 */
  private Long visitorCount;
}

package com.shoestp.mains.views.sellerdataview.product;

import lombok.Data;

/**
 * @description: 实时排行前端展示类
 * @author: lingjian @Date: 2019/5/27 8:58
 */
@Data
public class RealRankView {
  /** 供应商ID-usr_main表的ID */
  private Integer supplierId;
  /** 国家 */
  private String country;
  /** 商品名称 */
  private String productName;
  /** 商品图片 */
  private String productImg;
  /** 商品url地址 */
  private String productUrl;
  /** 浏览量 */
  private Integer viewCount;
  /** 询盘量 */
  private Integer inquiryCount;
  /** 访问量 */
  private Integer visitorCount;
  /** 收藏量 */
  private Integer collectCount;
}

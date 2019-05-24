package com.shoestp.mains.entitys.sellerdataview.product;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 商家后台商品表
 * @author: lingjian
 * @create: 2019/5/24 10:21
 */
@Data
@Entity
@Table(name = "seller_data_view_product")
public class SellerDataViewProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 供应商ID-usr_main表的ID */
  @Column(name = "supplier_id")
  private String supplierId;
  /** 国家 */
  @Column(name = "country")
  private String country;
  /** 商品名称 */
  @Column(name = "product_name")
  private String productName;
  /** 商品图片 */
  @Column(name = "product_img")
  private String productImg;
  /** 商品url地址 */
  @Column(name = "product_url")
  private String productUrl;
  /** 浏览量 */
  @Column(name = "view_count")
  private String viewCount;
  /** 询盘量 */
  @Column(name = "inquiry_count")
  private String inquiryCount;
  /** 访问量 */
  @Column(name = "view_count")
  private String visitorCount;
  /** 收藏量 */
  @Column(name = "collect_count")
  private String collectCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

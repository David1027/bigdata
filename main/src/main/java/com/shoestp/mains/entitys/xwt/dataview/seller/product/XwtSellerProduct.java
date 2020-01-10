package com.shoestp.mains.entitys.xwt.dataview.seller.product;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.entitys.xwt.metadata.XwtMetaCountry;

import lombok.Data;

/**
 * @description: 商家后台-商品表
 * @author: lingjian
 * @create: 2020/1/9 14:00
 */
@Data
@Entity
@Table(name = "xwt_data_view_seller_product")
public class XwtSellerProduct {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 关联店铺id */
  @Column(name = "enterprise_id", columnDefinition = "int(11) comment '关联店铺id'")
  private Integer enterpriseId;

  /** 关联产品id */
  @Column(name = "product_id", columnDefinition = "int(11) comment '关联店铺id'")
  private Integer productId;

  /** 产品名称 */
  @Column(name = "product_name", columnDefinition = "varchar(1000) comment '产品名称'")
  private String productName;

  /** 产品图片 */
  @Column(name = "product_image", columnDefinition = "varchar(1000) comment '产品图片'")
  private String productImage;

  /** 页面url */
  @Column(name = "url", columnDefinition = "varchar(255) comment '页面url'")
  private String url;

  /** 关联国家 */
  @ManyToOne
  @JoinColumn(
          name = "country",
          columnDefinition = "int(11) comment '关联国家'",
          updatable = false,
          insertable = false)
  private XwtMetaCountry xwtMetaCountry;

  @Column(name = "country", columnDefinition = "int(11) comment '关联国家'")
  private Integer countryId;

  /** 浏览量 */
  @Column(name = "view_count", columnDefinition = "bigint(20) comment '浏览量'")
  private Long viewCount;

  /** 访问量 */
  @Column(name = "visitor_count", columnDefinition = "bigint(20) comment '访问量'")
  private Long visitorCount;

  /** 销售量 */
  @Column(name = "sales_count", columnDefinition = "bigint(20) comment '销售量'")
  private Long salesCount;

  /** 销售金额 */
  @Column(name = "sales_price", columnDefinition = "decimal(19,2) comment '销售金额'")
  private BigDecimal salesPrice;

  /** 收藏量 */
  @Column(name = "collect_count", columnDefinition = "bigint(20) comment '收藏量'")
  private Long collectCount;

  /** 市场指数 */
  @Column(name = "market_count", columnDefinition = "bigint(20) comment '市场指数'")
  private Long marketCount;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

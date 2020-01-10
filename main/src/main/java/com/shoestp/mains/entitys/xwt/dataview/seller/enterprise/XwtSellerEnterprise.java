package com.shoestp.mains.entitys.xwt.dataview.seller.enterprise;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.entitys.xwt.metadata.XwtMetaCountry;

import lombok.Data;

/**
 * @description: 商家后台-店铺表
 * @author: lingjian
 * @create: 2019/5/24 10:32
 */
@Data
@Entity
@Table(name = "xwt_data_view_seller_enterprise")
public class XwtSellerEnterprise {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 关联店铺id */
  @Column(name = "enterprise_id", columnDefinition = "int(11) comment '关联店铺id'")
  private Integer enterpriseId;

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

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

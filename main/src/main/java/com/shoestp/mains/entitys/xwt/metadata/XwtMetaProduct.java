package com.shoestp.mains.entitys.xwt.metadata;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.enums.xwt.OProductRoleEnum;

import lombok.Data;

/**
 * @description: 产品日志信息
 * @author: lingjian
 * @create: 2019/12/26 10:03
 */
@Data
@Entity
@Table(name = "xwt_meta_data_product")
public class XwtMetaProduct {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 店铺产品角色 */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_role", columnDefinition = "varchar(100) comment '关联店铺id'")
  private OProductRoleEnum productRole;

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

  /** 关联用户信息表 */
  @ManyToOne
  @JoinColumn(
      name = "member_info",
      columnDefinition = "int(11) comment '关联用户信息表'",
      updatable = false,
      insertable = false)
  private XwtMetaMemberInfo memberInfo;

  @Column(name = "member_info", columnDefinition = "int(11) comment '关联用户信息表'")
  private Integer memberInfoId;

  /** 关联国家 */
  @ManyToOne
  @JoinColumn(
      name = "country",
      columnDefinition = "int(11) comment '关联国家'",
      updatable = false,
      insertable = false)
  private XwtMetaCountry country;

  @Column(name = "country", columnDefinition = "int(11) comment '关联国家'")
  private Integer countryId;

  /** 关联省份-中国 */
  @ManyToOne
  @JoinColumn(
      name = "province",
      columnDefinition = "int(11) comment '关联省份'",
      updatable = false,
      insertable = false)
  private XwtMetaProvince province;

  @Column(name = "province", columnDefinition = "int(11) comment '关联省份'")
  private Integer provinceId;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;

  /* ==============================忽略字段============================== */

  /** 访问页面数 */
  @Transient private Long viewCount;
}

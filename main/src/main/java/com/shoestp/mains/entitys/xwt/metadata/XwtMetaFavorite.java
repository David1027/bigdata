package com.shoestp.mains.entitys.xwt.metadata;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.enums.xwt.OFavoriteTypeEnum;
import com.shoestp.mains.enums.xwt.OPlatFormEnum;
import com.shoestp.mains.enums.xwt.ORegisterRoleEnum;

import lombok.Data;

/**
 * @description: 收藏表
 * @author: lingjian
 * @create: 2019/12/31 9:11
 */
@Data
@Entity
@Table(name = "xwt_meta_data_favorite")
public class XwtMetaFavorite {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 平台类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "plat_form", columnDefinition = "varchar(100) comment '平台类型'")
  private OPlatFormEnum platForm;

  /** 关联店铺id */
  @Column(name = "enterprise_id", columnDefinition = "int(11) comment '关联店铺id'")
  private Integer enterpriseId;

  /** 关联产品id */
  @Column(name = "product_id", columnDefinition = "int(11) comment '关联店铺id'")
  private Integer productId;

  /** 收藏id */
  @Column(name = "favorite_id", columnDefinition = "int(11) comment '收藏id'")
  private Integer favoriteId;

  /** 收藏作品类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "favorite_type", columnDefinition = "varchar(100) comment '收藏作品类型'")
  private OFavoriteTypeEnum favoriteType;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

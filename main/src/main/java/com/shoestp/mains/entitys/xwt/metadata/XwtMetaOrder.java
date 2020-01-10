package com.shoestp.mains.entitys.xwt.metadata;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.enums.xwt.OOrderTypeEnum;
import com.shoestp.mains.enums.xwt.OPlatFormEnum;
import com.shoestp.mains.enums.xwt.ORegisterRoleEnum;

import lombok.Data;

/**
 * @description: 订单表
 * @author: lingjian
 * @create: 2019/12/31 9:11
 */
@Data
@Entity
@Table(name = "xwt_meta_data_order")
public class XwtMetaOrder {

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

  /** 订单id */
  @Column(name = "order_id", columnDefinition = "int(11) comment '订单id'")
  private Integer orderId;

  /** 订单金额 */
  @Column(name = "order_price", columnDefinition = "decimal(19,2) comment '订单金额'")
  private BigDecimal orderPrice;

  /** 订单类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "order_type", columnDefinition = "varchar(100) comment '订单类型'")
  private OOrderTypeEnum orderType;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

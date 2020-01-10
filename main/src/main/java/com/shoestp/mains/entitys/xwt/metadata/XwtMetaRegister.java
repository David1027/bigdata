package com.shoestp.mains.entitys.xwt.metadata;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.enums.xwt.OPlatFormEnum;
import com.shoestp.mains.enums.xwt.ORegisterRoleEnum;

import lombok.Data;

/**
 * @description: 注册表
 * @author: lingjian
 * @create: 2019/12/31 9:11
 */
@Data
@Entity
@Table(name = "xwt_meta_data_register")
public class XwtMetaRegister {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 平台类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "plat_form", columnDefinition = "varchar(100) comment '平台类型'")
  private OPlatFormEnum platForm;

  /** 注册id-sso关联id */
  @Column(name = "user_id", columnDefinition = "int(11) comment '注册id-sso关联id'")
  private Integer userId;

  /** 用户角色 */
  @Enumerated(EnumType.STRING)
  @Column(name = "role", columnDefinition = "varchar(100) comment '用户角色'")
  private ORegisterRoleEnum role;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

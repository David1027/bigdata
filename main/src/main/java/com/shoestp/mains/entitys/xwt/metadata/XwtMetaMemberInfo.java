package com.shoestp.mains.entitys.xwt.metadata;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.enums.xwt.OMemberRoleEnum;

import lombok.Data;

/**
 * @description: 用户信息表
 * @author: lingjian
 * @create: 2019/12/30 14:44
 */
@Data
@Entity
@Table(name = "xwt_meta_data_member_info")
public class XwtMetaMemberInfo {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 第三方平台用户id */
  @Column(name = "user_id", columnDefinition = "int(11) comment '第三方平台用户id'")
  private Integer userId;

  /** 用户昵称 */
  @Column(name = "nick_name", columnDefinition = "varchar(255) comment '用户昵称'")
  private String nickName;

  /** 用户邮箱 */
  @Column(name = "email", columnDefinition = "varchar(255) comment '用户邮箱'")
  private String email;

  /** 用户手机号码 */
  @Column(name = "phone", columnDefinition = "varchar(255) comment '用户手机号码'")
  private String phone;

  /** 用户角色 */
  @Enumerated(EnumType.STRING)
  @Column(name = "user_role", columnDefinition = "varchar(100) comment '用户角色'")
  private OMemberRoleEnum userRole;

  /** cookie中的访客id */
  @Column(name = "uv_id", columnDefinition = "varchar(255) comment 'cookie中的访客id'")
  private String uvId;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

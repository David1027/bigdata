package com.shoestp.mains.entitys.metadata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;

import lombok.Data;

@Data
@Entity
@Table(name = "meta_data_user_info")
public class UserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** usr_main表id */
  @Column(name = "user_id")
  private Integer userId;
  /** 用户类型 */
  @Enumerated(EnumType.STRING)
  private RegisterTypeEnum type;
  /** 用户名称-邮箱 */
  private String name;
  /** 性别 */
  @Enumerated(EnumType.STRING)
  private SexEnum sex;
  /** 国家 */
  private String country;
  /** 省 */
  private String province;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

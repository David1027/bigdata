package com.shoestp.mains.entitys.metadata;

import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "meta_data_user_info")
public class UserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 用户类型 */
  @Enumerated(EnumType.STRING)
  private RegisterTypeEnum type;
  /** 性别 */
  @Enumerated(EnumType.STRING)
  private SexEnum sex;
  /** 国家 */
  private String country;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

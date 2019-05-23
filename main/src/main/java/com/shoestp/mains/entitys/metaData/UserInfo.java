package com.shoestp.mains.entitys.metaData;

import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "mete_data_user_info")
public class UserInfo {

  @Id @GeneratedValue private Integer id;

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

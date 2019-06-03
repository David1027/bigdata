package com.shoestp.mains.entitys.metadata;

import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "meta_data_user_info")
public class UserInfo {

  /**
   * 数据库 示例数据 3586 中国 2018-09-18 16:26:00 UNKNOWN SUPPLIER 3587 中国 2018-09-18 16:26:00 UNKNOWN
   * SUPPLIER 3588 中国 2018-09-18 16:26:00 UNKNOWN SUPPLIER 3589 中国 2018-09-18 16:26:00 UNKNOWN
   * SUPPLIER 3590 中国 2018-09-18 16:26:00 UNKNOWN SUPPLIER 3591 中国 2018-09-18 16:26:00 UNKNOWN
   * SUPPLIER 3592 中国 2018-09-18 16:26:00 UNKNOWN SUPPLIER 3593 中国 2018-09-18 16:26:00 UNKNOWN
   * SUPPLIER 3594 中国 2018-09-18 16:26:00 UNKNOWN SUPPLIER 3595 中国 2018-09-18 16:26:00 UNKNOWN
   * SUPPLIER 3596 中国 2018-09-18 16:26:00 UNKNOWN SUPPLIER
   */
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

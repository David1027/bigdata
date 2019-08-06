package com.shoestp.mains.entitys.metadata;

import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.entitys.metadata.enums.SexEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * The type User info.
 *
 * @author lijie
 * @date 2019 /08/05
 * @since
 * @modify Lijie HelloBox@outlook.com 2019-08-05 14:02 新增用户签名
 */
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
  /** 鞋贸港 usr_main表id */
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
  @OneToOne @JoinColumn private PltCountry country;
  /** 省 */
  @OneToOne @JoinColumn private Province province;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
  /**
   * 用户签名,规则为UUID
   *
   * @modify Lijie HelloBox@outlook.com 2019-08-05 14:03 用户签名用于追踪
   */
  private String sign;
}

package com.shoestp.mains.entitys.xwt.metadata;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;

import lombok.Data;

/**
 * @description: 访问日志
 * @author: lingjian
 * @create: 2019/12/26 10:03
 */
@Data
@Entity
@Table(name = "xwt_meta_data_access_log")
public class XwtMetaAccessLog {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 页面标题 */
  @Column(name = "title", columnDefinition = "varchar(1000) comment '页面标题'")
  private String title;

  /** 页面url */
  @Column(name = "url", columnDefinition = "text comment '页面url'")
  private String url;

  /** 跳转页面url */
  @Column(name = "ref", columnDefinition = "text comment '跳转页面url'")
  private String ref;

  /** ip地址 */
  @Column(name = "ip", columnDefinition = "varchar(255) comment 'ip地址'")
  private String ip;

  /** 关联用户信息表 */
  @ManyToOne
  @JoinColumn(
      name = "member_info",
      columnDefinition = "int(11) comment '关联用户信息表'",
      updatable = false,
      insertable = false)
  private XwtMetaMemberInfo xwtMetaMemberInfo;

  @Column(name = "member_info", columnDefinition = "int(11) comment '关联用户信息表'")
  private Integer memberInfoId;

  /** session中的访问id，会话id */
  @Column(name = "ss_id", columnDefinition = "varchar(255) comment 'session中的访问id，会话id'")
  private String ssId;

  /** session有效期内访问页面的次数 */
  @Column(name = "ss_count", columnDefinition = "int(11) comment 'session有效期内访问页面的次数'")
  private Integer ssCount;

  /** session访问时间，会话时间 */
  @Column(name = "ss_time", columnDefinition = "datetime comment 'session访问时间，会话时间'")
  private Date ssTime;

  /** 设备类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "device_type", columnDefinition = "varchar(100) comment '设备类型'")
  private DeviceTypeEnum deviceType;

  /** 来源类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "source_type", columnDefinition = "varchar(100) comment '来源类型'")
  private SourceTypeEnum sourceType;

  /** 页面类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "access_type", columnDefinition = "varchar(100) comment '页面类型'")
  private OAccessTypeEnum accessType;

  /** 关联国家 */
  @ManyToOne
  @JoinColumn(
      name = "xwtMetaCountry",
      columnDefinition = "int(11) comment '关联国家'",
      updatable = false,
      insertable = false)
  private XwtMetaCountry xwtMetaCountry;

  @Column(name = "xwtMetaCountry", columnDefinition = "int(11) comment '关联国家'")
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

  /** 页面停留时长 */
  @Column(name = "time_on_page", columnDefinition = "bigint(20) comment '页面停留时长'")
  private Long timeOnPage;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;

  /* ==============================忽略字段============================== */

  /** uri */
  @Transient private String uri;
  /** 用户登陆id */
  @Transient private String userId;
  /** 用户登陆token */
  @Transient private String token;
  /** cookie中的访客id */
  @Transient private String uvId;
}

package com.shoestp.mains.entitys.metadata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/** 网站访问元数据 */
@Data
@Entity
@Table(name = "meta_data_web_visit_info")
public class WebVisitInfo {
  /** Id表主键 自增 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** * 页面标题 */
  private String title;
  /** * 页面URL */
  @Column(columnDefinition = " text null ")
  private String url;
  /** * 访客UA */
  @Column(name = "user_agent", columnDefinition = " text null ")
  private String userAgent;
  /** * 访客来自于 */
  @Column(columnDefinition = " text null ")
  private String referer;
  /** * 访客IP */
  private String ip;
  /** * Usr_main表里的Id 默认值为-1 -1 为游客 */
  @Column(name = "user_id")
  private Integer userId;
  /** * 访客名称 */
  @Column(name = "visit_name")
  private String visitName;
  /** * 访客位置 国家 */
  @Column(name = "location")
  private String location;
  /** * 省 */
  private String province;
  /** * 商家id usr_main */
  @Column(name = "usr_main_supplier")
  private Integer usrMainSupplier;
  /** 商品图片 */
  private String img;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

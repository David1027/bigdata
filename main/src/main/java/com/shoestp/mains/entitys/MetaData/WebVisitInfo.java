package com.shoestp.mains.entitys.MetaData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/** 网站访问元数据 */
@Data
@Entity
@Table(name = "web_visit_info")
public class WebVisitInfo {
  /** Id表主键 自增 */
  @Id @GeneratedValue private Integer id;
  /** * 页面标题 */
  private String title;
  /** * 页面URL */
  private String url;
  /** * 访客UA */
  @Column(name = "user_agent")
  private String userAgent;
  /** * 访客来自于 */
  private String referer;
  /** * 访客IP */
  private String ip;
  /** * Usr_main表里的Id 默认值为-1 -1 为游客 */
  @Column(name = "user_id")
  private Integer userId;
  /** * 访客名称 */
  @Column(name = "visit_name")
  private String visitName;
  /** * 访客位置 */
  @Column(name = "site")
  private String site;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

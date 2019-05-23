package com.shoestp.mains.entitys.metadata;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
  @Column(name = "location")
  private String location;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

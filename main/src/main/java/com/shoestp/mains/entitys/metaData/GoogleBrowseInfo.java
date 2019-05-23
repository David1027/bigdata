package com.shoestp.mains.entitys.metaData;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * google获取到的页面浏览记录
 *
 * @author xinlian
 */
@Data
@Entity
@Table(name = "meta_data_google_browse_info")
public class GoogleBrowseInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // 页面路径
  @Column(name = "page_path", length = 1000)
  private String pagePath;

  /*// 主机名称
  @Column(name = "host_name", length = 50)
  private String hostName;*/

  // 访问源地址
  @Column(name = "source_page", length = 1000)
  private String sourcePage;

  // 系统设备
  @Column(length = 50)
  private String system;

  // 省
  /*@Column(length = 50)
  private String province;*/

  // 访问时间
  @Column(name = "access_time", length = 50)
  private String accessTime;

  // 访问国家
  @Column(length = 50)
  private String country;

  // 该页面创建的会话数
  @Column(length = 50)
  private String sessions;

  // 跳失率
  @Column(name = "bounce_rate", length = 50)
  private String bounceRate;

  // 平均停留时长
  @Column(name = "time_on_page", length = 50)
  private String timeOnPage;

  // 上一个页面
  @Column(name = "previous_page", length = 1000)
  private String previousPage;

  // 同一会话访问数
  @Column(length = 50)
  private String visitor;

  // 访问次数
  @Column(length = 50)
  private String pageViews;

  // 1PC 2手机
  @Column(name = "sys_type", length = 1000)
  private Integer sysType;

  // 创建时间
  @Column(name = "create_time")
  private Date createTime;
}

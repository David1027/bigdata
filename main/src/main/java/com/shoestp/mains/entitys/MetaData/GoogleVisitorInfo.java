package com.shoestp.mains.entitys.MetaData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * google获取到的session访问记录
 *
 * @author xinlian
 */
@Data
@Entity
@Table(name = "google_visitor_info")
public class GoogleVisitorInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // 用户操作系统
  @Column(length = 50)
  private String system;

  // 访问源
  @Column(length = 1000)
  private String source;

  // 访问时间
  @Column(name = "access_time", length = 50)
  private String accessTime;

  // 访问者国家
  @Column(length = 50)
  private String country;

  // 创建时间
  @Column(name = "create_time")
  private Date createTime;
}

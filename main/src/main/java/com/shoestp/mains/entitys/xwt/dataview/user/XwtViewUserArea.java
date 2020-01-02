package com.shoestp.mains.entitys.xwt.dataview.user;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 用户表
 * @author: lingjian
 * @create: 2019/12/31 15:03
 */
@Data
@Entity
@Table(name = "xwt_data_view_user_area")
public class XwtViewUserArea {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 地域名称 */
  @Column(name = "area", columnDefinition = "varchar(100) comment '地域名称'")
  private String area;

  /** 地域访客人数 */
  @Column(name = "area_count", columnDefinition = "bigint(20) comment '地域访客人数'")
  private Long areaCount;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

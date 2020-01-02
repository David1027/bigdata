package com.shoestp.mains.entitys.xwt.dataview.real;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 实时表
 * @author: lingjian
 * @create: 2019/12/31 14:21
 */
@Data
@Entity
@Table(name = "xwt_data_view_real")
public class XwtViewReal {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** PC端访客数 */
  @Column(name = "visitor_count_pc", columnDefinition = "bigint(20) comment 'PC端访客数'")
  private Long visitorCountPc;

  /** 移动端访客数 */
  @Column(name = "visitor_count_wap", columnDefinition = "bigint(20) comment '移动端访客数'")
  private Long visitorCountWap;

  /** 总访客数 */
  @Column(name = "visitor_count", columnDefinition = "bigint(20) comment '总访客数'")
  private Long visitorCount;

  /** 浏览数 */
  @Column(name = "page_views_count", columnDefinition = "bigint(20) comment '浏览数'")
  private Long pageViewsCount;

  /** 注册量 */
  @Column(name = "register_count", columnDefinition = "bigint(20) comment '浏览数'")
  private Long registerCount;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

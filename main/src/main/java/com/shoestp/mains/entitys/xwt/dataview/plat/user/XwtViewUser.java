package com.shoestp.mains.entitys.xwt.dataview.plat.user;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 用户表
 * @author: lingjian
 * @create: 2019/12/31 14:36
 */
@Data
@Entity
@Table(name = "xwt_data_view_user")
public class XwtViewUser {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 访客数 */
  @Column(name = "visitor_count", columnDefinition = "bigint(20) comment '访客数'")
  private Long visitorCount;

  /** 新用户数 */
  @Column(name = "new_visitor_count", columnDefinition = "bigint(20) comment '新用户数'")
  private Long newVisitorCount;

  /** 老用户数 */
  @Column(name = "old_visitor_count", columnDefinition = "bigint(20) comment '老用户数'")
  private Long oldVisitorCount;

  /** 总注册量 */
  @Column(name = "register_count", columnDefinition = "bigint(20) comment '总注册量'")
  private Long registerCount;

  /** 普通用户数量 */
  @Column(name = "general_count", columnDefinition = "bigint(20) comment '普通用户数量'")
  private Long generalCount;

  /** 鞋企数量 */
  @Column(name = "shoes_count", columnDefinition = "bigint(20) comment '鞋企数量'")
  private Long shoesCount;

  /** 辅料商数量 */
  @Column(name = "material_count", columnDefinition = "bigint(20) comment '总注册量'")
  private Long materialCount;

  /** 设计师数量 */
  @Column(name = "designer_count", columnDefinition = "bigint(20) comment '总注册量'")
  private Long designerCount;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

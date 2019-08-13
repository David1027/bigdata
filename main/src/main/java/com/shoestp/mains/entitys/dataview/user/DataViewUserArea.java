package com.shoestp.mains.entitys.dataview.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @description: 用户表
 * @author: lingjian
 * @create: 2019/5/8 9:02
 */
@Data
@Entity
@Table(name = "data_view_user_area")
public class DataViewUserArea {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 地域名称 */
  @Column(name = "area")
  private String area;
  /** 地域访客人数 */
  @Column(name = "area_count")
  private Integer areaCount;
  /** 地域访客总人数 */
  @Column(name = "area_count_total")
  private Integer areaCountTotal;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

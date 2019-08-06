package com.shoestp.mains.entitys.dataview.real;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 总体数据表
 * @author: lingjian @Date: 2019/8/6 16:46
 */
@Data
@Entity
@Table(name = "data_view_real")
public class DataViewReal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** PC端访客数 */
  @Column(name = "visitor_count_pc")
  private Integer visitorCountPc;
  /** 移动端访客数 */
  @Column(name = "visitor_count_wap")
  private Integer visitorCountWap;
  /** 总访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 浏览数 */
  @Column(name = "page_views_count")
  private Integer pageViewsCount;
  /** 注册量 */
  @Column(name = "register_count")
  private Integer registerCount;
  /** 询盘数 */
  @Column(name = "inquiry_count")
  private Integer inquiryCount;
  /** RFQ数 */
  @Column(name = "rfq_count")
  private Integer rfqCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;

  public DataViewReal() {
    this.visitorCountPc = 0;
    this.visitorCountWap = 0;
    this.visitorCount = 0;
    this.pageViewsCount = 0;
    this.registerCount = 0;
    this.inquiryCount = 0;
    this.rfqCount = 0;
  }
}

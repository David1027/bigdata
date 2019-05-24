package com.shoestp.mains.entitys.dataview.country;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @description: 国家表
 * @author: lingjian
 * @create: 2019/5/8 10:04
 */
@Data
@Entity
@Table(name = "data_view_country")
public class DataViewCountry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 国家名称 */
  @Column(name = "country_name")
  private String countryName;
  /** 国家英文名称 */
  @Column(name = "country_english_name")
  private String countryEnglishName;
  /** 国旗图片 */
  @Column(name = "country_image")
  private String countryImage;
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
  /** 用户人数 */
  @Column(name = "user_count")
  private Integer userCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;

  public DataViewCountry() {
    this.visitorCountPc = 0;
    this.visitorCountWap = 0;
    this.visitorCount = 0;
    this.pageViewsCount = 0;
    this.registerCount = 0;
    this.inquiryCount = 0;
    this.rfqCount = 0;
    this.userCount = 0;
  }
}

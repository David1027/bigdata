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
  /** 访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

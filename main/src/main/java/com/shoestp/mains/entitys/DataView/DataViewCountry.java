package com.shoestp.mains.entitys.DataView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 国家表
 * @author: lingjian
 * @create: 2019/5/8 10:04
 */
@Data
@Entity
@Table(name = "dataview_country")
public class DataViewCountry {
  @Id @GeneratedValue private Integer id;
  /** 国家名称 */
  @Column(name = "country_name")
  private String countryName;
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
}

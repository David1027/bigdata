package com.shoestp.mains.entitys.xwt.dataview.country;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 国家表
 * @author: lingjian
 * @create: 2019/12/31 15:03
 */
@Data
@Entity
@Table(name = "xwt_data_view_country")
public class XwtViewCountry {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 国家名称 */
  @Column(name = "country_name", columnDefinition = "varchar(100) comment '国家名称'")
  private String countryName;

  /** 国家英文名称 */
  @Column(name = "country_english_name", columnDefinition = "varchar(100) comment '国家名称'")
  private String countryEnglishName;

  /** 国旗图片 */
  @Column(name = "country_image", columnDefinition = "varchar(1000) comment '国旗图片'")
  private String countryImage;

  /** 访客数 */
  @Column(name = "visitor_count", columnDefinition = "bigint(20) comment '访客数'")
  private Long visitorCount;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

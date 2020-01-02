package com.shoestp.mains.entitys.xwt.metadata;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 国家表
 * @author: lingjian
 * @create: 2019/12/31 9:11
 */
@Data
@Entity
@Table(name = "xwt_meta_data_country")
public class XwtMetaCountry {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 中文名称 */
  @Column(name = "name", columnDefinition = "varchar(100) comment '中文名称'")
  private String name;

  /** 英文名称 */
  @Column(name = "english_name", columnDefinition = "varchar(100) comment '英文名称'")
  private String englishName;

  /** 英文名称缩写 */
  @Column(name = "short_name", columnDefinition = "varchar(100) comment '英文名称缩写'")
  private String shortName;

  /** 国旗图片 */
  @Column(name = "img", columnDefinition = "varchar(1000) comment '国旗图片'")
  private String img;
}

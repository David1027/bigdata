package com.shoestp.mains.entitys.xwt.metadata;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 省份表 - 只包含中国省份
 * @author: lingjian
 * @create: 2019/12/31 9:17
 */
@Data
@Entity
@Table(name = "xwt_meta_data_province")
public class XwtMetaProvince {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 省份名称 */
  @Column(name = "name", columnDefinition = "varchar(100) comment '省份名称'")
  private String name;
}

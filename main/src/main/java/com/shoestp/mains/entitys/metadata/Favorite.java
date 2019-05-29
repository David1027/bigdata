package com.shoestp.mains.entitys.metadata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "meta_data_favorite")
public class Favorite {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 鞋贸港收藏表pkey */
  private Integer pkey;
  /** 商家id usr_main表 */
  @Column(name = "sup_id")
  private Integer supId;
  /** 产品id */
  @Column(name = "pdt_id")
  private Integer pdtId;
  /** 产品名称 */
  private String name;
  /** 产品图片 */
  private String img;
  /** 国家 根据ip获得 */
  private String country;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

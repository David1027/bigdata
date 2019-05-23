package com.shoestp.mains.entitys.metaData;

import lombok.Data;

import javax.persistence.*;

/**
 * -鞋贸港国家表
 *
 * @author xinlian
 */
@Data
@Entity
@Table(name = "meta_data_country")
public class PltCountry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "short_name", length = 50)
  private String shortName;

  @Column(name = "eng_name", length = 50)
  private String engName;

  @Column(length = 50)
  private String name;
}

package com.shoestp.mains.entitys.MetaData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * -鞋贸港国家表
 *
 * @author xinlian
 */
@Data
@Entity
@Table(name = "country")
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

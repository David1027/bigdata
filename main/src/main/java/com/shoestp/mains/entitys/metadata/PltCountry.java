package com.shoestp.mains.entitys.metadata;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

/**
 * -鞋贸港国家表
 *
 * @author xinlian
 */
@Data
@Entity
@ToString
@Table(name = "meta_data_country")
public class PltCountry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "short_name", length = 50)
  private String shortName;

  @Column(name = "eng_name", length = 50)
  private String engName;

  private String img;

  @Column(length = 50)
  private String name;
  /** 实体类关系映射 */
  @JsonIgnore
  @OneToMany(mappedBy = "country")
  private Set<UserInfo> userInfos;
}

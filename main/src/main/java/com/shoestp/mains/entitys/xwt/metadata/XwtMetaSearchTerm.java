package com.shoestp.mains.entitys.xwt.metadata;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.enums.xwt.OPlatFormEnum;

import lombok.Data;

/**
 * @description: 搜索词表
 * @author: lingjian
 * @create: 2020/1/9 10:37
 */
@Data
@Entity
@Table(name = "xwt_meta_data_search_term")
public class XwtMetaSearchTerm {

  /** Id表主键 自增 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 平台类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "plat_form", columnDefinition = "varchar(100) comment '平台类型'")
  private OPlatFormEnum platForm;

  /** 搜索关键词 */
  @Column(name = "key_word", columnDefinition = "varchar(255) comment '搜索关键词'")
  private String keyWord;

  /** ip地址 */
  @Column(name = "ip", columnDefinition = "varchar(100) comment 'ip地址'")
  private String ip;

  /** 关联国家 */
  @ManyToOne
  @JoinColumn(
      name = "country",
      columnDefinition = "int(11) comment '关联国家'",
      updatable = false,
      insertable = false)
  private XwtMetaCountry xwtMetaCountry;

  @Column(name = "country", columnDefinition = "int(11) comment '关联国家'")
  private Integer countryId;

  /** 关联省份-中国 */
  @ManyToOne
  @JoinColumn(
      name = "province",
      columnDefinition = "int(11) comment '关联省份'",
      updatable = false,
      insertable = false)
  private XwtMetaProvince province;

  @Column(name = "province", columnDefinition = "int(11) comment '关联省份'")
  private Integer provinceId;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

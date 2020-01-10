package com.shoestp.mains.entitys.xwt.dataview.seller.search;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.entitys.xwt.metadata.XwtMetaCountry;
import com.shoestp.mains.enums.xwt.OPlatFormEnum;

import lombok.Data;

/**
 * @description: 商家后台展示-搜索词表
 * @author: lingjian
 * @create: 2020/1/9 15:11
 */
@Data
@Entity
@Table(name = "xwt_data_view_seller_search_term")
public class XwtSellerSearchTerm {
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

  /** 搜索次数 */
  @Column(name = "search_count", columnDefinition = "bigint(20) comment '搜索次数'")
  private Long searchCount;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

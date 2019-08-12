package com.shoestp.mains.entitys.metadata;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
  /** 和UserInfo进行关联,日后可以对该用户收藏进行追踪 */
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserInfo userInfo;
  /** 产品名称 */
  private String name;
  /** 产品图片 */
  private String img;
  /** 国家 根据ip获得 */
  @OneToOne @JoinColumn private PltCountry country;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

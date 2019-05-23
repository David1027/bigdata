package com.shoestp.mains.entitys.metaData;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/** 搜索关键词元数据 */
@Data
@Entity
@Table(name = "meta_data_search_word_info")
public class SearchWordInfo {

  /** Id表主键 自增 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** Ip */
  private String ip;

  /** 搜索关键词 */
  private String keyword;

  /** 搜索人的id,usr_main的pkey 默认值为 -1 */
  @Column(name = "user_id")
  private Integer userId;
  /** 插入时间 */
  @Column(name = "create_time")
  private Date createTime;
}

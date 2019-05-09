package com.shoestp.mains.entitys.MetaData;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/** 搜索关键词元数据 */
@Data
@Builder
@Entity
@Table(name = "search_word_info")
public class SearchWordInfo {

  /** Id表主键 自增 */
  @Id @GeneratedValue private Integer id;
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

package com.shoestp.mains.entitys.DataView;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

/** 搜索关键词元数据 */
@Data
@Builder
@Entity
public class SearchKeyWordInfo {

  /** Id表主键 自增 */
  @Id @GeneratedValue private Integer id;
  /** Ip */
  private String ip;

  /** 搜索关键词 */
  private String keyword;

  /** 搜索人的id,usr_main的pkey 默认值为 -1 */
  private Integer userId;
  /** 插入时间 */
  private Date createTime;
}

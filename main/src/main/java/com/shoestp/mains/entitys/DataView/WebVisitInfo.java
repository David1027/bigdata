package com.shoestp.mains.entitys.DataView;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

/** 网站访问元数据 */
@Data
@Builder
@Entity
public class WebVisitInfo {
  /** Id表主键 自增 */
  @Id @GeneratedValue private Integer id;

  private String title;
  private String url;
  private String userAgent;
  private String referer;
  private String ip;
  private Date createDate;
}

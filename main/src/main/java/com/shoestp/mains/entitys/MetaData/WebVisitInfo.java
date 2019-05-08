package com.shoestp.mains.entitys.MetaData;

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
  /** * 页面标题 */
  private String title;
  /** * 页面URL */
  private String url;
  /** * 访客UA */
  private String userAgent;
  /** * 访客来自于 */
  private String referer;
  /** * 访客IP */
  private String ip;
  /** * Usr_main表里的Id 默认值为-1 -1 为游客 */
  private Integer userId;
  /** * 创建时间 */
  private Date createTime;
}

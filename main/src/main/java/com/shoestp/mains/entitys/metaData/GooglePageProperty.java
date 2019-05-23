package com.shoestp.mains.entitys.metaData;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * google获取的页面属性表
 *
 * @author xinlian
 */
@Data
@Entity
@Table(name = "mete_data_google_page_property")
public class GooglePageProperty {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // 总跳失率
  @Column(name = "bounce_rate", length = 50)
  private String bounceRate;

  // 总浏览量
  @Column(name = "page_views", length = 50)
  private String pageViews;

  // 总平均停留时长
  @Column(name = "avg_time_on_page", length = 50)
  private String avgTimeOnPage;

  // 创建时间
  @Column(name = "create_time")
  private Date createTime;
}

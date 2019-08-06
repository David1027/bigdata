package com.shoestp.mains.entitys.metadata;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 网站访问元数据 访客
 *
 * @author lijie
 * @date 2019 /08/05
 * @modify Lijie HelloBox@outlook.com 2019-08-05 13:59 删除UsrmainId 字段
 */
@Data
@Entity
@Table(name = "meta_data_web_visit_info")
public class WebVisitInfo {
  /** Id表主键 自增 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** * 页面标题 */
  private String title;
  /** * 页面URL */
  @Column(columnDefinition = " text null ")
  private String url;
  /** * 访客UA */
  @Column(name = "user_agent", columnDefinition = " text null ")
  private String userAgent;
  /** * 访客来自于 */
  @Column(columnDefinition = " text null ")
  private String referer;
  /** * 访客IP */
  private String ip;
  /** 多对一关系,用户标识 */
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserInfo userId;
  /** * 访客名称 */
  @Column(name = "visit_name")
  private String visitName;
  /** * 访客位置 国家 */
  @OneToOne
  @JoinColumn(name = "location")
  private PltCountry location;
  /** * 省 */
  @OneToOne @JoinColumn private Province province;
  /** 商品图片 */
  private String img;
  /** * 创建时间 完整的时间戳 2019-05-12 12:40:22 */
  @Column(name = "create_time")
  private Date createTime;

  /**
   * 页面等待时间 用于后期优化页面数据埋点
   *
   * @modify Lijie HelloBox@outlook.com 2019-08-05 13:56 新增字段
   */
  @Column(name = "page_wait_time")
  private Long pageWaitTime;

  /**
   * 页面停留时间
   *
   * @modify Lijie HelloBox@outlook.com 2019-08-05 13:57 新增字段
   */
  @Column(name = "time_on_page")
  private Long timeOnPage;
}

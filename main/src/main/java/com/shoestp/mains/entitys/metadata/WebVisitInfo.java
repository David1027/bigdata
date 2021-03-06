package com.shoestp.mains.entitys.metadata;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 网站访问元数据 访客
 *
 * @author lijie
 * @date 2019 /08/05
 * @since
 * @modify Lijie HelloBox@outlook.com 2019-08-06 10:46 添加设备平台标识,页面URI,
 * @modify Lijie HelloBox@outlook.com 2019-08-06 11:01 添加会话次数
 * @modify Lijie HelloBox@outlook.com 2019-08-06 11:12 添加会话创建时间
 * @modify Lijie HelloBox@outlook.com 2019-08-06 11:38 删除访客名称
 * @modify Lijie HelloBox@outlook.com 2019-08-08 09:57 添加点击次数
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
  /** 页面的URI 没有HOST数据 */
  private String uri;
  /** 页面类型 */
  @Column(name = "page_type")
  @Enumerated(EnumType.STRING)
  private AccessTypeEnum pageType;
  /** * 访客UA */
  @Column(name = "user_agent", columnDefinition = " text null ")
  private String userAgent;
  /** 设备平台标识 */
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "equipment_platform")
  private DeviceTypeEnum equipmentPlatform;

  /** * 访客来自于 Http Referer头 */
  @Column(columnDefinition = " text null ")
  private String referer;
  /** * 访客IP */
  private String ip;
  /** 多对一关系,用户唯一标识,存放于Cookie */
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserInfo userId;
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

  /** 会话Id,当会话超时或者关闭浏览器,该会话id会重新生成 所以会话次数 = 通过count(1) from xxx group by session */
  @Column(name = "session")
  private String session;
  /** 会话创建时间 格式:2019-05-12 12:40:22 */
  @Column(name = "session_create_time")
  private LocalDateTime sessionCreateTime;
  /**
   * The Click count.
   *
   * <p>该页面总的点击次数,现在未对点击元素进行跟踪
   */
  @Column(name = "click_count")
  private Integer clickCount;
}

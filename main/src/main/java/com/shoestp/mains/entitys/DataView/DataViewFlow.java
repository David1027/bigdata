package com.shoestp.mains.entitys.DataView;

import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 流量表
 * @author: lingjian
 * @create: 2019/5/8 9:51
 */
@Data
@Entity
@Builder
@Table(name = "data_view_flow")
public class DataViewFlow {
  @Id @GeneratedValue private Integer id;
  /** 设备类型：PC-电脑端，WAP-移动端 */
  @Enumerated(EnumType.STRING)
  @Column(name = "device_type")
  private DeviceTypeEnum deviceType;
  /** 来源类型：INTERVIEW-自主访问，GOOGLE-谷歌，BAIDU-百度，OTHER-其他 */
  @Enumerated(EnumType.STRING)
  @Column(name = "source_type")
  private SourceTypeEnum sourceType;
  /** 来源渠道 */
  @Column(name = "source_page")
  private String sourcePage;
  /** 访问具体页面 */
  @Column(name = "access_page")
  private String accessPage;
  /** 访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 浏览量 */
  @Column(name = "view_count")
  private Integer viewCount;
  /** 点击率 */
  @Column(name = "click_rate")
  private Double clickRate;
  /** 跳失率 */
  @Column(name = "bounce_rate")
  private Double jumpRate;
  /** 平均停留时长 */
  @Column(name = "average_stay_time")
  private Double averageStayTime;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

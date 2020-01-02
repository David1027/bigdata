package com.shoestp.mains.entitys.xwt.dataview.flow;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import lombok.Data;

/**
 * @description: 流量表
 * @author: lingjian
 * @create: 2019/12/31 14:26
 */
@Data
@Entity
@Table(name = "xwt_data_view_flow")
public class XwtViewFlow {

  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 设备类型: PC-电脑端，WAP-移动端 */
  @Enumerated(EnumType.STRING)
  @Column(name = "device_type", columnDefinition = "varchar(100) comment '设备类型'")
  private DeviceTypeEnum deviceType;

  /** 来源类型: INTERVIEW-自主访问，GOOGLE-谷歌，BAIDU-百度，OTHER-其他 */
  @Enumerated(EnumType.STRING)
  @Column(name = "source_type", columnDefinition = "varchar(100) comment '来源类型'")
  private SourceTypeEnum sourceType;

  /** 访客数 */
  @Column(name = "visitor_count", columnDefinition = "bigint(20) comment '访客数'")
  private Long visitorCount;

  /** 浏览量 */
  @Column(name = "view_count", columnDefinition = "bigint(20) comment '浏览量'")
  private Long viewCount;

  /** 创建时间 */
  @Column(name = "create_time", columnDefinition = "datetime comment '创建时间'")
  private Date createTime;
}

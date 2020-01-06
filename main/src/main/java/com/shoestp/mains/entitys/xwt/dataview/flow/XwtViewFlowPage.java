package com.shoestp.mains.entitys.xwt.dataview.flow;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;

import lombok.Data;

/**
 * @description: 页面分析表
 * @author: lingjian
 * @create: 2019/12/31 14:31
 */
@Data
@Entity
@Table(name = "xwt_data_view_flow_page")
public class XwtViewFlowPage {
  /** 主键 */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** 页面类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "access_type", columnDefinition = "varchar(100) comment '页面类型'")
  private OAccessTypeEnum accessType;

  /** 访客数 */
  @Column(name = "visitor_count", columnDefinition = "bigint(20) comment '访客数'")
  private Long visitorCount;

  /** 浏览量 */
  @Column(name = "view_count", columnDefinition = "bigint(20) comment '浏览量'")
  private Long viewCount;

  /** 点击率：访客数/浏览量 */
  @Column(name = "click_rate", columnDefinition = "double(16,2) comment '点击率'")
  private Double clickRate;

  /** 跳失率 */
  @Column(name = "jump_rate", columnDefinition = "double(16,2) comment '跳失率'")
  private Double jumpRate;

  /** 平均停留时长 */
  @Column(name = "average_stay_time", columnDefinition = "double(16,2) comment '平均停留时长'")
  private Double averageStayTime;

  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;

  /* ==============================忽略字段============================== */
  /** 访客占比 */
  @Transient private Double visitorRate;
  /** 页面名称 */
  @Transient private String accessName;

  /** 平均浏览量 */
  @Transient private Double viewAvgCount;
  /** 平均浏览量与昨日的比较值 */
  @Transient private Double viewCompareYesterday;
  /** 平均浏览量与上周同一日的比较值 */

  @Transient private Double viewCompareWeek;
  /** 跳失率与昨日的比较值 */
  @Transient private Double jumpCompareYesterday;
  /** 跳失率与上周同一日的比较值 */
  @Transient private Double jumpCompareWeek;


  /** 平均停留时长与昨日的比较值 */
  @Transient private Double timeCompareYesterday;
  /** 平均停留时长与上周同一日的比较值 */
  @Transient private Double timeCompareWeek;
}

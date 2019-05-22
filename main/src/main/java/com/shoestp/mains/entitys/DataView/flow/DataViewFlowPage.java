package com.shoestp.mains.entitys.DataView.flow;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shoestp.mains.enums.flow.AccessTypeEnum;

import lombok.Data;

/**
 * @description: 页面分析表
 * @author: lingjian
 * @create: 2019/5/8 9:51
 */
@Data
@Entity
@Table(name = "data_view_flow_page")
public class DataViewFlowPage {
  @Id @GeneratedValue private Integer id;
  /** 页面类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "access_type")
  private AccessTypeEnum accessType;
  /** 访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 浏览量 */
  @Column(name = "view_count")
  private Integer viewCount;
  /** 点击次数 */
  @Column(name = "click_count")
  private Integer clickCount;
  /** 点击人数 */
  @Column(name = "click_number")
  private Integer clickNumber;
  /** 点击率 */
  @Column(name = "click_rate")
  private Double clickRate;
  /** 跳失率 */
  @Column(name = "jump_rate")
  private Double jumpRate;
  /** 平均停留时长 */
  @Column(name = "average_stay_time")
  private Double averageStayTime;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

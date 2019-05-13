package com.shoestp.mains.entitys.DataView;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

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
  @Column(name = "jump_rate")
  private Double jumpRate;
  /** 平均停留时长 */
  @Column(name = "average_stay_time")
  private Double averageStayTime;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

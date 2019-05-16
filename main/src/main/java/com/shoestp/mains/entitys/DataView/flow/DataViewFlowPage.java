package com.shoestp.mains.entitys.DataView.flow;

import java.util.Date;

import javax.persistence.*;

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
  /** 页面类型：INDEX-首页, DETAIL-商品详情页, SEARCH-搜索结果页, SVS-SVS专题页, CATEGORY-商品分类页, OTHER-其他页 */
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
  private Double clickCount;
  /** 点击人数 */
  @Column(name = "click_number")
  private Double clickNumber;
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
package com.shoestp.mains.entitys.DataView.real;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 实时表
 * @author: lingjian
 * @create: 2019/5/8 9:40
 */
@Data
@Entity
@Table(name = "data_view_real")
public class DataViewReal {
  @Id @GeneratedValue private Integer id;
  /** 访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 浏览量 */
  @Column(name = "view_count")
  private Integer viewCount;
  /** 注册量 */
  @Column(name = "register_count")
  private Integer registerCount;
  /** 询盘量 */
  @Column(name = "inquiry_count")
  private Integer inquiryCount;
  /** RFQ数 */
  @Column(name = "rfq_ount")
  private Integer rfqCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

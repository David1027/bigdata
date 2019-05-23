package com.shoestp.mains.entitys.dataView.inquiry;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 询盘表
 * @author: lingjian
 * @create: 2019/5/8 9:43
 */
@Data
@Entity
@Table(name = "data_view_inquiry")
public class DataViewInquiry {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
  /** 访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 询盘数 */
  @Column(name = "inquiry_count")
  private Integer inquiryCount;
  /** 询盘人数 */
  @Column(name = "inquiry_number")
  private Integer inquiryNumber;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

package com.shoestp.mains.entitys.dataView.inquiry;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 询盘排行表
 * @author: lingjian @Date: 2019/5/14 10:43
 */
@Data
@Entity
@Table(name = "data_view_inquiry_rank")
public class DataViewInquiryRank {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
  /** 询盘类型：SUPPLIER-供应商询盘，COMMODITY-商品询盘，SEARCHTERM-热门关键词 */
  @Enumerated(EnumType.STRING)
  @Column(name = "inquiry_type")
  private InquiryTypeEnum inquiryType;
  /** 询盘名称 */
  @Column(name = "inquiry_name")
  private String inquiryName;
  /** 访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 浏览量 */
  @Column(name = "view_count")
  private Integer viewCount;
  /** 询盘数 */
  @Column(name = "inquiry_count")
  private Integer inquiryCount;
  /** 询盘人数 */
  @Column(name = "inquiry_number")
  private Integer inquiryNumber;
  /** 询盘金额 */
  @Column(name = "inquiry_amount")
  private double inquiryAmount;
  /** 商家或商品pkey */
  @Column(name = "pkey")
  private Integer pkey;
  /** 商家或商品图片 */
  private String img;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}
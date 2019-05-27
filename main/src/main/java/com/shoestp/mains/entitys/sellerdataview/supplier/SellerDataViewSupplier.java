package com.shoestp.mains.entitys.sellerdataview.supplier;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 供应商总数据表
 * @author: lingjian
 * @create: 2019/5/24 10:32
 */
@Data
@Entity
@Table(name = "seller_data_view_supplier")
public class SellerDataViewSupplier {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 供应商ID-usr_main表的ID */
  @Column(name = "supplier_id")
  private Integer supplierId;
  /** 国家 */
  @Column(name = "country")
  private String country;
  /** 访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 浏览量 */
  @Column(name = "view_count")
  private Integer viewCount;
  /** 询盘量 */
  @Column(name = "inquiry_count")
  private Integer inquiryCount;
  /** 询盘人数 */
  @Column(name = "inquiry_number")
  private Integer inquiryNumber;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

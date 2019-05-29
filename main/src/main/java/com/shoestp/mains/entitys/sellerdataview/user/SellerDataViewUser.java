package com.shoestp.mains.entitys.sellerdataview.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @description: 商家后台用户表
 * @author: lingjian
 * @create: 2019/5/24 9:58
 */
@Data
@Entity
@Table(name = "seller_data_view_user")
public class SellerDataViewUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** 供应商ID-usr_main表的ID */
  @Column(name = "supplier_id")
  private Integer supplierId;
  /** 访客标记 */
  private String sign;
  /** 国家 */
  @Column(name = "country")
  private String country;
  /** 省份 */
  @Column(name = "province")
  private String province;
  /** 访客名称 */
  @Column(name = "visitor_name")
  private String visitorName;
  /** 访问页面数 */
  @Column(name = "page_count")
  private Integer pageCount;
  /** 询盘数 */
  @Column(name = "inquiry_count")
  private Integer inquiryCount;
  /** 用户关键词 */
  @Column(name = "key_words")
  private String keyWords;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

package com.shoestp.mains.entitys.metadata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;

import lombok.Data;

/** * 从鞋帽港推送过来的数据 */
@Data
@Entity
@Table(name = "meta_data_inquiry_info")
public class InquiryInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /** * 询盘类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private InquiryTypeEnum type;
  /** * 询盘的ID */
  @Column(name = "inquiry_id")
  private Integer inquiryId;
  /** * 来自于那个链接 */
  private String url;
  /** * 来自于那个页面 */
  private String referer;
  /** * 名称 */
  private String name;
  /** * 商家或商品pkey */
  private Integer pkey;
  /** * 金额 */
  private double money;
  /** * ip */
  private String ip;
  /** 国家 */
  private String country;
  /** 图片 */
  private String img;
  /** 商家id usr_main */
  @Column(name = "usr_main_supplier")
  private Integer usrMainSupplier;
  /** 采购商id usr_main */
  @Column(name = "usr_main_purchase")
  private Integer usrMainPurchase;
  /** 商品关键字 推送商品名称 */
  private String keyword;
  /** 设备类型：PC-电脑端，WAP-移动端 */
  @Enumerated(EnumType.STRING)
  @Column(name = "device_type")
  private DeviceTypeEnum deviceType;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

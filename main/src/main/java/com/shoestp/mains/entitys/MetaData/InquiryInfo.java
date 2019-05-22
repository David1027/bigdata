package com.shoestp.mains.entitys.MetaData;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;

import lombok.Data;

/** * 从鞋帽港推送过来的数据 */
@Data
@Entity
@Table(name = "inquiry_info")
public class InquiryInfo {
  @Id @GeneratedValue private Integer id;
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
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

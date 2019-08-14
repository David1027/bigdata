package com.shoestp.mains.entitys.metadata;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
  /** * 询盘的ID 原鞋贸港询盘表的ID */
  @Column(name = "inquiry_id")
  private Integer inquiryId;
  /** * 来自于那个链接,追踪提交询盘入口 */
  private String url;
  /** * 来自于那个页面,追踪从哪个搜索引擎进入 */
  private String referer;
  /** * 名称 产品询盘: 产品标题 ,供应商:供应商名称, 着陆页:供应商名称 */
  private String name;
  /** * 商家或商品pkey */
  private Integer pkey;
  /** * 金额 */
  private Double money;
  /** * ip */
  private String ip;
  /** 国家 */
  @OneToOne
  @JoinColumn(name = "country")
  private PltCountry country;
  /** 图片,商品询盘:商品主图,供应商询盘:供应商Logo,着陆页:供应商Logo */
  private String img;
  /** 询盘发起人ID usr_main */
  @ManyToOne
  @JoinColumn(name = "submit_user")
  private UserInfo submit_user;
  /** 询盘发起人ID usr_main */
  @ManyToOne
  /** 询盘接受人 一般为商家,Usr_main表的Id */
  @JoinColumn(name = "recipient")
  private UserInfo recipient_user;

  /** 设备类型：PC-电脑端，WAP-移动端 */
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "device_type")
  private DeviceTypeEnum deviceType;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

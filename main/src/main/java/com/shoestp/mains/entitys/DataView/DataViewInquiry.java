package com.shoestp.mains.entitys.DataView;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 询盘表
 * @author: lingjian
 * @create: 2019/5/8 9:43
 */
@Data
@Entity
@Builder
@Table(name = "data_view_inquiry")
public class DataViewInquiry {
  @Id @GeneratedValue private Integer id;
  /** 询盘类型：SUPPLIER-供应商询盘，COMMODITY-商品询盘 */
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
  /** 总询盘数 */
  @Column(name = "inquiry_total_count")
  private Integer inquiryTotalCount;
  /** 询盘人数 */
  @Column(name = "inquiry_number")
  private Integer inquiryNumber;
  /** 询盘金额 */
  @Column(name = "inquiry_amount")
  private Integer inquiryAmount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

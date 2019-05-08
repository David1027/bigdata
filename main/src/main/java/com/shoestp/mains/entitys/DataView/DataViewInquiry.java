package com.shoestp.mains.entitys.DataView;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class DataViewInquiry {
  @Id @GeneratedValue private Integer id;
  /** 询盘类型：SUPPLIER-供应商询盘，COMMODITY-商品询盘 */
  @Enumerated(EnumType.STRING)
  private InquiryTypeEnum inquiryType;
  /** 询盘名称 */
  private String inquiryName;
  /** 访客数 */
  private Integer visitorCount;
  /** 浏览量 */
  private Integer viewCount;
  /** 询盘数 */
  private Integer inquiryCount;
  /** 总询盘数 */
  private Integer inquiryTotalCount;
  /** 询盘人数 */
  private Integer inquiryNumber;
  /** 询盘金额 */
  private Integer inquiryAmount;
  /** 创建时间 */
  private Date createTime;
}

package com.shoestp.mains.views.dataView.inquiry;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import lombok.Data;

/**
 * @description: 询盘排行搜索前端展示类
 * @author: lingjian @Date: 2019/5/20 9:37
 */
@Data
public class InquiryTypeView {
  /** 询盘类型 */
  private InquiryTypeEnum inquiryType;
  /** 询盘名称 */
  private String inquiryName;
  /** 访客数 */
  private Integer visitorCount;
  /** 浏览量 */
  private Integer viewCount;
  /** 询盘数 */
  private Integer inquiryCount;
  /** 询盘人数 */
  private Integer inquiryNumber;
  /** 询盘金额 */
  private double inquiryAmount;
}

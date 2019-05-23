package com.shoestp.mains.views.dataView.inquiry;

import lombok.Data;

/**
 * @description: 询盘概况前端展示类
 * @author: lingjian @Date: 2019/5/14 9:27
 */
@Data
public class InquiryView {
  /** 访客数 */
  private Integer visitorCount;
  /** 询盘数 */
  private Integer inquiryCount;
  /** 总询盘数 */
  private Integer totalInquiryCount;
  /** 询盘人数 */
  private Integer inquiryNumber;
}

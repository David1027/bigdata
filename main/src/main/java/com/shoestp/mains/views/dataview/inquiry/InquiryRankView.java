package com.shoestp.mains.views.dataview.inquiry;

import lombok.Data;

/**
 * @description: 询盘排行前端展示类
 * @author: lingjian @Date: 2019/5/14 11:28
 */
@Data
public class InquiryRankView {
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

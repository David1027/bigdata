package com.shoestp.mains.views.sellerdataview.supplier;

import javax.persistence.Column;

import lombok.Data;

/**
 * @description: 实时访客前端展示类
 * @author: lingjian
 * @create: 2019/5/24 14:50
 */
@Data
public class OverviewView {
  /** 供应商ID-usr_main表的ID */
  private Integer supplierId;
  /** 访客数 */
  private Integer visitorCount;
  /** 浏览量 */
  private Integer viewCount;
  /** 询盘量 */
  private Integer inquiryCount;
  /** 询盘人数 */
  private Integer inquiryNumber;
}

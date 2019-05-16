package com.shoestp.mains.views.DataView.real;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

/**
 * @description: 实时前端展示类
 * @author: lingjian
 * @create: 2019/5/8 9:40
 */
@Data
public class RealView {
  /** 访客数 */
  private Integer visitorCount;
  /** 浏览量 */
  private Integer viewCount;
  /** 注册量 */
  private Integer registerCount;
  /** 询盘量 */
  private Integer inquiryCount;
  /** RFQ数 */
  private Integer rfqCount;
}

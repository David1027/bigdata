package com.shoestp.mains.entitys;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

/**
 * @description: 实时表
 * @author: lingjian
 * @create: 2019/5/8 9:40
 */
@Data
@Entity
@Builder
public class DataViewReal {
  @Id @GeneratedValue private Integer id;
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
  /** 创建时间 */
  private Date createTime;
}

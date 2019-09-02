package com.shoestp.mains.views.dataview.real;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import lombok.Data;

/**
 * @description: 常访问页面前端展示类
 * @author: lingjian
 * @create: 2019/8/19 16:42
 */
@Data
public class RealVisitorPageView {
  /** * 页面URL */
  private String url;
  /** 浏览量 */
  private Long viewCount;
  /** 访客数 */
  private Long visitorCount;
  /** 平均停留时长 */
  private Double averageTime;
}

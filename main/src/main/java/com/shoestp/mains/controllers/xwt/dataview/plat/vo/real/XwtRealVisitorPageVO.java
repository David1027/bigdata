package com.shoestp.mains.controllers.xwt.dataview.plat.vo.real;

import lombok.Data;

/**
 * @description: 常访问页面前端展示类
 * @author: lingjian
 * @create: 2019/8/19 16:42
 */
@Data
public class XwtRealVisitorPageVO {
  /** * 页面URL */
  private String url;
  /** 浏览量 */
  private Long viewCount;
  /** 访客数 */
  private Long visitorCount;
  /** 平均停留时长 */
  private Double averageTime;
}

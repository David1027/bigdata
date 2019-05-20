package com.shoestp.mains.views.DataView.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;

import lombok.Data;

/**
 * @description: 流量概况参数连接数据层类
 * @author: lingjian @Date: 2019/5/14 16:47
 */
@Data
public class PageViewObject {
  /** 访客数 */
  private Integer visitorCount;
  /** 浏览量 */
  private Integer viewCount;
  /** 跳失率 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRate;
  /** 平均停留时长 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double averageStayTime;
}

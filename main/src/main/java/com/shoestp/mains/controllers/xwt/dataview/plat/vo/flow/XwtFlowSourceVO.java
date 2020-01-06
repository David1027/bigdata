package com.shoestp.mains.controllers.xwt.dataview.plat.vo.flow;

import com.shoestp.mains.enums.flow.SourceTypeEnum;

import lombok.Data;

/**
 * @description: 设备来源VO
 * @author: lingjian
 * @create: 2020/1/6 9:23
 */
@Data
public class XwtFlowSourceVO {
  /** 来源类型 */
  private SourceTypeEnum sourceType;
  /** 访客数 */
  private Long visitorCount;
  /** 浏览量 */
  private Long viewCount;
}

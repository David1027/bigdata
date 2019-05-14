package com.shoestp.mains.views.DataView.flow;

import java.util.Date;

import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import lombok.Data;

/**
 * @description: 根据流量来源获取访客数前端展示类
 * @author: lingjian @Date: 2019/5/10 11:28
 */
@Data
public class FlowSourceView {
  /** 流量来源 */
  private String sourceType;
  /** 访客数 */
  private Integer visitorCount;
}

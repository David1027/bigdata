package com.shoestp.mains.service.DataView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.views.DataView.DataViewFlowView;

/**
 * @description: 流量-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:10
 */
public interface FlowService {
  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2019/5/10 16:43
   * @return Map<String, List>对象
   */
  Map<String, List> getFlowSource();
}

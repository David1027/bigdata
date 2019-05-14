package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.views.DataView.flow.FlowDeviceView;

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
  Map<String, List> getRealSource();
  /**
   * 根据时间获取设备来源
   *
   * @author: lingjian @Date: 2019/5/13 9:55
   * @param startDate
   * @param endDate
   * @return List<FlowDeviceView>
   */
  List<FlowDeviceView> getFlowDevice(Date startDate, Date endDate);
  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2019/5/14 14:12
   * @param startDate
   * @param endDate
   * @return List
   */
  List getFlowSourceType(Date startDate, Date endDate);

  /**
   * 根据时间获取流量概况
   *
   * @author: lingjian @Date: 2019/5/14 15:07
   * @param date
   * @return Map<String, int[]>
   */
  Map<String, int[]> getFlowSourceTypeTime(Date date);

  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:27
   * @param startDate
   * @param endDate
   * @return Map<String, List>
   */
  Map<String, List> getFlowSourcePage(Date startDate, Date endDate);

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param startDate
   * @param endDate
   * @return List
   */
  List getFlowPageAnalysis(Date startDate, Date endDate);
}

package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.views.DataView.flow.AccessView;
import com.shoestp.mains.views.DataView.flow.FlowDeviceView;
import com.shoestp.mains.views.DataView.flow.FlowSourcePageView;
import com.shoestp.mains.views.DataView.flow.PageParameterView;

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
   * @param date
   * @param num
   * @return List
   */
  List getFlowSourceType(Date date, Integer num);
  /**
   * 根据时间获取流量概况(小时)
   *
   * @author: lingjian @Date: 2019/5/14 15:07
   * @param date
   * @return Map<String, int[]>
   */
  Map<String, Map> getFlowSourceTypeTimeByHour(Date date);
  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2019/5/15 11:42
   * @param date
   * @return
   */
  Map<String, Map> getFlowSourceTypeTimeByDay(int num, Date date);
  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:27
   * @param date
   * @param type
   * @return Map<String, List>
   */
  List getFlowSourcePage(Date date, String type);

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param date
   * @param sourceType
   * @param sourcePage
   * @return
   */
  Map<String, Map> getFlowSourcePageByHour(Date date, SourceTypeEnum sourceType, String sourcePage);

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/17 16:30
   * @param num
   * @param date
   * @param sourceType
   * @param sourcePage
   * @return
   */
  Map<String, Map> getFlowSourcePageByDay(
      int num, Date date, SourceTypeEnum sourceType, String sourcePage);

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param date
   * @param num
   * @return List
   */
  Map<String, List<AccessView>> getFlowPageAnalysis(Date date, Integer num);

  /**
   * 根据时间和页面分类，获取页面分析时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date
   * @param access
   * @return
   */
  Map<String, Map> getFlowPageAnalysisByHour(Date date, AccessTypeEnum access);

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param date
   * @param access
   * @return
   */
  Map<String, Map> getFlowPageAnalysisByDay(int num, Date date, AccessTypeEnum access);

  /**
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/5/14 16:48
   * @param date
   * @return
   */
  PageParameterView getFlowPage(Date date);

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2019/5/15 14:26
   * @param date
   * @return
   */
  Map<String, Map> getFlowPageByMonth(Date date);
}

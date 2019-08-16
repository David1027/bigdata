package com.shoestp.mains.service.dataview;

import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.views.dataview.flow.AccessView;
import com.shoestp.mains.views.dataview.flow.FlowDeviceView;
import com.shoestp.mains.views.dataview.flow.PageParameterView;
import com.shoestp.mains.views.dataview.utils.KeyValue;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description: 流量-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:10
 */
public interface FlowService {
  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2019/5/10 16:43
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Map<String, List>对象
   */
  Map<String, List> getRealSource(Date startDate, Date endDate);
  /**
   * 根据两个时间获取设备来源
   *
   * @author: lingjian @Date: 2019/5/13 9:55
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<FlowDeviceView>
   */
  List<FlowDeviceView> getFlowDevice(Date startDate, Date endDate);

  /**
   * 根据单个时间获取设备来源
   *
   * @param date 时间
   * @param num 数字类型
   * @return List<FlowDeviceView>
   */
  List<FlowDeviceView> getFlowDevice(Date date, Integer num);
  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2019/5/14 14:12
   * @param date 时间
   * @param num 天数
   * @return List
   */
  List getFlowSourceType(Date date, Integer num);
  /**
   * 根据时间获取流量概况(小时)
   *
   * @author: lingjian @Date: 2019/5/14 15:07
   * @param date 时间
   * @return Map<String, int[]>
   */
  Map<String, Map> getFlowSourceTypeTimeByHour(Date date);
  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2019/5/15 11:42
   * @param num 天数
   * @param date 时间
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowSourceTypeTimeByDay(int num, Date date);
  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:27
   * @param date 时间
   * @param num 天数
   * @return Map<String, List>
   */
  List<KeyValue> getFlowSourcePage(Date date, Integer num);

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param date 时间
   * @param sourceType 来源渠道类型
   * @param sourcePage 来源渠道名称
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowSourcePageByHour(Date date, SourceTypeEnum sourceType, String sourcePage);

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/17 16:30
   * @param num 天数
   * @param date 时间
   * @param sourceType 来源渠道类型
   * @param sourcePage 来源渠道名称
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowSourcePageByDay(
      int num, Date date, SourceTypeEnum sourceType, String sourcePage);

  /**
   * 获取所有的页面类型
   *
   * @author: lingjian @Date: 2019/8/14 11:24
   * @return List集合
   */
  List getFlowPageType();
  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param date 时间
   * @param num 天数
   * @return List
   */
  Map<String, List<AccessView>> getFlowPageAnalysis(Date date, Integer num);

  /**
   * 根据时间和页面分类，获取页面分析时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowPageAnalysisByHour(Date date, AccessTypeEnum access);

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param num 天数
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowPageAnalysisByDay(int num, Date date, AccessTypeEnum access);

  /**
   * 根据时间,天数获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/5/14 16:48
   * @param date 时间
   * @param num 天数
   * @return PageParameterView 流量概况参数+占比前端展示类
   */
  PageParameterView getFlowPage(Date date, Integer num);

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2019/5/15 14:26
   * @param date 时间
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowPageByMonth(Date date);
}

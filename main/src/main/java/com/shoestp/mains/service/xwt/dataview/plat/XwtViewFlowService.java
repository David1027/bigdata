package com.shoestp.mains.service.xwt.dataview.plat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlow;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlowPage;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;

/**
 * @description: 流量-服务层接口
 * @author: lingjian
 * @create: 2020/1/6 9:05
 */
public interface XwtViewFlowService {
  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2020/1/6 9:08
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Map<String, List>对象
   */
  Map<String, List> getRealSource(Date startDate, Date endDate);
  /**
   * 根据两个时间获取设备来源
   *
   * @author: lingjian @Date: 2020/1/6 9:18
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<XwtViewFlow> 流量集合对象
   */
  List<XwtViewFlow> getFlowDevice(Date startDate, Date endDate);

  /**
   * 根据单个时间获取设备来源
   *
   * @author: lingjian @Date: 2020/1/6 9:29
   * @param date 时间
   * @param num 数字类型
   * @return List<XwtViewFlow> 流量集合对象
   */
  List<XwtViewFlow> getFlowDevice(Date date, Integer num);
  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2020/1/6 9:31
   * @param date 时间
   * @param num 天数
   * @return List<XwtViewFlow> 流量集合对象
   */
  List<XwtViewFlow> getFlowSourceType(Date date, Integer num);
  /**
   * 根据时间获取流量概况(小时)
   *
   * @author: lingjian @Date: 2020/1/6 9:43
   * @param date 时间
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowSourceTypeTimeByHour(Date date);
  /**
   * 根据时间和来源类型获取流量概况(小时)
   *
   * @author: lingjian @Date: 2020/1/7 10:37
   * @param date 时间
   * @param sourceType 来源类型
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowSourceTypeTimeByHour(Date date, SourceTypeEnum sourceType);
  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2020/1/6 9:59
   * @param num 天数
   * @param date 时间
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowSourceTypeTimeByDay(int num, Date date);
  /**
   * 根据时间和类型获取流量概况(天)
   *
   * @author: lingjian @Date: 2020/1/7 10:56
   * @param num 天数
   * @param date 时间
   * @param sourceType 来源类型
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowSourceTypeTimeByDay(int num, Date date, SourceTypeEnum sourceType);
  /**
   * 获取所有的页面类型
   *
   * @author: lingjian @Date: 2020/1/6 10:16
   * @return List<OAccessTypeEnum> 页面类型集合
   */
  List<OAccessTypeEnum> getFlowPageType();
  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2020/1/6 10:19
   * @param date 时间
   * @param num 天数
   * @return List
   */
  Map<String, List<XwtViewFlowPage>> getFlowPageAnalysis(Date date, Integer num);

  /**
   * 根据时间和页面分类，获取页面分析时段分析(小时)
   *
   * @author: lingjian @Date: 2020/1/6 10:34
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowPageAnalysisByHour(Date date, OAccessTypeEnum access);

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)
   *
   * @author: lingjian @Date: 2020/1/6 10:57
   * @param num 天数
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowPageAnalysisByDay(int num, Date date, OAccessTypeEnum access);

  /**
   * 根据时间,天数获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2020/1/6 11:01
   * @param date 时间
   * @param num 天数
   * @return XwtViewFlowPage 流量页面对象
   */
  XwtViewFlowPage getFlowPage(Date date, Integer num);

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2020/1/6 11:17
   * @param date 时间
   * @return Map<String, Map>
   */
  Map<String, Map> getFlowPageByMonth(Date date);
}

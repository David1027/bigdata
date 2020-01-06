package com.shoestp.mains.service.xwt.dataview.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.constant.dataview.Contants;
import com.shoestp.mains.dao.dataview.flow.FlowDao;
import com.shoestp.mains.dao.dataview.flow.FlowPageDao;
import com.shoestp.mains.dao.xwt.dataview.dao.XwtViewFlowDAO;
import com.shoestp.mains.dao.xwt.dataview.dao.XwtViewFlowPageDAO;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.entitys.xwt.dataview.flow.XwtViewFlow;
import com.shoestp.mains.entitys.xwt.dataview.flow.XwtViewFlowPage;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.service.dataview.FlowService;
import com.shoestp.mains.service.xwt.dataview.XwtViewFlowService;
import com.shoestp.mains.utils.dateUtils.CalculateUtil;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.utils.dateUtils.KeyValueViewUtil;
import com.shoestp.mains.views.dataview.flow.*;
import com.shoestp.mains.views.dataview.utils.KeyValue;

import org.springframework.stereotype.Service;

/**
 * @description: 流量-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:13
 */
@Service
public class XwtViewFlowServiceImpl implements XwtViewFlowService {

  @Resource private FlowDao flowDao;
  @Resource private FlowPageDao flowPageDao;

  @Resource private XwtViewFlowDAO flowDAO;
  @Resource private XwtViewFlowPageDAO flowPageDAO;

  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2020/1/6 9:17
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Map<String, List>
   */
  @Override
  public Map<String, List> getRealSource(Date startDate, Date endDate) {
    Map<String, List> flowViewMap = new HashMap<>(16);
    for (DeviceTypeEnum device : DeviceTypeEnum.values()) {
      // 获取来源数据
      List<XwtViewFlow> list =
          flowDAO.listByCreateTimeAndDevice(
              DateTimeUtil.getTimesOfDay(startDate, 0),
              DateTimeUtil.getTimesOfDay(endDate, 24),
              device);
      flowViewMap.put(device.name(), list);
    }
    return flowViewMap;
  }

  /**
   * 根据时间获取设备来源
   *
   * @author: lingjian @Date: 2019/5/13 9:56
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<XwtViewFlow> 流量集合对象
   */
  @Override
  public List<XwtViewFlow> getFlowDevice(Date startDate, Date endDate) {
    return flowDAO.listByCreateTimeAndGroupDevice(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
  }

  /**
   * 据单个时间获取设备来源
   *
   * @author: lingjian @Date: 2020/1/6 9:29
   * @param date 时间
   * @param num 数字类型
   * @return List<XwtViewFlow> 流量集合对象
   */
  @Override
  public List<XwtViewFlow> getFlowDevice(Date date, Integer num) {
    if (num == null) {
      return getFlowDevice(date, date);
    } else {
      return getFlowDevice(DateTimeUtil.getDayFromNum(date, num), date);
    }
  }

  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2020/1/6 9:36
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<XwtViewFlow> 流量集合对象
   */
  private List<XwtViewFlow> getFlowSourceTypeByDate(Date startDate, Date endDate) {
    return flowDAO.listByCreateTimeAndDevice(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24), null);
  }

  /**
   * 根据时间,天数获取流量来源
   *
   * @author: lingjian @Date: 2020/1/6 9:36
   * @param date 时间
   * @param num 天数类型
   * @return List<XwtViewFlow> 流量集合对象
   */
  @Override
  public List<XwtViewFlow> getFlowSourceType(Date date, Integer num) {
    if (num != null) {
      return getFlowSourceTypeByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getFlowSourceTypeByDate(date, date);
    }
  }

  /**
   * 根据时间获取每个流量来源的访客数
   *
   * @author: lingjian @Date: 2020/1/6 9:57
   * @param date 时间
   * @param start 开始值
   * @param end 结束值
   * @return Map<String, XwtViewFlow>
   */
  private Map<String, XwtViewFlow> getFlowSourceType(Date date, int start, int end) {
    Map<String, XwtViewFlow> flowSourceMap = new HashMap<>(16);
    for (SourceTypeEnum source : SourceTypeEnum.values()) {
      flowSourceMap.put(
          source.name(),
          flowDAO.listByCreateTimeAndSource(
              DateTimeUtil.getTimesOfDay(date, start),
              DateTimeUtil.getTimesOfDay(date, end),
              source));
    }
    return flowSourceMap;
  }

  /**
   * 根据时间获取24个小时中每个小时流量来源的访客数
   *
   * @author: lingjian @Date: 2020/1/6 9:57
   * @param date 时间
   * @return long[]
   */
  private long[] getEveryHour(Date date, SourceTypeEnum source) {
    long[] arr = new long[24];
    for (int i = 0; i < arr.length; i++) {
      if (getFlowSourceType(date, i, i + 1).get(source.toString()) != null) {
        arr[i] = getFlowSourceType(date, i, i + 1).get(source.toString()).getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取流量概况(小时)
   *
   * @author: lingjian @Date: 2020/1/6 9:57
   * @param date 时间
   * @return Map<String, List>
   */
  private Map<String, List<KeyValue>> getFlowSourceTypeTimeByHourMap(Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    for (SourceTypeEnum source : SourceTypeEnum.values()) {
      keyValues.add(KeyValueViewUtil.getFlowKeyValue(source.getName(), getEveryHour(date, source)));
    }
    Map<String, List<KeyValue>> flowTimeHourMap = new HashMap<>(16);
    flowTimeHourMap.put(Contants.TODAY, keyValues);
    return flowTimeHourMap;
  }

  /**
   * 根据时间获取流量概况(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2020/1/6 9:46
   * @param date 时间
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowSourceTypeTimeByHour(Date date) {
    Map<String, Map> flowTimeHourMap = new HashMap<>(16);
    flowTimeHourMap.put(Contants.ABSCISSA, DateTimeUtil.getHourAbscissa(1));
    flowTimeHourMap.put(Contants.SOURCE_TYPE, getFlowSourceTypeTimeByHourMap(date));
    return flowTimeHourMap;
  }

  /**
   * 根据时间获取某一天开始的前一周的每一天的流量来源的访客数
   *
   * @author: lingjian @Date: 2020/1/6 10:02
   * @param date 时间
   * @param source 来源类型
   * @return long[]
   */
  private long[] getEveryDay(int num, Date date, SourceTypeEnum source) {
    long[] arr = new long[num];
    for (int i = 0; i < arr.length; i++) {
      if (getFlowSourceType(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(source.toString())
          != null) {
        arr[i] =
            getFlowSourceType(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(source.toString())
                .getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2020/1/6 10:02
   * @param num 天数
   * @param date 时间
   * @return Map<String, List>
   */
  private Map<String, List<KeyValue>> getFlowSourceTypeTimeByDayMap(int num, Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    for (SourceTypeEnum source : SourceTypeEnum.values()) {
      keyValues.add(
          KeyValueViewUtil.getFlowKeyValue(source.getName(), getEveryDay(num, date, source)));
    }
    Map<String, List<KeyValue>> flowTimeDayMap = new HashMap<>(16);
    flowTimeDayMap.put("day", keyValues);
    return flowTimeDayMap;
  }

  /**
   * 根据时间获取流量概况(天)+横坐标(天)
   *
   * @author: lingjian @Date: 2020/1/6 10:05
   * @param date 时间
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowSourceTypeTimeByDay(int num, Date date) {
    Map<String, Map> flowTimeDayMap = new HashMap<>(16);
    flowTimeDayMap.put(Contants.ABSCISSA, DateTimeUtil.getDayAbscissa(num, date));
    flowTimeDayMap.put(Contants.SOURCE_TYPE, getFlowSourceTypeTimeByDayMap(num, date));
    return flowTimeDayMap;
  }

  /**
   * 获取所有的页面类型
   *
   * @author: lingjian @Date: 2020/1/6 10:16
   * @return List<AccessTypeEnum> 页面类型集合
   */
  @Override
  public List<OAccessTypeEnum> getFlowPageType() {
    List<OAccessTypeEnum> list = new ArrayList<>();
    Collections.addAll(list, OAccessTypeEnum.values());
    return list;
  }

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2020/1/6 10:18
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Map<String, List<XwtViewFlowPage>>
   */
  private Map<String, List<XwtViewFlowPage>> getFlowPageAnalysisByDate(
      Date startDate, Date endDate) {
    Map<String, List<XwtViewFlowPage>> accessPageMap = new HashMap<>(16);
    List<XwtViewFlowPage> list = new ArrayList<>();
    for (OAccessTypeEnum access : OAccessTypeEnum.values()) {
      XwtViewFlowPage flowPage = new XwtViewFlowPage();
      // 页面类型
      flowPage.setAccessType(access);
      flowPage.setAccessName(access.getDescription());
      // 访客数
      Long visitor =
          flowPageDAO.countByCreateTimeAndAccess(
              DateTimeUtil.getTimesOfDay(startDate, 0),
              DateTimeUtil.getTimesOfDay(endDate, 24),
              access);
      flowPage.setVisitorCount(visitor);
      // 访客占比率
      // 一天内的总访客数
      Long visitorTotal =
          flowPageDAO.countByCreateTimeAndAccess(
              DateTimeUtil.getTimesOfDay(startDate, 0),
              DateTimeUtil.getTimesOfDay(endDate, 24),
              null);
      // 占比率
      flowPage.setVisitorRate(
          CustomDoubleSerialize.setDouble(CalculateUtil.getExcept(visitor, visitorTotal)));
      list.add(flowPage);
    }
    accessPageMap.put(Contants.ACCESS, list);
    return accessPageMap;
  }

  /**
   * 根据时间，日期类型获取页面分析
   *
   * @author: lingjian @Date: 2020/1/6 10:32
   * @param date 时间
   * @param num 天数
   * @return Map<String, List<XwtViewFlowPage>>
   */
  @Override
  public Map<String, List<XwtViewFlowPage>> getFlowPageAnalysis(Date date, Integer num) {
    if (num != null) {
      return getFlowPageAnalysisByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getFlowPageAnalysisByDate(date, date);
    }
  }

  /**
   * 根据指定时间，开始小时，结束小时，获取页面分析参数
   *
   * @author: lingjian @Date: 2020/1/6 10:50
   * @param access 页面类型
   * @param date 时间
   * @param start 开始值
   * @param end 结束值
   * @return XwtViewFlowPage 流量页面对象
   */
  private XwtViewFlowPage getFlowPageAnalysisByAccess(
      OAccessTypeEnum access, Date date, int start, int end) {
    XwtViewFlowPage flowPage =
        flowPageDAO.listByCreateTimeAndAccess(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end), access);
    return isNullTo(flowPage);
  }

  /**
   * 根据时间和页面分类，获取一天24小时每个小时的页面参数
   *
   * @author: lingjian @Date: 2020/1/6 10:51
   * @param date 时间
   * @param access 页面类型
   * @param parameter 页面类型的关键词
   * @return double[]
   */
  private double[] getAccessHour(Date date, OAccessTypeEnum access, String parameter) {
    double[] arr = new double[24];
    for (int i = 0; i < arr.length; i++) {
      if (Contants.VISITOR.equals(parameter)
          && getFlowPageAnalysisByAccess(access, date, i, i + 1) != null) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).getVisitorCount();
      } else if (Contants.VIEW.equals(parameter)
          && getFlowPageAnalysisByAccess(access, date, i, i + 1) != null) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).getViewCount();
      } else if (Contants.CLICK_RATE.equals(parameter)
          && getFlowPageAnalysisByAccess(access, date, i, i + 1) != null) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).getClickRate();
      } else if (Contants.JUMP.equals(parameter)
          && getFlowPageAnalysisByAccess(access, date, i, i + 1) != null) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).getJumpRate();
      } else if (Contants.AVERAGE_STAY_TIME.equals(parameter)
          && getFlowPageAnalysisByAccess(access, date, i, i + 1) != null) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(小时)
   *
   * @author: lingjian @Date: 2020/1/6 10:54
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, List<KeyValue>>
   */
  private Map<String, List<KeyValue>> getFlowPageAnalysisByHourMap(
      Date date, OAccessTypeEnum access) {
    List<KeyValue> keyValues = new ArrayList<>();
    String[] arr = {
      Contants.VISITOR,
      Contants.VIEW,
      Contants.CLICK_RATE,
      Contants.JUMP,
      Contants.AVERAGE_STAY_TIME
    };
    for (String parameter : arr) {
      keyValues.add(
          KeyValueViewUtil.getFlowKeyValue(parameter, getAccessHour(date, access, parameter)));
    }
    Map<String, List<KeyValue>> analysisHourMap = new HashMap<>(16);
    analysisHourMap.put(Contants.TODAY, keyValues);
    return analysisHourMap;
  }

  /**
   * * 根据时间和页面分类，获取页面分析时段分析(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowPageAnalysisByHour(Date date, OAccessTypeEnum access) {
    Map<String, Map> analysisHourMap = new HashMap<>(16);
    analysisHourMap.put(Contants.ABSCISSA, DateTimeUtil.getHourAbscissa(1));
    analysisHourMap.put(Contants.ANALYSIS, getFlowPageAnalysisByHourMap(date, access));
    return analysisHourMap;
  }

  /**
   * 根据时间和页面，获取某几天每一天的页面参数
   *
   * @author: lingjian @Date: 2020/1/6 10:59
   * @param num 某几天
   * @param date 时间
   * @param access 页面类型
   * @param parameter 页面类型的关键词
   * @return double[]
   */
  private double[] getAccessDay(int num, Date date, OAccessTypeEnum access, String parameter) {
    double[] arr = new double[num];
    for (int i = 0; i < arr.length; i++) {
      if (Contants.VISITOR.equals(parameter)
          && getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              != null) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .getVisitorCount();
      } else if (Contants.VIEW.equals(parameter)
          && getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              != null) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .getViewCount();
      } else if (Contants.CLICK_RATE.equals(parameter)
          && getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              != null) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .getClickRate();
      } else if (Contants.JUMP.equals(parameter)
          && getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              != null) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .getJumpRate();
      } else if (Contants.AVERAGE_STAY_TIME.equals(parameter)
          && getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              != null) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/17 14:32
   * @param num 天数
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, List<KeyValue>>
   */
  private Map<String, List<KeyValue>> getFlowPageAnalysisByDayMap(
      int num, Date date, OAccessTypeEnum access) {
    List<KeyValue> keyValues = new ArrayList<>();
    String[] arr = {
      Contants.VISITOR,
      Contants.VIEW,
      Contants.CLICK_RATE,
      Contants.JUMP,
      Contants.AVERAGE_STAY_TIME
    };
    for (String parameter : arr) {
      keyValues.add(
          KeyValueViewUtil.getFlowKeyValue(parameter, getAccessDay(num, date, access, parameter)));
    }
    Map<String, List<KeyValue>> analysisDayMap = new HashMap<>(16);
    analysisDayMap.put(Contants.TODAY, keyValues);
    return analysisDayMap;
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)+横坐标(天)
   *
   * @author: lingjian @Date: 2020/1/6 10:56
   * @param num 天数类型
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowPageAnalysisByDay(int num, Date date, OAccessTypeEnum access) {
    Map<String, Map> analysisDayMap = new HashMap<>(16);
    analysisDayMap.put(Contants.ABSCISSA, DateTimeUtil.getDayAbscissa(num, date));
    analysisDayMap.put(Contants.ANALYSIS, getFlowPageAnalysisByDayMap(num, date, access));
    return analysisDayMap;
  }

  /**
   * 判断非空处理
   *
   * @author: lingjian @Date: 2020/1/6 11:06
   * @param page 流量页面对象
   * @return XwtViewFlowPage 流量页面对象
   */
  private XwtViewFlowPage isNullTo(XwtViewFlowPage page) {
    if (page.getVisitorCount() == null) {
      page.setVisitorCount(0L);
      page.setViewCount(0L);
      page.setClickRate(0.0);
      page.setJumpRate(0.0);
      page.setAverageStayTime(0.0);
    }
    return page;
  }

  /**
   * 根据时间获取流量概况参数(访客数，浏览量，跳失率，平均停留时长)
   *
   * @author: lingjian @Date: 2020/1/6 13:34
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return PageViewObject 流量概况参数连接数据层类
   */
  private XwtViewFlowPage getFlowPageObject(Date startDate, Date endDate) {
    return isNullTo(
        flowPageDAO.listByCreateTimeAndAccess(
            DateTimeUtil.getTimesOfDay(startDate, 0),
            DateTimeUtil.getTimesOfDay(endDate, 24),
            null));
  }

  /**
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2020/1/6 13:34
   * @param date 时间
   * @return PageParameterView 流量概况参数+占比前端展示类
   */
  private XwtViewFlowPage getFlowPageByDate(Date date, Integer num) {
    // 当天的值
    XwtViewFlowPage today = getFlowPageObject(DateTimeUtil.getDayFromNum(date, num), date);
    // 昨天的值
    XwtViewFlowPage yesterday =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(date, 1 + num), DateTimeUtil.getDayFromNum(date, 1));
    // 上周的值
    XwtViewFlowPage week =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(date, 7 + num), DateTimeUtil.getDayFromNum(date, 7));

    // 计算(浏览量 / 访客数)
    Double compareToday = CalculateUtil.getExcept(today.getViewCount(), today.getVisitorCount());
    Double compareYesterday =
        CalculateUtil.getExcept(yesterday.getViewCount(), yesterday.getVisitorCount());
    Double compareWeek = CalculateUtil.getExcept(week.getViewCount(), week.getVisitorCount());

    // 流量量，与昨日的比较值，与上周同一日的比较值
    today.setViewAvgCount(CustomDoubleSerialize.setDouble(compareToday));
    today.setViewCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(compareToday, compareYesterday)));
    today.setViewCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(compareToday, compareWeek)));

    // 跳失率，与昨日的比较值，与上周同一日的比较值
    today.setJumpRate(CustomDoubleSerialize.setDouble(today.getJumpRate()));
    today.setJumpCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(today.getJumpRate(), yesterday.getJumpRate())));
    today.setJumpCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(today.getJumpRate(), week.getJumpRate())));

    // 平均停留时长，与昨日的比较值，与上周同一日的比较值
    today.setAverageStayTime(CustomDoubleSerialize.setDouble(today.getAverageStayTime()));
    today.setTimeCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                today.getAverageStayTime(), yesterday.getAverageStayTime())));
    today.setTimeCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                today.getAverageStayTime(), week.getAverageStayTime())));
    return today;
  }

  /**
   * 根据时间,天数获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2020/1/6 13:34
   * @param date 时间
   * @param num 天数
   * @return PageParameterView 流量概况参数+占比前端展示类
   */
  @Override
  public XwtViewFlowPage getFlowPage(Date date, Integer num) {
    if (num != null) {
      return getFlowPageByDate(date, num);
    } else {
      return getFlowPageByDate(date, 0);
    }
  }

  /**
   * 根据时间获取流量概况参数
   *
   * @author: lingjian @Date: 2020/1/6 13:34
   * @param date 时间
   * @return List<PageView>
   */
  private XwtViewFlowPage getFlowPageParameter(Date date) {
    XwtViewFlowPage result =
        isNullTo(
            flowPageDAO.listByCreateTimeAndAccess(
                DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24), null));
    // 浏览量
    result.setViewAvgCount(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getExcept(result.getViewCount(), result.getVisitorCount())));
    // 跳失率
    result.setJumpRate(CustomDoubleSerialize.setDouble(result.getJumpRate()));
    // 平均停留时长
    result.setAverageStayTime(CustomDoubleSerialize.setDouble(result.getAverageStayTime()));
    return result;
  }

  /**
   * 根据时间，参数，获取三十天中每一天的跳失率、人均流量量、平均停留时长
   *
   * @author: lingjian @Date: 2020/1/6 13:34
   * @param date 时间
   * @param parameter 类型关键词
   * @return double[]
   */
  private double[] getEveryPage(Date date, String parameter) {
    double[] arr = new double[30];
    for (int i = 0; i < arr.length; i++) {
      if (Contants.VIEW_AVG_COUNT.equals(parameter)) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1)).getViewAvgCount();
      } else if (Contants.JUMP.equals(parameter)) {
        arr[i] = getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1)).getJumpRate();
      } else if (Contants.AVERAGE_STAY_TIME.equals(parameter)) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1)).getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2020/1/6 13:34
   * @param date 时间
   * @return Map<String, List<KeyValue>>
   */
  private Map<String, List<KeyValue>> getFlowPageByMonthMap(Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    String[] arr = {Contants.VIEW_AVG_COUNT, Contants.JUMP, Contants.AVERAGE_STAY_TIME};
    for (String parameter : arr) {
      keyValues.add(KeyValueViewUtil.getFlowKeyValue(parameter, getEveryPage(date, parameter)));
    }
    Map<String, List<KeyValue>> flowPageMonthMap = new HashMap<>(16);
    flowPageMonthMap.put(Contants.MONTH, keyValues);
    return flowPageMonthMap;
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数+横坐标(天)
   *
   * @author: lingjian @Date: 2020/1/6 11:18
   * @param date 时间
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowPageByMonth(Date date) {
    Map<String, Map> flowPageMonthMap = new HashMap<>(16);
    flowPageMonthMap.put(Contants.ANALYSIS, DateTimeUtil.getDayAbscissa(30, date));
    flowPageMonthMap.put(Contants.FLOW_PAGE, getFlowPageByMonthMap(date));
    return flowPageMonthMap;
  }
}

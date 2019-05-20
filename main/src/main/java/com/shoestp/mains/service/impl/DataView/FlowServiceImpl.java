package com.shoestp.mains.service.impl.DataView;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoestp.mains.dao.DataView.flow.FlowDao;
import com.shoestp.mains.dao.DataView.flow.FlowPageDao;
import com.shoestp.mains.dao.metaData.GoogleBrowseInfoDao;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.service.DataView.FlowService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.utils.dateUtils.KeyValueViewUtil;
import com.shoestp.mains.views.DataView.flow.*;
import com.shoestp.mains.views.DataView.utils.KeyValue;

/**
 * @description: 流量-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:13
 */
@Service
public class FlowServiceImpl implements FlowService {

  @Resource private FlowDao flowDao;
  @Resource private FlowPageDao flowPageDao;
  @Autowired private GoogleBrowseInfoDao googleBrowseInfoDao;

  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2019/5/10 16:44
   * @return Map<String, List>
   */
  @Override
  public Map<String, List> getRealSource() {
    Map<String, List> flowViewMap = new HashMap<>();
    for (DeviceTypeEnum device : DeviceTypeEnum.values()) {
      flowViewMap.put(
          device.name(),
          flowDao
              .findAllByDeviceType(
                  device, DateTimeUtil.getTimesmorning(), DateTimeUtil.getTimesnight())
              .stream()
              .map(
                  bean -> {
                    FlowSourceView flowView = new FlowSourceView();
                    flowView.setSourceType(bean.get(0, String.class));
                    flowView.setVisitorCount(bean.get(1, Integer.class));
                    return flowView;
                  })
              .collect(Collectors.toList()));
    }
    return flowViewMap;
  }

  /**
   * 根据时间获取设备来源
   *
   * @author: lingjian @Date: 2019/5/13 9:56
   * @param date
   * @param type
   * @return List<FlowDeviceView>
   */
  @Override
  public List<FlowDeviceView> getFlowDevice(Date date, String type) {
    List<FlowDeviceView> flowDeviceViewList = null;
    if ("week".equals(type)) {
      flowDeviceViewList =
          flowDao
              .findAllByDeviceCount(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 7), 0),
                  DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    FlowDeviceView flowDeviceView = new FlowDeviceView();
                    flowDeviceView.setDeviceType(bean.get(0, String.class));
                    flowDeviceView.setVisitorCount(bean.get(1, Integer.class));
                    return flowDeviceView;
                  })
              .collect(Collectors.toList());
    } else if ("month".equals(type)) {
      flowDeviceViewList =
          flowDao
              .findAllByDeviceCount(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 30), 0),
                  DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    FlowDeviceView flowDeviceView = new FlowDeviceView();
                    flowDeviceView.setDeviceType(bean.get(0, String.class));
                    flowDeviceView.setVisitorCount(bean.get(1, Integer.class));
                    return flowDeviceView;
                  })
              .collect(Collectors.toList());
    } else {
      flowDeviceViewList =
          flowDao
              .findAllByDeviceCount(
                  DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    FlowDeviceView flowDeviceView = new FlowDeviceView();
                    flowDeviceView.setDeviceType(bean.get(0, String.class));
                    flowDeviceView.setVisitorCount(bean.get(1, Integer.class));
                    return flowDeviceView;
                  })
              .collect(Collectors.toList());
    }
    return flowDeviceViewList;
  }

  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2019/5/14 14:12
   * @param date
   * @param type
   * @return
   */
  @Override
  public List<FlowSourceView> getFlowSourceType(Date date, String type) {
    List<FlowSourceView> list = null;
    if ("week".equals(type)) {
      list =
          flowDao
              .findAllBySourceType(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 7), 0),
                  DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    FlowSourceView flowSourceView = new FlowSourceView();
                    flowSourceView.setSourceType(bean.get(0, String.class));
                    flowSourceView.setVisitorCount(bean.get(1, Integer.class));
                    return flowSourceView;
                  })
              .collect(Collectors.toList());
    } else if ("month".equals(type)) {
      list =
          flowDao
              .findAllBySourceType(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 30), 0),
                  DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    FlowSourceView flowSourceView = new FlowSourceView();
                    flowSourceView.setSourceType(bean.get(0, String.class));
                    flowSourceView.setVisitorCount(bean.get(1, Integer.class));
                    return flowSourceView;
                  })
              .collect(Collectors.toList());
    } else {
      list =
          flowDao
              .findAllBySourceType(
                  DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    FlowSourceView flowSourceView = new FlowSourceView();
                    flowSourceView.setSourceType(bean.get(0, String.class));
                    flowSourceView.setVisitorCount(bean.get(1, Integer.class));
                    return flowSourceView;
                  })
              .collect(Collectors.toList());
    }
    return list;
  }

  /**
   * 根据时间获取每个流量来源的访客数
   *
   * @author: lingjian @Date: 2019/5/14 15:08
   * @param date
   * @param start
   * @param end
   * @return
   */
  public Map<String, List<FlowSourceView>> getFlowSourceType(Date date, int start, int end) {
    Map<String, List<FlowSourceView>> flowSourceMap = new HashMap<>();
    for (SourceTypeEnum source : SourceTypeEnum.values()) {
      flowSourceMap.put(
          source.name(),
          flowDao
              .findAllBySource(
                  source,
                  DateTimeUtil.getTimesOfDay(date, start),
                  DateTimeUtil.getTimesOfDay(date, end))
              .stream()
              .map(
                  bean -> {
                    FlowSourceView flowSourceView = new FlowSourceView();
                    flowSourceView.setSourceType(bean.get(0, String.class));
                    flowSourceView.setVisitorCount(bean.get(1, Integer.class));
                    return flowSourceView;
                  })
              .collect(Collectors.toList()));
    }
    return flowSourceMap;
  }

  /**
   * 根据时间获取24个小时中每个小时流量来源的访客数
   *
   * @author: lingjian @Date: 2019/5/14 15:08
   * @param date
   * @return
   */
  public int[] getEveryHour(Date date, SourceTypeEnum source) {
    int[] arr = new int[24];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowSourceType(date, i, i + 1).get(source.toString()).isEmpty()) {
        arr[i] = getFlowSourceType(date, i, i + 1).get(source.toString()).get(0).getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取某一天开始的前一周的每一天的流量来源的访客数
   *
   * @author: lingjian @Date: 2019/5/15 11:43
   * @param date
   * @param source
   * @return
   */
  public int[] getEveryDay(int num, Date date, SourceTypeEnum source) {
    int[] arr = new int[num];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowSourceType(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
          .get(source.toString())
          .isEmpty()) {
        arr[i] =
            getFlowSourceType(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(source.toString())
                .get(0)
                .getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取流量概况(小时)
   *
   * @author: lingjian @Date: 2019/5/17 14:43
   * @param date
   * @return
   */
  public Map<String, List> getFlowSourceTypeTimeByHourMap(Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("BAIDU", getEveryHour(date, SourceTypeEnum.BAIDU)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("GOOGLE", getEveryHour(date, SourceTypeEnum.GOOGLE)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "INTERVIEW", getEveryHour(date, SourceTypeEnum.INTERVIEW)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("OTHER", getEveryHour(date, SourceTypeEnum.OTHER)));
    Map<String, List> flowTimeHourMap = new HashMap<>();
    flowTimeHourMap.put("hour", keyValues);
    return flowTimeHourMap;
  }

  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2019/5/17 14:46
   * @param num
   * @param date
   * @return
   */
  public Map<String, List> getFlowSourceTypeTimeByDayMap(int num, Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("BAIDU", getEveryDay(num, date, SourceTypeEnum.BAIDU)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("GOOGLE", getEveryDay(num, date, SourceTypeEnum.GOOGLE)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "INTERVIEW", getEveryDay(num, date, SourceTypeEnum.INTERVIEW)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("OTHER", getEveryDay(num, date, SourceTypeEnum.OTHER)));
    Map<String, List> flowTimeDayMap = new HashMap<>();
    flowTimeDayMap.put("day", keyValues);
    return flowTimeDayMap;
  }

  /**
   * 根据时间获取流量概况(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2019/5/14 15:07
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getFlowSourceTypeTimeByHour(Date date) {
    Map<String, Map> flowTimeHourMap = new HashMap<>();
    flowTimeHourMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    flowTimeHourMap.put("sourcetype", getFlowSourceTypeTimeByHourMap(date));
    return flowTimeHourMap;
  }

  /**
   * 根据时间获取流量概况(天)+横坐标(天)
   *
   * @author: lingjian @Date: 2019/5/15 11:43
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getFlowSourceTypeTimeByDay(int num, Date date) {
    Map<String, Map> flowTimeDayMap = new HashMap<>();
    flowTimeDayMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    flowTimeDayMap.put("sourcetype", getFlowSourceTypeTimeByDayMap(num, date));
    return flowTimeDayMap;
  }

  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:27
   * @param date
   * @param type
   * @return Map<String, List>
   */
  @Override
  public List getFlowSourcePage(Date date, String type) {
    List<FlowSourcePageView> list = new ArrayList<>();
    if ("week".equals(type)) {
      for (SourceTypeEnum source : SourceTypeEnum.values()) {
        list.addAll(
            flowDao
                .findAllBySourcePage(
                    source,
                    DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 7), 0),
                    DateTimeUtil.getTimesOfDay(date, 24))
                .stream()
                .map(
                    bean -> {
                      FlowSourcePageView flowSourcePageView = new FlowSourcePageView();
                      flowSourcePageView.setSourceType(bean.get(0, String.class));
                      flowSourcePageView.setSourcePage(bean.get(1, String.class));
                      flowSourcePageView.setVisitorCount(bean.get(2, Integer.class));
                      return flowSourcePageView;
                    })
                .collect(Collectors.toList()));
      }
    } else if ("month".equals(type)) {
      for (SourceTypeEnum source : SourceTypeEnum.values()) {
        list.addAll(
            flowDao
                .findAllBySourcePage(
                    source,
                    DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 30), 0),
                    DateTimeUtil.getTimesOfDay(date, 24))
                .stream()
                .map(
                    bean -> {
                      FlowSourcePageView flowSourcePageView = new FlowSourcePageView();
                      flowSourcePageView.setSourceType(bean.get(0, String.class));
                      flowSourcePageView.setSourcePage(bean.get(1, String.class));
                      flowSourcePageView.setVisitorCount(bean.get(2, Integer.class));
                      return flowSourcePageView;
                    })
                .collect(Collectors.toList()));
      }
    } else {
      for (SourceTypeEnum source : SourceTypeEnum.values()) {
        list.addAll(
            flowDao
                .findAllBySourcePage(
                    source,
                    DateTimeUtil.getTimesOfDay(date, 0),
                    DateTimeUtil.getTimesOfDay(date, 24))
                .stream()
                .map(
                    bean -> {
                      FlowSourcePageView flowSourcePageView = new FlowSourcePageView();
                      flowSourcePageView.setSourceType(bean.get(0, String.class));
                      flowSourcePageView.setSourcePage(bean.get(1, String.class));
                      flowSourcePageView.setVisitorCount(bean.get(2, Integer.class));
                      return flowSourcePageView;
                    })
                .collect(Collectors.toList()));
      }
    }
    return list;
  }

  /**
   * 根据来源类型，来源渠道，时间，获取来源渠道的访客数
   *
   * @param sourceType
   * @param sourcePage
   * @param date
   * @param start
   * @param end
   * @return
   */
  public List<FlowSourcePageView> getSourcePage(
      SourceTypeEnum sourceType, String sourcePage, Date date, int start, int end) {
    return flowDao
        .findAllBySourceTypeAndSourcePage(
            sourceType,
            sourcePage,
            DateTimeUtil.getTimesOfDay(date, start),
            DateTimeUtil.getTimesOfDay(date, end))
        .stream()
        .map(
            bean -> {
              FlowSourcePageView flowSourcePageView = new FlowSourcePageView();
              flowSourcePageView.setSourcePage(bean.get(0, String.class));
              flowSourcePageView.setVisitorCount(bean.get(1, Integer.class));
              return flowSourcePageView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 获取24小时每个小时的来源渠道的访客数
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param sourceType
   * @param sourcePage
   * @param date
   * @return
   */
  public int[] getEveryHourBySourcePage(SourceTypeEnum sourceType, String sourcePage, Date date) {
    int[] arr = new int[24];
    for (int i = 0; i < arr.length; i++) {
      if (!getSourcePage(sourceType, sourcePage, date, i, i + 1).isEmpty()) {
        arr[i] = getSourcePage(sourceType, sourcePage, date, i, i + 1).get(0).getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 获取num天每天的来源渠道的访客数
   *
   * @author: lingjian @Date: 2019/5/17 16:31
   * @param num
   * @param sourceType
   * @param sourcePage
   * @param date
   * @return
   */
  public int[] getEveryDayBySourcePage(
      int num, SourceTypeEnum sourceType, String sourcePage, Date date) {
    int[] arr = new int[num];
    for (int i = 0; i < arr.length; i++) {
      if (!getSourcePage(
              sourceType, sourcePage, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
          .isEmpty()) {
        arr[i] =
            getSourcePage(
                    sourceType, sourcePage, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param sourceType
   * @param sourcePage
   * @param date
   * @return
   */
  public Map<String, List> getSourcePageHourMap(
      SourceTypeEnum sourceType, String sourcePage, Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "visitorCount", getEveryHourBySourcePage(sourceType, sourcePage, date)));
    Map<String, List> sourcePageMap = new HashMap<>();
    sourcePageMap.put("hour", keyValues);
    return sourcePageMap;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/17 16:30
   * @param num
   * @param sourceType
   * @param sourcePage
   * @param date
   * @return
   */
  public Map<String, List> getSourcePageDayMap(
      int num, SourceTypeEnum sourceType, String sourcePage, Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "visitorCount", getEveryDayBySourcePage(num, sourceType, sourcePage, date)));
    Map<String, List> sourcePageMap = new HashMap<>();
    sourcePageMap.put("day", keyValues);
    return sourcePageMap;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param date
   * @param sourceType
   * @param sourcePage
   * @return
   */
  @Override
  public Map<String, Map> getFlowSourcePageByHour(
      Date date, SourceTypeEnum sourceType, String sourcePage) {
    Map<String, Map> sourcePageMap = new HashMap<>();
    sourcePageMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    sourcePageMap.put("sourcepage", getSourcePageHourMap(sourceType, sourcePage, date));
    return sourcePageMap;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(天)+横坐标(日期)
   *
   * @author: lingjian @Date: 2019/5/17 16:30
   * @param num
   * @param date
   * @param sourceType
   * @param sourcePage
   * @return
   */
  @Override
  public Map<String, Map> getFlowSourcePageByDay(
      int num, Date date, SourceTypeEnum sourceType, String sourcePage) {
    Map<String, Map> sourcePageMap = new HashMap<>();
    sourcePageMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    sourcePageMap.put("sourcepage", getSourcePageDayMap(num, sourceType, sourcePage, date));
    return sourcePageMap;
  }

  /**
   * 计算页面分析的占比率
   *
   * @author: lingjian @Date: 2019/5/17 14:04
   * @param num1
   * @param num2
   * @return
   */
  public Double getCompareRate(int num1, int num2) {
    if (num2 == 0) {
      num2 = 1;
    }
    return num1 / (num2 * 1.0);
  }

  public Map<String, List<AccessView>> getFlowPageAnalysisByDate(Date startDate, Date endDate) {
    Map<String, List<AccessView>> accessPageMap = new HashMap<>();
    for (AccessTypeEnum access : AccessTypeEnum.values()) {
      if (!access.equals(AccessTypeEnum.OTHER)) {
        accessPageMap.put(
            access.name(),
            flowPageDao
                .findAllByAccess(
                    access,
                    DateTimeUtil.getTimesOfDay(startDate, 0),
                    DateTimeUtil.getTimesOfDay(endDate, 24))
                .stream()
                .map(
                    bean -> {
                      AccessView accessView = new AccessView();
                      accessView.setAccessType(bean.get(0, String.class));
                      accessView.setVisitorCount(bean.get(1, Integer.class));
                      Integer accessTotal =
                          flowPageDao.findAllByAccessTotal(
                              DateTimeUtil.getTimesOfDay(startDate, 0),
                              DateTimeUtil.getTimesOfDay(endDate, 24));
                      accessView.setVisitorRate(
                          getCompareRate(bean.get(1, Integer.class), accessTotal));
                      return accessView;
                    })
                .collect(Collectors.toList()));
      }
    }
    return accessPageMap;
  }

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param date
   * @param type
   * @return Map<String, List<AccessView>>
   */
  @Override
  public Map<String, List<AccessView>> getFlowPageAnalysis(Date date, String type) {
    Map<String, List<AccessView>> map = null;
    if ("week".equals(type)) {
      map = getFlowPageAnalysisByDate(DateTimeUtil.getDayFromNum(date, 7), date);
    } else if ("month".equals(type)) {
      map = getFlowPageAnalysisByDate(DateTimeUtil.getDayFromNum(date, 30), date);
    } else {
      map = getFlowPageAnalysisByDate(date, date);
    }
    return map;
  }

  /**
   * 根据指定时间，开始小时，结束小时，获取页面分析参数
   *
   * @author: lingjian @Date: 2019/5/15 15:15
   * @param date
   * @param start
   * @param end
   * @return
   */
  public Map<String, List<AccessPageView>> getFlowPageAnalysisByAccess(
      Date date, int start, int end) {
    Map<String, List<AccessPageView>> accessPageMap = new HashMap<>();
    for (AccessTypeEnum access : AccessTypeEnum.values()) {
      accessPageMap.put(
          access.name(),
          flowPageDao
              .findAllByAccess(
                  access,
                  DateTimeUtil.getTimesOfDay(date, start),
                  DateTimeUtil.getTimesOfDay(date, end))
              .stream()
              .map(
                  bean -> {
                    AccessPageView accessPageView = new AccessPageView();
                    accessPageView.setAccessType(bean.get(0, String.class));
                    accessPageView.setVisitorCount(bean.get(1, Integer.class));
                    accessPageView.setViewCount(bean.get(2, Integer.class));
                    accessPageView.setClickCount(bean.get(3, Integer.class));
                    accessPageView.setClickNumber(bean.get(4, Integer.class));
                    accessPageView.setClickRate(bean.get(5, Double.class));
                    accessPageView.setJumpRate(bean.get(6, Double.class));
                    accessPageView.setAverageStayTime(bean.get(7, Double.class));
                    return accessPageView;
                  })
              .collect(Collectors.toList()));
    }
    return accessPageMap;
  }

  /**
   * 根据时间和页面分类，获取一天24小时每个小时的页面参数
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date
   * @param access
   * @param parameter
   * @return
   */
  public double[] getAccessHour(Date date, AccessTypeEnum access, String parameter) {
    double[] arr = new double[24];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).isEmpty()
          && "visitorCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(date, i, i + 1)
                .get(access.toString())
                .get(0)
                .getVisitorCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).isEmpty()
          && "viewCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(date, i, i + 1)
                .get(access.toString())
                .get(0)
                .getViewCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).isEmpty()
          && "clickCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(date, i, i + 1)
                .get(access.toString())
                .get(0)
                .getClickCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).isEmpty()
          && "clickNumber".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(date, i, i + 1)
                .get(access.toString())
                .get(0)
                .getClickNumber()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).isEmpty()
          && "clickRate".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(date, i, i + 1)
                .get(access.toString())
                .get(0)
                .getClickRate();
      } else if (!getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).isEmpty()
          && "jumpRate".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).get(0).getJumpRate();
      } else if (!getFlowPageAnalysisByAccess(date, i, i + 1).get(access.toString()).isEmpty()
          && "averageStayTime".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(date, i, i + 1)
                .get(access.toString())
                .get(0)
                .getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间和页面，获取某几天每一天的页面参数
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param num 某几天
   * @param date
   * @param access
   * @param parameter
   * @return
   */
  public double[] getAccessDay(int num, Date date, AccessTypeEnum access, String parameter) {
    double[] arr = new double[num];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "visitorCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(access.toString())
                .get(0)
                .getVisitorCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "viewCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(access.toString())
                .get(0)
                .getViewCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "clickCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(access.toString())
                .get(0)
                .getClickCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "clickNumber".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(access.toString())
                .get(0)
                .getClickNumber()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "clickRate".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(access.toString())
                .get(0)
                .getClickRate();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "jumpRate".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(access.toString())
                .get(0)
                .getJumpRate();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "averageStayTime".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(access.toString())
                .get(0)
                .getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/17 14:33
   * @param date
   * @param access
   * @return
   */
  public Map<String, List> getFlowPageAnalysisByHourMap(Date date, AccessTypeEnum access) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "visitorCount", getAccessHour(date, access, "visitorCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("viewCount", getAccessHour(date, access, "viewCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("clickCount", getAccessHour(date, access, "clickCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "clickNumber", getAccessHour(date, access, "clickNumber")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("clickRate", getAccessHour(date, access, "clickRate")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("jumpRate", getAccessHour(date, access, "jumpRate")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "averageStayTime", getAccessHour(date, access, "averageStayTime")));
    Map<String, List> analysisHourMap = new HashMap<>();
    analysisHourMap.put("hour", keyValues);
    return analysisHourMap;
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/17 14:32
   * @param num
   * @param date
   * @param access
   * @return
   */
  public Map<String, List> getFlowPageAnalysisByDayMap(int num, Date date, AccessTypeEnum access) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "visitorCount", getAccessDay(num, date, access, "visitorCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "viewCount", getAccessDay(num, date, access, "viewCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "clickCount", getAccessDay(num, date, access, "clickCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "clickNumber", getAccessDay(num, date, access, "clickNumber")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "clickRate", getAccessDay(num, date, access, "clickRate")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("jumpRate", getAccessDay(num, date, access, "jumpRate")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "averageStayTime", getAccessDay(num, date, access, "averageStayTime")));
    Map<String, List> analysisDayMap = new HashMap<>();
    analysisDayMap.put("day", keyValues);
    return analysisDayMap;
  }

  /**
   * * 根据时间和页面分类，获取页面分析时段分析(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date
   * @param access
   * @return
   */
  @Override
  public Map<String, Map> getFlowPageAnalysisByHour(Date date, AccessTypeEnum access) {
    Map<String, Map> analysisHourMap = new HashMap<>();
    analysisHourMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    analysisHourMap.put("analysis", getFlowPageAnalysisByHourMap(date, access));
    return analysisHourMap;
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)+横坐标(天)
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param date
   * @param access
   * @return
   */
  @Override
  public Map<String, Map> getFlowPageAnalysisByDay(int num, Date date, AccessTypeEnum access) {
    Map<String, Map> analysisDayMap = new HashMap<>();
    analysisDayMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    analysisDayMap.put("hour", getFlowPageAnalysisByDayMap(num, date, access));
    return analysisDayMap;
  }

  /**
   * 计算平均浏览量
   *
   * @author: lingjian @Date: 2019/5/14 16:53
   * @param num1
   * @param num2
   * @return
   */
  public Double getCompare(int num1, int num2) {
    if (num2 == 0) {
      num2 = 1;
    }
    return num1 / (num2 * 1.0);
  }

  /**
   * 计算对比值
   *
   * @author: lingjian @Date: 2019/5/17 15:27
   * @param num1
   * @param num2
   * @return
   */
  public Double getCompareTwo(double num1, double num2) {
    if (num2 == 0) {
      num2 = 1.0;
    }
    return (num1 - num2) / num2;
  }

  /**
   * 判断非空处理
   *
   * @author: lingjian @Date: 2019/5/17 15:38
   * @param page
   * @return
   */
  public PageViewObject isNullTo(PageViewObject page) {
    if (page.getVisitorCount() == null) {
      page.setVisitorCount(0);
      page.setViewCount(0);
      page.setJumpRate(0.0);
      page.setAverageStayTime(0.0);
    }
    return page;
  }

  /**
   * 根据时间获取流量概况参数(访客数，浏览量，跳失率，平均停留时长)
   *
   * @author: lingjian @Date: 2019/5/17 15:39
   * @param date
   * @return
   */
  public PageViewObject getFlowPageObject(Date date) {
    return isNullTo(
        flowPageDao.findAllByCreateTimeObject(
            DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24)));
  }

  /**
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/5/17 15:38
   * @param date
   * @return
   */
  @Override
  public PageParameterView getFlowPage(Date date) {
    PageViewObject today = getFlowPageObject(date);
    PageViewObject yesterday = getFlowPageObject(DateTimeUtil.getDayFromNum(date, 1));
    PageViewObject week = getFlowPageObject(DateTimeUtil.getDayFromNum(date, 7));

    Double compareToday = getCompare(today.getViewCount(), today.getVisitorCount());
    Double compareYesterday = getCompare(yesterday.getViewCount(), yesterday.getVisitorCount());
    Double compareWeek = getCompare(week.getViewCount(), week.getVisitorCount());

    PageParameterView pageParameterView = new PageParameterView();
    pageParameterView.setViewAvgCount(compareToday);
    pageParameterView.setViewCompareYesterday(getCompareTwo(compareToday, compareYesterday));
    pageParameterView.setViewCompareWeek(getCompareTwo(compareToday, compareWeek));
    pageParameterView.setJumpRate(today.getJumpRate());
    pageParameterView.setJumpCompareYesterday(
        getCompareTwo(today.getJumpRate(), yesterday.getJumpRate()));
    pageParameterView.setJumpCompareWeek(getCompareTwo(today.getJumpRate(), week.getJumpRate()));
    pageParameterView.setAverageStayTime(today.getAverageStayTime());
    pageParameterView.setTimeCompareYesterday(
        getCompareTwo(today.getAverageStayTime(), yesterday.getAverageStayTime()));
    pageParameterView.setTimeCompareWeek(
        getCompareTwo(today.getAverageStayTime(), week.getAverageStayTime()));

    return pageParameterView;
  }

  /**
   * 根据时间获取流量概况参数
   *
   * @author: lingjian @Date: 2019/5/14 16:47
   * @param date
   * @return
   */
  public List<PageView> getFlowPageParameter(Date date) {
    return flowPageDao
        .findAllByCreateTime(
            DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24))
        .stream()
        .map(
            bean -> {
              PageView pageView = new PageView();
              if (bean.get(0, Integer.class) != null && bean.get(1, Integer.class) != null) {
                pageView.setViewAvgCount(
                    getCompare(bean.get(0, Integer.class), bean.get(1, Integer.class)));
              }
              pageView.setJumpRate(bean.get(2, Double.class));
              pageView.setAverageStayTime(bean.get(3, Double.class));
              return pageView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据时间，参数，获取三十天中每一天的跳失率、人均流量量、平均停留时长
   *
   * @author: lingjian @Date: 2019/5/15 14:27
   * @param date
   * @param parameter
   * @return
   */
  public double[] getEveryPage(int num, Date date, String parameter) {
    double[] arr = new double[num];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowPageParameter(DateTimeUtil.getDayFromNum(date, num - i - 1)).isEmpty()
          && "viewAvgCount".equals(parameter)) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, num - i - 1))
                .get(0)
                .getViewAvgCount();
      } else if (!getFlowPageParameter(DateTimeUtil.getDayFromNum(date, num - i - 1)).isEmpty()
          && "jumpRate".equals(parameter)) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, num - i - 1))
                .get(0)
                .getJumpRate();
      } else if (!getFlowPageParameter(DateTimeUtil.getDayFromNum(date, num - i - 1)).isEmpty()
          && "averageStayTime".equals(parameter)) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, num - i - 1))
                .get(0)
                .getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2019/5/17 15:24
   * @param num
   * @param date
   * @return
   */
  public Map<String, List> getFlowPageByMonthMap(int num, Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("viewAvgCount", getEveryPage(num, date, "viewAvgCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("jumpRate", getEveryPage(num, date, "jumpRate")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "averageStayTime", getEveryPage(num, date, "averageStayTime")));
    Map<String, List> flowPageMonthMap = new HashMap<>();
    flowPageMonthMap.put("month", keyValues);
    return flowPageMonthMap;
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数+横坐标(天)
   *
   * @author: lingjian @Date: 2019/5/15 14:27
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getFlowPageByMonth(Date date) {
    Map<String, Map> flowPageMonthMap = new HashMap<>();
    flowPageMonthMap.put("abscissa", DateTimeUtil.getDayAbscissa(30, date));
    flowPageMonthMap.put("flowpage", getFlowPageByMonthMap(30, date));
    return flowPageMonthMap;
  }

  /**
   * -获取访问页面排行 按页面浏览量排行
   *
   * @author xiayan
   * @param
   * @return
   */
  public List<com.shoestp.mains.views.DataView.metaData.PageRankingView> getPageRanking(
      Integer limit) {
    return googleBrowseInfoDao.queryPageRanking(limit);
  }
}

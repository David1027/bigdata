package com.shoestp.mains.service.impl.dataview;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.dao.dataview.flow.FlowDao;
import com.shoestp.mains.dao.dataview.flow.FlowPageDao;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.service.dataview.FlowService;
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
public class FlowServiceImpl implements FlowService {

  @Resource private FlowDao flowDao;
  @Resource private FlowPageDao flowPageDao;

  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2019/5/10 16:44
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Map<String, List>
   */
  @Override
  public Map<String, List> getRealSource(Date startDate, Date endDate) {
    Map<String, List> flowViewMap = new HashMap<>();
    for (DeviceTypeEnum device : DeviceTypeEnum.values()) {
      flowViewMap.put(
          device.name(),
          flowDao
              .findAllByDeviceType(
                  device,
                  DateTimeUtil.getTimesOfDay(startDate, 0),
                  DateTimeUtil.getTimesOfDay(endDate, 24))
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
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<FlowDeviceView>
   */
  @Override
  public List<FlowDeviceView> getFlowDevice(Date startDate, Date endDate) {
    return flowDao
        .findAllByDeviceCount(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
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

  /**
   * 据单个时间获取设备来源
   *
   * @author: lingjian @Date: 2019/8/16 9:53
   * @param date 时间
   * @param num 数字类型
   * @return List<FlowDeviceView>
   */
  @Override
  public List<FlowDeviceView> getFlowDevice(Date date, Integer num) {
    if (num == null) {
      return getFlowDevice(date, date);
    } else {
      return getFlowDevice(DateTimeUtil.getDayFromNum(date, num), date);
    }
  }

  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2019/5/20 16:37
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<FlowSourceView>
   */
  private List<FlowSourceView> getFlowSourceTypeByDate(Date startDate, Date endDate) {
    return flowDao
        .findAllBySourceType(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
        .stream()
        .map(
            bean -> {
              FlowSourceView flowSourceView = new FlowSourceView();
              flowSourceView.setSourceType(bean.get(0, String.class));
              flowSourceView.setVisitorCount(bean.get(1, Integer.class));
              flowSourceView.setInquiryCount(bean.get(2, Integer.class));
              return flowSourceView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据时间,天数获取流量来源
   *
   * @author: lingjian @Date: 2019/5/14 14:12
   * @param date 时间
   * @param num 天数类型
   * @return List<FlowSourceView> 流量来源访客VO集合
   */
  @Override
  public List<FlowSourceView> getFlowSourceType(Date date, Integer num) {
    if (num != null) {
      return getFlowSourceTypeByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getFlowSourceTypeByDate(date, date);
    }
  }

  /**
   * 根据时间获取每个流量来源的访客数
   *
   * @author: lingjian @Date: 2019/5/14 15:08
   * @param date 时间
   * @param start 开始值
   * @param end 结束值
   * @return Map<String, List<FlowSourceView>>
   */
  private Map<String, List<FlowSourceView>> getFlowSourceType(Date date, int start, int end) {
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
   * @param date 时间
   * @return int[]
   */
  private int[] getEveryHour(Date date, SourceTypeEnum source) {
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
   * @param date 时间
   * @param source 来源类型
   * @return int[]
   */
  private int[] getEveryDay(int num, Date date, SourceTypeEnum source) {
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
   * @param date 时间
   * @return Map<String, List>
   */
  private Map<String, List<KeyValue>> getFlowSourceTypeTimeByHourMap(Date date) {
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
    Map<String, List<KeyValue>> flowTimeHourMap = new HashMap<>(16);
    flowTimeHourMap.put("day", keyValues);
    return flowTimeHourMap;
  }

  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2019/5/17 14:46
   * @param num 天数
   * @param date 时间
   * @return Map<String, List>
   */
  private Map<String, List<KeyValue>> getFlowSourceTypeTimeByDayMap(int num, Date date) {
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
    Map<String, List<KeyValue>> flowTimeDayMap = new HashMap<>(16);
    flowTimeDayMap.put("day", keyValues);
    return flowTimeDayMap;
  }

  /**
   * 根据时间获取流量概况(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2019/5/14 15:07
   * @param date 时间
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowSourceTypeTimeByHour(Date date) {
    Map<String, Map> flowTimeHourMap = new HashMap<>(16);
    flowTimeHourMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    flowTimeHourMap.put("sourcetype", getFlowSourceTypeTimeByHourMap(date));
    return flowTimeHourMap;
  }

  /**
   * 根据时间获取流量概况(天)+横坐标(天)
   *
   * @author: lingjian @Date: 2019/5/15 11:43
   * @param date 时间
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowSourceTypeTimeByDay(int num, Date date) {
    Map<String, Map> flowTimeDayMap = new HashMap<>(16);
    flowTimeDayMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    flowTimeDayMap.put("sourcetype", getFlowSourceTypeTimeByDayMap(num, date));
    return flowTimeDayMap;
  }

  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/20 16:40
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List<KeyValue> 转化对象VO集合对象
   */
  private List<KeyValue> getFlowSourcePageByDate(Date startDate, Date endDate) {
    List<KeyValue> list = new ArrayList<>();
    for (SourceTypeEnum source : SourceTypeEnum.values()) {
      List<FlowSourcePageView> collect =
          flowDao
              .findAllBySourcePage(
                  source,
                  DateTimeUtil.getTimesOfDay(startDate, 0),
                  DateTimeUtil.getTimesOfDay(endDate, 24))
              .stream()
              .map(
                  bean -> {
                    FlowSourcePageView flowSourcePageView = new FlowSourcePageView();
                    flowSourcePageView.setSourcePage(bean.get(0, String.class));
                    flowSourcePageView.setVisitorCount(bean.get(1, Integer.class));
                    flowSourcePageView.setInquiryCount(bean.get(2, Integer.class));
                    return flowSourcePageView;
                  })
              .collect(Collectors.toList());
      list.add(KeyValueViewUtil.getFlowKeyValue(source.name(), collect));
    }
    return list;
  }

  /**
   * 根据时间,日期类型获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:27
   * @param date 时间
   * @param num 天数
   * @return Map<String, List>
   */
  @Override
  public List<KeyValue> getFlowSourcePage(Date date, Integer num) {
    if (num != null) {
      return getFlowSourcePageByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getFlowSourcePageByDate(date, date);
    }
  }

  /**
   * 根据来源类型，来源渠道，时间，获取来源渠道的访客数
   *
   * @author: lingjian @Date: 2019/8/16 14:50
   * @param sourceType 来源类型
   * @param sourcePage 来源名称
   * @param date 时间
   * @param start 开始值
   * @param end 结束值
   * @return List<FlowSourcePageView> 来源渠道访客VO集合对象
   */
  private List<FlowSourcePageView> getSourcePage(
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
              flowSourcePageView.setInquiryCount(bean.get(2, Integer.class));
              return flowSourcePageView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 获取24小时每个小时的来源渠道的访客数
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param sourceType 来源类型
   * @param sourcePage 来源名称
   * @param date 时间
   * @return int[]
   */
  private int[] getEveryHourBySourcePage(
      SourceTypeEnum sourceType, String sourcePage, Date date, String parameter) {
    int[] arr = new int[24];
    for (int i = 0; i < arr.length; i++) {
      if ("visitorCount".equals(parameter)
          && !getSourcePage(sourceType, sourcePage, date, i, i + 1).isEmpty()) {
        arr[i] = getSourcePage(sourceType, sourcePage, date, i, i + 1).get(0).getVisitorCount();
      } else if ("inquiryCount".equals(parameter)
          && !getSourcePage(sourceType, sourcePage, date, i, i + 1).isEmpty()) {
        arr[i] = getSourcePage(sourceType, sourcePage, date, i, i + 1).get(0).getInquiryCount();
      }
    }
    return arr;
  }

  /**
   * 获取num天每天的来源渠道的访客数
   *
   * @author: lingjian @Date: 2019/5/17 16:31
   * @param num 天数
   * @param sourceType 来源类型
   * @param sourcePage 来源名称
   * @param date 时间
   * @return int[]
   */
  private int[] getEveryDayBySourcePage(
      int num, SourceTypeEnum sourceType, String sourcePage, Date date, String parameter) {
    int[] arr = new int[num];
    for (int i = 0; i < arr.length; i++) {
      if ("visitorCount".equals(parameter)
          && !getSourcePage(
                  sourceType, sourcePage, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getSourcePage(
                    sourceType, sourcePage, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getVisitorCount();
      } else if ("inquiryCount".equals(parameter)
          && !getSourcePage(
                  sourceType, sourcePage, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getSourcePage(
                    sourceType, sourcePage, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getInquiryCount();
      }
    }
    return arr;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param sourceType 来源类型
   * @param sourcePage 来源名称
   * @param date 时间
   * @return Map<String, List>
   */
  private Map<String, List<KeyValue>> getSourcePageHourMap(
      SourceTypeEnum sourceType, String sourcePage, Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "visitorCount",
            getEveryHourBySourcePage(sourceType, sourcePage, date, "visitorCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "inquiryCount",
            getEveryHourBySourcePage(sourceType, sourcePage, date, "inquiryCount")));
    Map<String, List<KeyValue>> sourcePageMap = new HashMap<>(16);
    sourcePageMap.put("day", keyValues);
    return sourcePageMap;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/17 16:30
   * @param num 天数
   * @param sourceType 来源类型
   * @param sourcePage 来源名称
   * @param date 时间
   * @return Map<String, List>
   */
  private Map<String, List<KeyValue>> getSourcePageDayMap(
      int num, SourceTypeEnum sourceType, String sourcePage, Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "visitorCount",
            getEveryDayBySourcePage(num, sourceType, sourcePage, date, "visitorCount")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            "inquiryCount",
            getEveryDayBySourcePage(num, sourceType, sourcePage, date, "inquiryCount")));
    Map<String, List<KeyValue>> sourcePageMap = new HashMap<>(16);
    sourcePageMap.put("day", keyValues);
    return sourcePageMap;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param date 时间
   * @param sourceType 来源类型
   * @param sourcePage 来源名称
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowSourcePageByHour(
      Date date, SourceTypeEnum sourceType, String sourcePage) {
    Map<String, Map> sourcePageMap = new HashMap<>(16);
    sourcePageMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    sourcePageMap.put("sourcepage", getSourcePageHourMap(sourceType, sourcePage, date));
    return sourcePageMap;
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(天)+横坐标(日期)
   *
   * @author: lingjian @Date: 2019/5/17 16:30
   * @param num 天数
   * @param date 时间
   * @param sourceType 来源类型
   * @param sourcePage 来源名称
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowSourcePageByDay(
      int num, Date date, SourceTypeEnum sourceType, String sourcePage) {
    Map<String, Map> sourcePageMap = new HashMap<>(16);
    sourcePageMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    sourcePageMap.put("sourcepage", getSourcePageDayMap(num, sourceType, sourcePage, date));
    return sourcePageMap;
  }

  /**
   * 获取所有的页面类型
   *
   * @author: lingjian @Date: 2019/8/14 11:24
   * @return List集合
   */
  @Override
  public List getFlowPageType() {
    List<AccessTypeEnum> list = new ArrayList<>();
    Collections.addAll(list, AccessTypeEnum.values());
    return list;
  }

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/20 16:41
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Map<String, List<AccessView>>
   */
  private Map<String, List<AccessView>> getFlowPageAnalysisByDate(Date startDate, Date endDate) {
    Map<String, List<AccessView>> accessPageMap = new HashMap<>(16);
    List<AccessView> list = new ArrayList<>();
    for (AccessTypeEnum a : AccessTypeEnum.values()) {
      if (a.equals(AccessTypeEnum.INDEX)
          || a.equals(AccessTypeEnum.DETAIL)
          || a.equals(AccessTypeEnum.LIST)
          || a.equals(AccessTypeEnum.OTHER)
          || a.equals(AccessTypeEnum.USER_REG)
          || a.equals(AccessTypeEnum.LANDING)) {
        AccessView access = new AccessView();
        // 页面类型
        access.setAccessType(a.toString());
        access.setAccessName(a.getName());
        // 访客数
        Integer visitor =
            flowPageDao.countAccessByType(
                a,
                DateTimeUtil.getTimesOfDay(startDate, 0),
                DateTimeUtil.getTimesOfDay(endDate, 24));
        access.setVisitorCount(visitor);
        // 访客占比率
        // 一天内的总访客数
        Integer visitorTotal =
            flowPageDao.findAllByAccessTotal(
                DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
        // 占比率
        access.setVisitorRate(
            CustomDoubleSerialize.setDouble(CalculateUtil.getExcept(visitor, visitorTotal)));
        list.add(access);
      }
    }
    accessPageMap.put("access", list);
    return accessPageMap;
  }

  /**
   * 根据时间，日期类型获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param date 时间
   * @param num 天数
   * @return Map<String, List<AccessView>>
   */
  @Override
  public Map<String, List<AccessView>> getFlowPageAnalysis(Date date, Integer num) {
    if (num != null) {
      return getFlowPageAnalysisByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getFlowPageAnalysisByDate(date, date);
    }
  }

  /**
   * 根据指定时间，开始小时，结束小时，获取页面分析参数
   *
   * @author: lingjian @Date: 2019/5/15 15:15
   * @param date 时间
   * @param start 开始值
   * @param end 结束值
   * @return List<AccessPageView>
   */
  private List<AccessPageView> getFlowPageAnalysisByAccess(
      AccessTypeEnum access, Date date, int start, int end) {
    return flowPageDao
        .findAllByAccess(
            access, DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end))
        .stream()
        .map(
            bean -> {
              AccessPageView accessPageView = new AccessPageView();
              accessPageView.setAccessType(bean.get(0, String.class));
              accessPageView.setVisitorCount(bean.get(1, Integer.class));
              accessPageView.setViewCount(bean.get(2, Integer.class));
              accessPageView.setClickCount(bean.get(3, Integer.class));
              accessPageView.setClickNumber(bean.get(4, Integer.class));
              accessPageView.setClickRate(
                  CustomDoubleSerialize.setDouble(bean.get(5, Double.class)));
              accessPageView.setJumpRate(
                  CustomDoubleSerialize.setDouble(bean.get(6, Double.class)));
              accessPageView.setAverageStayTime(
                  CustomDoubleSerialize.setDouble(bean.get(7, Double.class)));
              return accessPageView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据时间和页面分类，获取一天24小时每个小时的页面参数
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date 时间
   * @param access 页面类型
   * @param parameter 页面类型的关键词
   * @return double[]
   */
  private double[] getAccessHour(Date date, AccessTypeEnum access, String parameter) {
    double[] arr = new double[24];
    for (int i = 0; i < arr.length; i++) {
      if ("visitorCount".equals(parameter)
          && !getFlowPageAnalysisByAccess(access, date, i, i + 1).isEmpty()) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).get(0).getVisitorCount();
      } else if ("viewCount".equals(parameter)
          && !getFlowPageAnalysisByAccess(access, date, i, i + 1).isEmpty()) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).get(0).getViewCount();
      } else if ("clickCount".equals(parameter)
          && !getFlowPageAnalysisByAccess(access, date, i, i + 1).isEmpty()) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).get(0).getClickCount();
      } else if ("clickNumber".equals(parameter)
          && !getFlowPageAnalysisByAccess(access, date, i, i + 1).isEmpty()) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).get(0).getClickNumber();
      } else if ("clickRate".equals(parameter)
          && !getFlowPageAnalysisByAccess(access, date, i, i + 1).isEmpty()) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).get(0).getClickRate();
      } else if ("jumpRate".equals(parameter)
          && !getFlowPageAnalysisByAccess(access, date, i, i + 1).isEmpty()) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).get(0).getJumpRate();
      } else if ("averageStayTime".equals(parameter)
          && !getFlowPageAnalysisByAccess(access, date, i, i + 1).isEmpty()) {
        arr[i] = getFlowPageAnalysisByAccess(access, date, i, i + 1).get(0).getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间和页面，获取某几天每一天的页面参数
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param num 某几天
   * @param date 时间
   * @param access 页面类型
   * @param parameter 页面类型的关键词
   * @return double[]
   */
  private double[] getAccessDay(int num, Date date, AccessTypeEnum access, String parameter) {
    double[] arr = new double[num];
    for (int i = 0; i < arr.length; i++) {
      if ("visitorCount".equals(parameter)
          && !getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getVisitorCount();
      } else if ("viewCount".equals(parameter)
          && !getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getViewCount();
      } else if ("clickCount".equals(parameter)
          && !getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getClickCount();
      } else if ("clickNumber".equals(parameter)
          && !getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getClickNumber();
      } else if ("clickRate".equals(parameter)
          && !getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getClickRate();
      } else if ("jumpRate".equals(parameter)
          && !getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getJumpRate();
      } else if ("averageStayTime".equals(parameter)
          && !getFlowPageAnalysisByAccess(
                  access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
              .isEmpty()) {
        arr[i] =
            getFlowPageAnalysisByAccess(
                    access, DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
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
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, List<KeyValue>>
   */
  private Map<String, List<KeyValue>> getFlowPageAnalysisByHourMap(
      Date date, AccessTypeEnum access) {
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
    Map<String, List<KeyValue>> analysisHourMap = new HashMap<>(16);
    analysisHourMap.put("day", keyValues);
    return analysisHourMap;
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
      int num, Date date, AccessTypeEnum access) {
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
    Map<String, List<KeyValue>> analysisDayMap = new HashMap<>(16);
    analysisDayMap.put("day", keyValues);
    return analysisDayMap;
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
  public Map<String, Map> getFlowPageAnalysisByHour(Date date, AccessTypeEnum access) {
    Map<String, Map> analysisHourMap = new HashMap<>(16);
    analysisHourMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    analysisHourMap.put("analysis", getFlowPageAnalysisByHourMap(date, access));
    return analysisHourMap;
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)+横坐标(天)
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param num 天数类型
   * @param date 时间
   * @param access 页面类型
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowPageAnalysisByDay(int num, Date date, AccessTypeEnum access) {
    Map<String, Map> analysisDayMap = new HashMap<>(16);
    analysisDayMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    analysisDayMap.put("analysis", getFlowPageAnalysisByDayMap(num, date, access));
    return analysisDayMap;
  }

  /**
   * 判断非空处理
   *
   * @author: lingjian @Date: 2019/5/17 15:38
   * @param page 流量概况参数连接数据层类
   * @return PageViewObject 流量概况参数连接数据层类
   */
  private PageViewObject isNullTo(PageViewObject page) {
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
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return PageViewObject 流量概况参数连接数据层类
   */
  private PageViewObject getFlowPageObject(Date startDate, Date endDate) {
    return isNullTo(
        flowPageDao.findAllByCreateTimeObject(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
  }

  /**
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/5/17 15:38
   * @param date 时间
   * @return PageParameterView 流量概况参数+占比前端展示类
   */
  private PageParameterView getFlowPageByDate(Date date, Integer num) {
    PageViewObject today = getFlowPageObject(DateTimeUtil.getDayFromNum(date, num), date);
    PageViewObject yesterday =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(date, 1 + num), DateTimeUtil.getDayFromNum(date, 1));
    PageViewObject week =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(date, 7 + num), DateTimeUtil.getDayFromNum(date, 7));

    // 计算(浏览量 / 访客数)
    Double compareToday = CalculateUtil.getExcept(today.getViewCount(), today.getVisitorCount());
    Double compareYesterday =
        CalculateUtil.getExcept(yesterday.getViewCount(), yesterday.getVisitorCount());
    Double compareWeek = CalculateUtil.getExcept(week.getViewCount(), week.getVisitorCount());

    PageParameterView pageParameterView = new PageParameterView();
    // 平均浏览量，与昨日的比较值，与上周同一日的比较值
    pageParameterView.setViewAvgCount(compareToday);
    pageParameterView.setViewCompareYesterday(
        CalculateUtil.getDifferenceExcept(compareToday, compareYesterday));
    pageParameterView.setViewCompareWeek(
        CalculateUtil.getDifferenceExcept(compareToday, compareWeek));
    // 跳失率，与昨日的比较值，与上周同一日的比较值
    pageParameterView.setJumpRate(today.getJumpRate());
    pageParameterView.setJumpCompareYesterday(
        CalculateUtil.getDifferenceExcept(today.getJumpRate(), yesterday.getJumpRate()));
    pageParameterView.setJumpCompareWeek(
        CalculateUtil.getDifferenceExcept(today.getJumpRate(), week.getJumpRate()));
    // 平均停留时长，与昨日的比较值，与上周同一日的比较值
    pageParameterView.setAverageStayTime(today.getAverageStayTime());
    pageParameterView.setTimeCompareYesterday(
        CalculateUtil.getDifferenceExcept(
            today.getAverageStayTime(), yesterday.getAverageStayTime()));
    pageParameterView.setTimeCompareWeek(
        CalculateUtil.getDifferenceExcept(today.getAverageStayTime(), week.getAverageStayTime()));

    return pageParameterView;
  }

  /**
   * 根据时间,天数获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/8/16 9:31
   * @param date 时间
   * @param num 天数
   * @return PageParameterView 流量概况参数+占比前端展示类
   */
  @Override
  public PageParameterView getFlowPage(Date date, Integer num) {
    if (num != null) {
      return getFlowPageByDate(date, num);
    } else {
      return getFlowPageByDate(date, 0);
    }
  }

  /**
   * 根据时间获取流量概况参数
   *
   * @author: lingjian @Date: 2019/5/14 16:47
   * @param date 时间
   * @return List<PageView>
   */
  private List<PageView> getFlowPageParameter(Date date) {
    return flowPageDao
        .findAllByCreateTime(
            DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24))
        .stream()
        .map(
            bean -> {
              PageView pageView = new PageView();
              // 平均浏览量
              if (bean.get(0, Integer.class) != null && bean.get(1, Integer.class) != null) {
                pageView.setViewAvgCount(
                    CustomDoubleSerialize.setDouble(
                        CalculateUtil.getExcept(
                            bean.get(1, Integer.class), bean.get(0, Integer.class))));
              }
              // 跳失率
              pageView.setJumpRate(CustomDoubleSerialize.setDouble(bean.get(2, Double.class)));
              // 平均时长
              pageView.setAverageStayTime(
                  CustomDoubleSerialize.setDouble(bean.get(3, Double.class)));
              return pageView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据时间，参数，获取三十天中每一天的跳失率、人均流量量、平均停留时长
   *
   * @author: lingjian @Date: 2019/5/15 14:27
   * @param date 时间
   * @param parameter 类型关键词
   * @return double[]
   */
  private double[] getEveryPage(Date date, String parameter) {
    double[] arr = new double[30];
    for (int i = 0; i < arr.length; i++) {
      if ("viewAvgCount".equals(parameter)
          && !getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1)).isEmpty()) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1))
                .get(0)
                .getViewAvgCount();
      } else if ("jumpRate".equals(parameter)
          && !getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1)).isEmpty()) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1)).get(0).getJumpRate();
      } else if ("averageStayTime".equals(parameter)
          && !getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1)).isEmpty()) {
        arr[i] =
            getFlowPageParameter(DateTimeUtil.getDayFromNum(date, 30 - i - 1))
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
   * @param date 时间
   * @return Map<String, List<KeyValue>>
   */
  private Map<String, List<KeyValue>> getFlowPageByMonthMap(Date date) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("viewAvgCount", getEveryPage(date, "viewAvgCount")));
    keyValues.add(KeyValueViewUtil.getFlowKeyValue("jumpRate", getEveryPage(date, "jumpRate")));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue("averageStayTime", getEveryPage(date, "averageStayTime")));
    Map<String, List<KeyValue>> flowPageMonthMap = new HashMap<>(16);
    flowPageMonthMap.put("month", keyValues);
    return flowPageMonthMap;
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数+横坐标(天)
   *
   * @author: lingjian @Date: 2019/5/15 14:27
   * @param date 时间
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getFlowPageByMonth(Date date) {
    Map<String, Map> flowPageMonthMap = new HashMap<>(16);
    flowPageMonthMap.put("abscissa", DateTimeUtil.getDayAbscissa(30, date));
    flowPageMonthMap.put("flowpage", getFlowPageByMonthMap(date));
    return flowPageMonthMap;
  }
}

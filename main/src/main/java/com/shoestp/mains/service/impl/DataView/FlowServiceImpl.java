package com.shoestp.mains.service.impl.DataView;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.DataView.flow.FlowDao;
import com.shoestp.mains.dao.DataView.flow.FlowPageDao;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.service.DataView.FlowService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.flow.*;
import com.sun.org.apache.xerces.internal.xs.StringList;

import org.springframework.stereotype.Service;

/**
 * @description: 流量-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:13
 */
@Service
public class FlowServiceImpl implements FlowService {

  @Resource private FlowDao flowDao;
  @Resource private FlowPageDao flowPageDao;

  /** 获取当天0:0:0 */
  private Date start = DateTimeUtil.getTimesmorning();
  /** 获取当天23:59:59 */
  private Date end = DateTimeUtil.getTimesnight();

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
          flowDao.findAllByDeviceType(device, start, end).stream()
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
   * @param startDate
   * @param endDate
   * @return List<FlowDeviceView>
   */
  @Override
  public List<FlowDeviceView> getFlowDevice(Date startDate, Date endDate) {
    List<FlowDeviceView> flowDeviceViewList =
        flowDao
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
    return flowDeviceViewList;
  }

  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2019/5/14 14:12
   * @param startDate
   * @param endDate
   * @return
   */
  @Override
  public List<FlowSourceView> getFlowSourceType(Date startDate, Date endDate) {
    return flowDao
        .findAllBySourceType(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
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

  /**
   * 根据时间获取每个流量来源的访客数
   *
   * @author: lingjian @Date: 2019/5/14 15:08
   * @param date
   * @param start
   * @param end
   * @return
   */
  public Map<String, List<FlowSourceView>> getFlowSourceTypeByHour(Date date, int start, int end) {
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
  public int[] getEveryDay(Date date, SourceTypeEnum source) {
    int[] arr = new int[23];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowSourceTypeByHour(date, i, i + 1).get(source.toString()).isEmpty()) {
        arr[i] =
            getFlowSourceTypeByHour(date, i, i + 1).get(source.toString()).get(0).getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 获取某一天24个小时每个小时的流量概况
   *
   * @author: lingjian @Date: 2019/5/14 15:07
   * @param date
   * @return
   */
  @Override
  public Map<String, int[]> getFlowSourceTypeTimeByDay(Date date) {
    Map<String, int[]> inquiryTimeMap = new HashMap<>();
    inquiryTimeMap.put("BAIDU", getEveryDay(date, SourceTypeEnum.BAIDU));
    inquiryTimeMap.put("GOOGLE", getEveryDay(date, SourceTypeEnum.GOOGLE));
    inquiryTimeMap.put("INTERVIEW", getEveryDay(date, SourceTypeEnum.INTERVIEW));
    inquiryTimeMap.put("OTHER", getEveryDay(date, SourceTypeEnum.OTHER));
    return inquiryTimeMap;
  }

  /**
   * 根据时间获取某一天开始的前一周的每一天的流量来源的访客数
   *
   * @author: lingjian @Date: 2019/5/15 11:43
   * @param date
   * @param source
   * @return
   */
  public int[] getEvery(int num, Date date, SourceTypeEnum source) {
    int[] arr = new int[num];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowSourceTypeByHour(DateTimeUtil.getDayFromNum(date, i), 0, 24)
          .get(source.toString())
          .isEmpty()) {
        arr[i] =
            getFlowSourceTypeByHour(DateTimeUtil.getDayFromNum(date, i), 0, 24)
                .get(source.toString())
                .get(0)
                .getVisitorCount();
      }
    }
    return arr;
  }

  /**
   * 获取某一天开始的前一周的每一天的流量概况
   *
   * @author: lingjian @Date: 2019/5/15 11:43
   * @param date
   * @return
   */
  @Override
  public Map<String, int[]> getFlowSourceTypeTimeByWeek(Date date) {
    Map<String, int[]> inquiryWeekMap = new HashMap<>();
    inquiryWeekMap.put("BAIDU", getEvery(7, date, SourceTypeEnum.BAIDU));
    inquiryWeekMap.put("GOOGLE", getEvery(7, date, SourceTypeEnum.GOOGLE));
    inquiryWeekMap.put("INTERVIEW", getEvery(7, date, SourceTypeEnum.INTERVIEW));
    inquiryWeekMap.put("OTHER", getEvery(7, date, SourceTypeEnum.OTHER));
    return inquiryWeekMap;
  }

  /**
   * 获取某一天开始的一个月三十天每一天的流量概况
   *
   * @author: lingjian @Date: 2019/5/15 13:38
   * @param date
   * @return
   */
  @Override
  public Map<String, int[]> getFlowSourceTypeTimeByMonth(Date date) {
    Map<String, int[]> inquiryMonthMap = new HashMap<>();
    inquiryMonthMap.put("BAIDU", getEvery(30, date, SourceTypeEnum.BAIDU));
    inquiryMonthMap.put("GOOGLE", getEvery(30, date, SourceTypeEnum.GOOGLE));
    inquiryMonthMap.put("INTERVIEW", getEvery(30, date, SourceTypeEnum.INTERVIEW));
    inquiryMonthMap.put("OTHER", getEvery(30, date, SourceTypeEnum.OTHER));
    return inquiryMonthMap;
  }

  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:27
   * @param startDate
   * @param endDate
   * @return Map<String, List>
   */
  @Override
  public Map<String, List> getFlowSourcePage(Date startDate, Date endDate) {
    Map<String, List> flowViewMap = new HashMap<>();
    for (SourceTypeEnum source : SourceTypeEnum.values()) {
      flowViewMap.put(
          source.name(),
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
                    return flowSourcePageView;
                  })
              .collect(Collectors.toList()));
    }
    return flowViewMap;
  }

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param startDate
   * @param endDate
   * @return
   */
  @Override
  public List getFlowPageAnalysis(Date startDate, Date endDate) {
    return flowPageDao
        .findAllByAccessPage(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
        .stream()
        .map(
            bean -> {
              AccessPageView accessPageView = new AccessPageView();
              accessPageView.setAccessType(bean.get(0, String.class));
              accessPageView.setVisitorCount(bean.get(1, Integer.class));
              accessPageView.setViewCount(bean.get(2, Integer.class));
              accessPageView.setClickRate(bean.get(3, Double.class));
              accessPageView.setJumpRate(bean.get(4, Double.class));
              accessPageView.setAverageStayTime(bean.get(5, Double.class));
              return accessPageView;
            })
        .collect(Collectors.toList());
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
                    accessPageView.setClickRate(bean.get(3, Double.class));
                    accessPageView.setJumpRate(bean.get(4, Double.class));
                    accessPageView.setAverageStayTime(bean.get(5, Double.class));
                    return accessPageView;
                  })
              .collect(Collectors.toList()));
    }
    return accessPageMap;
  }

  /**
   * 根据时间和页面分类，获取一天24小时每个小时的页面参数 (访客数，浏览量，点击率，跳失率，平均停留时长)
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date
   * @param access
   * @param parameter
   * @return
   */
  public double[] getAccessHour(Date date, AccessTypeEnum access, String parameter) {
    double[] arr = new double[23];
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
   * 根据时间和页面，获取某几天每一天的页面参数(访客数，浏览量，点击率，跳失率，平均停留时长)
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
      if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "visitorCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
                .get(access.toString())
                .get(0)
                .getVisitorCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "viewCount".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
                .get(access.toString())
                .get(0)
                .getViewCount()
                .doubleValue();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "clickRate".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
                .get(access.toString())
                .get(0)
                .getClickRate();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "jumpRate".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
                .get(access.toString())
                .get(0)
                .getJumpRate();
      } else if (!getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
              .get(access.toString())
              .isEmpty()
          && "averageStayTime".equals(parameter)) {
        arr[i] =
            getFlowPageAnalysisByAccess(DateTimeUtil.getDayFromNum(date, i), 0, 24)
                .get(access.toString())
                .get(0)
                .getAverageStayTime();
      }
    }
    return arr;
  }

  /**
   * 根据时间和页面分类，获取一天24小时每个小时的页面参数
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date
   * @param access
   * @return
   */
  @Override
  public Map<String, double[]> getFlowPageAnalysisByDay(Date date, AccessTypeEnum access) {
    Map<String, double[]> analysisDayMap = new HashMap<>();
    analysisDayMap.put("visitorCount", getAccessHour(date, access, "visitorCount"));
    analysisDayMap.put("viewCount", getAccessHour(date, access, "viewCount"));
    analysisDayMap.put("clickRate", getAccessHour(date, access, "clickRate"));
    analysisDayMap.put("jumpRate", getAccessHour(date, access, "jumpRate"));
    analysisDayMap.put("averageStayTime", getAccessHour(date, access, "averageStayTime"));
    return analysisDayMap;
  }

  /**
   * 根据时间和页面，获取一周七天每一天的页面参数
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param date
   * @param access
   * @return
   */
  @Override
  public Map<String, double[]> getFlowPageAnalysisByWeek(Date date, AccessTypeEnum access) {
    Map<String, double[]> analysisWeekMap = new HashMap<>();
    analysisWeekMap.put("visitorCount", getAccessDay(7, date, access, "visitorCount"));
    analysisWeekMap.put("viewCount", getAccessDay(7, date, access, "viewCount"));
    analysisWeekMap.put("clickRate", getAccessDay(7, date, access, "clickRate"));
    analysisWeekMap.put("jumpRate", getAccessDay(7, date, access, "jumpRate"));
    analysisWeekMap.put("averageStayTime", getAccessDay(7, date, access, "averageStayTime"));
    return analysisWeekMap;
  }

  /**
   * 根据时间和页面，获取一个月三十天每一天的页面参数
   *
   * @author: lingjian @Date: 2019/5/15 15:35
   * @param date
   * @param access
   * @return
   */
  @Override
  public Map<String, double[]> getFlowPageAnalysisByMonth(Date date, AccessTypeEnum access) {
    Map<String, double[]> analysisMonthMap = new HashMap<>();
    analysisMonthMap.put("visitorCount", getAccessDay(30, date, access, "visitorCount"));
    analysisMonthMap.put("viewCount", getAccessDay(30, date, access, "viewCount"));
    analysisMonthMap.put("clickRate", getAccessDay(30, date, access, "clickRate"));
    analysisMonthMap.put("jumpRate", getAccessDay(30, date, access, "jumpRate"));
    analysisMonthMap.put("averageStayTime", getAccessDay(30, date, access, "averageStayTime"));
    return analysisMonthMap;
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
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/5/14 16:47
   * @param date
   * @return
   */
  @Override
  public List<PageView> getFlowPage(Date date) {
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
  public double[] getEveryPage(Date date, String parameter) {
    double[] arr = new double[30];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowPage(DateTimeUtil.getDayFromNum(date, i)).isEmpty()
          && "viewAvgCount".equals(parameter)) {
        arr[i] =
            getFlowPage(DateTimeUtil.getDayFromNum(date, i)).get(0).getViewAvgCount().doubleValue();
      } else if (!getFlowPage(DateTimeUtil.getDayFromNum(date, i)).isEmpty()
          && "jumpRate".equals(parameter)) {
        arr[i] =
            getFlowPage(DateTimeUtil.getDayFromNum(date, i)).get(0).getJumpRate().doubleValue();
      } else if (!getFlowPage(DateTimeUtil.getDayFromNum(date, i)).isEmpty()
          && "averageStayTime".equals(parameter)) {
        arr[i] =
            getFlowPage(DateTimeUtil.getDayFromNum(date, i))
                .get(0)
                .getAverageStayTime()
                .doubleValue();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2019/5/15 14:27
   * @param date
   * @return
   */
  @Override
  public Map<String, double[]> getFlowPageByMonth(Date date) {
    Map<String, double[]> flowPageMonthMap = new HashMap<>();
    flowPageMonthMap.put("viewAvgCount", getEveryPage(date, "viewAvgCount"));
    flowPageMonthMap.put("jumpRate", getEveryPage(date, "jumpRate"));
    flowPageMonthMap.put("averageStayTime", getEveryPage(date, "averageStayTime"));
    return flowPageMonthMap;
  }
}

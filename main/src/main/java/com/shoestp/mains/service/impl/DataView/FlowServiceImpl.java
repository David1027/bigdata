package com.shoestp.mains.service.impl.DataView;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.flow.FlowDao;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.service.DataView.FlowService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.flow.FlowSourcePageView;
import com.shoestp.mains.views.DataView.flow.FlowSourceView;
import com.shoestp.mains.views.DataView.flow.FlowDeviceView;
import com.sun.org.apache.xerces.internal.xs.StringList;

import org.springframework.stereotype.Service;

/**
 * @description: 流量-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:13
 */
@Service
public class FlowServiceImpl implements FlowService {

  @Resource private FlowDao flowDao;

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
  public List<FlowSourceView> getFlowSourceTypeByHour(Date date, int start, int end) {
    return flowDao
        .findAllBySourceType(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end))
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
   * 根据时间获取24个小时中每个小时流量来源的访客数
   *
   * @author: lingjian @Date: 2019/5/14 15:08
   * @param date
   * @param num
   * @return
   */
  public int[] getEveryHour(Date date, int num) {
    int[] arr = new int[23];
    for (int i = 0; i < arr.length; i++) {
      if (!getFlowSourceTypeByHour(date, i, i + 1).isEmpty()) {
        arr[i] = getFlowSourceTypeByHour(date, i, i + 1).get(num).getVisitorCount().intValue();
      }
    }
    return arr;
  }

  /**
   * 根据时间获取流量概况
   *
   * @author: lingjian @Date: 2019/5/14 15:07
   * @param date
   * @return
   */
  @Override
  public Map<String, int[]> getFlowSourceTypeTime(Date date) {
    Map<String, int[]> inquiryTimeMap = new HashMap<>();
    inquiryTimeMap.put("BAIDU", getEveryHour(date, 0));
    inquiryTimeMap.put("GOOGLE", getEveryHour(date, 1));
    inquiryTimeMap.put("INTERVIEW", getEveryHour(date, 2));
    inquiryTimeMap.put("OTHER", getEveryHour(date, 3));
    return inquiryTimeMap;
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
}

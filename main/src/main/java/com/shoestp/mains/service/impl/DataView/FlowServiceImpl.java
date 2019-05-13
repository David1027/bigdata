package com.shoestp.mains.service.impl.DataView;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.FlowDao;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.service.DataView.FlowService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.flow.FlowSourceView;
import com.shoestp.mains.views.DataView.flow.FlowDeviceView;

import org.springframework.stereotype.Service;

/**
 * @description: 流量-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:13
 */
@Service
public class FlowServiceImpl implements FlowService {

  @Resource private FlowDao flowDao;

  // 获取当天0:0:0
  private Date start = DateTimeUtil.getTimesmorning();
  // 获取当天23:59:59
  private Date end = DateTimeUtil.getTimesnight();

  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2019/5/10 16:44
   * @return Map<String, List>
   */
  @Override
  public Map<String, List> getFlowSource() {
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
      List<FlowDeviceView> flowDeviceViewList = flowDao
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
}

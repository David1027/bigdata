package com.shoestp.mains.dao.xwt.dataview.repository;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.entitys.xwt.dataview.flow.XwtViewFlow;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

/**
 * (Flow)表数据访问层自定义接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewFlowRepository {

  /**
   * 根据设备来源，当天时间，设备来源分组获取流量表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param device 设备来源
   * @return List<XwtViewFlow> 流量表集合对象
   */
  List<XwtViewFlow> listByCreateTimeAndDevice(Date start, Date end, DeviceTypeEnum device);

  /**
   * 根据时间分组获取流量表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlow> 流量表集合对象
   */
  List<XwtViewFlow> listByCreateTimeAndGroupDevice(Date start, Date end);

  /**
   * 根据来源类型和时间分组获取流量表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param source 来源类型
   * @return XwtViewFlow 流量表对象
   */
  XwtViewFlow listByCreateTimeAndSource(Date start, Date end, SourceTypeEnum source);
}

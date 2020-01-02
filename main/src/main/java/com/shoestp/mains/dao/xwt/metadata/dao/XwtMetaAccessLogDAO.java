package com.shoestp.mains.dao.xwt.metadata.dao;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.dao.xwt.metadata.repository.XwtMetaAccessLogRepository;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 访问日志数据访问层接口
 * @author: lingjian
 * @create: 2019/12/27 10:29
 */
public interface XwtMetaAccessLogDAO
    extends XwtMetaAccessLogRepository, JpaRepository<XwtMetaAccessLog, Integer> {

  XwtMetaAccessLog findBySsIdAndSsCount(String ssId, Integer ssCount);

  /**
   * 根据时间和设备来源获取浏览量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return Long浏览量
   */
  Long countByCreateTimeBetween(Date start, Date end);

  /**
   * 根据时间，设备类型，来源类型获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param deviceType 设备类型
   * @param sourceType 来源类型
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  List<XwtMetaAccessLog> findAllByCreateTimeBetweenAndDeviceTypeAndSourceType(
          Date start, Date end, DeviceTypeEnum deviceType, SourceTypeEnum sourceType);

  /**
   * 根据时间和页面类型获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param accessType 页面类型
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  List<XwtMetaAccessLog> findAllByCreateTimeBetweenAndAccessType(
          Date start, Date end, OAccessTypeEnum accessType);

}

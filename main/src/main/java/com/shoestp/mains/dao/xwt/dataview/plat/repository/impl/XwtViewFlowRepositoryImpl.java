package com.shoestp.mains.dao.xwt.dataview.plat.repository.impl;

import java.util.Date;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewFlowRepository;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.QXwtViewFlow;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlow;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import org.springframework.stereotype.Repository;

/**
 * (Flow)表数据访问层自定义接口实现类
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
@Repository
public class XwtViewFlowRepositoryImpl extends BaseDao<XwtViewFlow>
    implements XwtViewFlowRepository {

  private QXwtViewFlow qXwtViewFlow = QXwtViewFlow.xwtViewFlow;

  /**
   * 根据设备来源，当天时间，设备来源分组获取访客数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param device 设备来源
   * @return List<XwtViewFlow> 流量表集合对象
   */
  @Override
  public List<XwtViewFlow> listByCreateTimeAndDevice(Date start, Date end, DeviceTypeEnum device) {
    JPAQuery<XwtViewFlow> query =
        getQuery()
            .select(
                Projections.bean(
                    XwtViewFlow.class,
                    qXwtViewFlow.deviceType.as("deviceType"),
                    qXwtViewFlow.sourceType.as("sourceType"),
                    qXwtViewFlow.visitorCount.sum().as("visitorCount"),
                    qXwtViewFlow.viewCount.sum().as("viewCount")))
            .from(qXwtViewFlow);
    // 设备来源
    if (device != null) {
      query.where(qXwtViewFlow.deviceType.eq(device));
    }
    query.where(qXwtViewFlow.createTime.between(start, end)).groupBy(qXwtViewFlow.sourceType);
    return query.fetchResults().getResults();
  }

  /**
   * 根据时间分组获取设备来源的访客数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlow> 流量表集合对象
   */
  @Override
  public List<XwtViewFlow> listByCreateTimeAndGroupDevice(Date start, Date end) {
    return getQuery()
        .select(
            Projections.bean(
                XwtViewFlow.class,
                qXwtViewFlow.deviceType.as("deviceType"),
                qXwtViewFlow.sourceType.as("sourceType"),
                qXwtViewFlow.visitorCount.sum().as("visitorCount"),
                qXwtViewFlow.viewCount.sum().as("viewCount")))
        .from(qXwtViewFlow)
        .where(qXwtViewFlow.createTime.between(start, end))
        .groupBy(qXwtViewFlow.deviceType)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据来源类型和时间分组获取流量表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param source 来源类型
   * @return XwtViewFlow 流量表对象
   */
  @Override
  public XwtViewFlow listByCreateTimeAndSource(Date start, Date end, SourceTypeEnum source) {
    return getQuery()
        .select(
            Projections.bean(
                XwtViewFlow.class,
                qXwtViewFlow.deviceType.as("deviceType"),
                qXwtViewFlow.sourceType.as("sourceType"),
                qXwtViewFlow.visitorCount.sum().as("visitorCount"),
                qXwtViewFlow.viewCount.sum().as("viewCount")))
        .from(qXwtViewFlow)
        .where(qXwtViewFlow.sourceType.eq(source))
        .where(qXwtViewFlow.createTime.between(start, end))
        .groupBy(qXwtViewFlow.sourceType)
        .fetchFirst();
  }
}

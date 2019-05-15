package com.shoestp.mains.dao.DataView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.DataViewFlow;
import com.shoestp.mains.entitys.DataView.QDataViewFlow;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.repositorys.DataView.FlowRepository;

/**
 * @description: 流量-数据层
 * @author: lingjian @Date: 2019/5/9 10:12
 */
@Repository
public class FlowDao extends BaseDao<DataViewFlow> {

  @Resource private FlowRepository flowRepository;

  /**
   * 根据设备来源，当天时间，流量来源分组获取访客数
   *
   * @author: lingjian @Date: 2019/5/10 16:45
   * @param device 设备来源
   * @param start 开始时间
   * @param end 结束时间
   * @return
   */
  public List<Tuple> findAllByDeviceType(DeviceTypeEnum device, Date start, Date end) {
    QDataViewFlow dataViewFlow = QDataViewFlow.dataViewFlow;
    return getQuery()
        .select(
            dataViewFlow.sourceType.stringValue().as("sourceType"),
            dataViewFlow.visitorCount.sum().as("visitCount"))
        .from(dataViewFlow)
        .where(dataViewFlow.deviceType.eq(device))
        .where(dataViewFlow.createTime.between(start, end))
        .groupBy(dataViewFlow.sourceType)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间获取设备来源的访客数
   *
   * @author: lingjian @Date: 2019/5/13 9:58
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllByDeviceCount(Date start, Date end) {
    QDataViewFlow dataViewFlow = QDataViewFlow.dataViewFlow;
    return getQuery()
        .select(
            dataViewFlow.deviceType.stringValue().as("deviceType"),
            dataViewFlow.visitorCount.sum().as("visitCount"))
        .from(dataViewFlow)
        .where(dataViewFlow.createTime.between(start, end))
        .groupBy(dataViewFlow.deviceType)
        .fetchResults()
        .getResults();
  }

  @Override
  public DataViewFlow find(DataViewFlow dataViewFlow) {
    return null;
  }

  @Override
  public DataViewFlow findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewFlow dataViewFlow) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewFlow> list) {
    return 0;
  }

  @Override
  public int remove(DataViewFlow dataViewFlow) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }

  public Optional<DataViewFlow> getFlowTopOne() {
    Optional<DataViewFlow> flow = flowRepository.findTopByOrderByCreateTimeDesc();
    return flow;
  }
}

package com.shoestp.mains.dao.dataview.flow;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataview.flow.QDataViewFlow;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.repositorys.dataview.flow.FlowRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: 流量-数据层
 * @author: lingjian @Date: 2019/5/9 10:12
 */
@Repository
public class FlowDao extends BaseDao<DataViewFlow> {

  @Resource private FlowRepository flowRepository;

  /**
   * 新增流量表
   *
   * @param dataViewFlow 流量表
   */
  public void saveFlow(DataViewFlow dataViewFlow) {
    flowRepository.save(dataViewFlow);
  }

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

  /**
   * 根据当天时间，来源类型分组获取访客数
   *
   * @author: lingjian @Date: 2019/5/14 14:13
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllBySourceType(Date start, Date end) {
    QDataViewFlow dataViewFlow = QDataViewFlow.dataViewFlow;
    return getQuery()
        .select(
            dataViewFlow.sourceType.stringValue(),
            dataViewFlow.visitorCount.sum(),
            dataViewFlow.inquiryCount.sum())
        .from(dataViewFlow)
        .where(dataViewFlow.createTime.between(start, end))
        .groupBy(dataViewFlow.sourceType)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据来源类型，当天时间，分组获取来源类型的访客数
   *
   * @author: lingjian @Date: 2019/5/15 13:44
   * @param source
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllBySource(SourceTypeEnum source, Date start, Date end) {
    QDataViewFlow dataViewFlow = QDataViewFlow.dataViewFlow;
    return getQuery()
        .select(dataViewFlow.sourceType.stringValue(), dataViewFlow.visitorCount.sum())
        .from(dataViewFlow)
        .where(dataViewFlow.sourceType.eq(source))
        .where(dataViewFlow.createTime.between(start, end))
        .groupBy(dataViewFlow.sourceType)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据当天时间，来源类型，来源渠道分组获取访客数
   *
   * @author: lingjian @Date: 2019/5/14 14:28
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllBySourcePage(SourceTypeEnum source, Date start, Date end) {
    QDataViewFlow dataViewFlow = QDataViewFlow.dataViewFlow;
    return getQuery()
        .select(
            dataViewFlow.sourcePage.stringValue(),
            dataViewFlow.visitorCount.sum(),
            dataViewFlow.inquiryCount.sum())
        .from(dataViewFlow)
        .where(dataViewFlow.sourceType.eq(source))
        .where(dataViewFlow.createTime.between(start, end))
        .groupBy(dataViewFlow.sourcePage)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据来源类型，来源渠道，时间，获取来源渠道的访客数
   *
   * @author: lingjian @Date: 2019/5/17 16:24
   * @param sourceType
   * @param sourcePage
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllBySourceTypeAndSourcePage(
      SourceTypeEnum sourceType, String sourcePage, Date start, Date end) {
    QDataViewFlow dataViewFlow = QDataViewFlow.dataViewFlow;
    return getQuery()
        .select(
            dataViewFlow.sourcePage.stringValue(),
            dataViewFlow.visitorCount.sum(),
            dataViewFlow.inquiryCount.sum())
        .from(dataViewFlow)
        .where(dataViewFlow.sourceType.eq(sourceType))
        .where(dataViewFlow.sourcePage.eq(sourcePage))
        .where(dataViewFlow.createTime.between(start, end))
        .groupBy(dataViewFlow.sourcePage)
        .fetchResults()
        .getResults();
  }

  public Optional<DataViewFlow> getFlowTopOne() {
    Optional<DataViewFlow> flow = flowRepository.findTopByOrderByCreateTimeDesc();
    return flow;
  }

  public void save(DataViewFlow flow) {
    flowRepository.save(flow);
  }
}

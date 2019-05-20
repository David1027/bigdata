package com.shoestp.mains.dao.DataView.flow;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.DataView.flow.QDataViewFlowPage;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.repositorys.DataView.flow.FlowPageRepository;

/**
 * @description: 流量-数据层
 * @author: lingjian @Date: 2019/5/9 10:12
 */
@Repository
public class FlowPageDao extends BaseDao<DataViewFlowPage> {

  @Resource private FlowPageRepository flowPageRepository;

  /**
   * 根据页面类型，当前时间获取数据记录
   *
   * @author: lingjian @Date: 2019/5/14 16:28
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllByAccessPage(Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(
            qDataViewFlowPage.accessType.stringValue(),
            qDataViewFlowPage.visitorCount.sum(),
            qDataViewFlowPage.viewCount.sum(),
            qDataViewFlowPage.clickRate.avg(),
            qDataViewFlowPage.jumpRate.avg(),
            qDataViewFlowPage.averageStayTime.avg())
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.createTime.between(start, end))
        .groupBy(qDataViewFlowPage.accessType)
        .fetchResults()
        .getResults();
  }

  public List<Tuple> findAllByAccess(AccessTypeEnum access, Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(
            qDataViewFlowPage.accessType.stringValue(),
            qDataViewFlowPage.visitorCount.sum(),
            qDataViewFlowPage.viewCount.sum(),
            qDataViewFlowPage.clickRate.avg(),
            qDataViewFlowPage.jumpRate.avg(),
            qDataViewFlowPage.averageStayTime.avg())
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.accessType.eq(access))
        .where(qDataViewFlowPage.createTime.between(start, end))
        .groupBy(qDataViewFlowPage.accessType)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间获取跳失率，访客数，浏览量，平均停留时长
   *
   * @author: lingjian @Date: 2019/5/14 16:47
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllByCreateTime(Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(
            qDataViewFlowPage.visitorCount.sum(),
            qDataViewFlowPage.viewCount.sum(),
            qDataViewFlowPage.jumpRate.avg(),
            qDataViewFlowPage.averageStayTime.avg())
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.createTime.between(start, end))
        .fetchResults()
        .getResults();
  }

  @Override
  public DataViewFlowPage find(DataViewFlowPage dataViewFlowPage) {
    return null;
  }

  @Override
  public DataViewFlowPage findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewFlowPage dataViewFlowPage) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewFlowPage> list) {
    return 0;
  }

  @Override
  public int remove(DataViewFlowPage dataViewFlowPage) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }

  public void save(DataViewFlowPage flowPage) {
    flowPageRepository.save(flowPage);
  }
}

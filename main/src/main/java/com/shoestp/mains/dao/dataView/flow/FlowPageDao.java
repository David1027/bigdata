package com.shoestp.mains.dao.DataView.flow;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.DataView.flow.QDataViewFlowPage;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.repositorys.DataView.flow.FlowPageRepository;
import com.shoestp.mains.views.DataView.flow.AccessView;
import com.shoestp.mains.views.DataView.flow.PageViewObject;

/**
 * @description: 流量-数据层
 * @author: lingjian @Date: 2019/5/9 10:12
 */
@Repository
public class FlowPageDao extends BaseDao<DataViewFlowPage> {

  @Resource private FlowPageRepository flowPageRepository;

  /**
   * 根据时间获取总访客数
   *
   * @author: lingjian @Date: 2019/5/17 11:40
   * @param start
   * @param end
   * @return
   */
  public Integer findAllByAccessTotal(Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(qDataViewFlowPage.visitorCount.sum())
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.createTime.between(start, end))
        .fetchFirst();
  }

  /**
   * 根据页面类型，当前时间获取访客数，返回集合
   *
   * @param access
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllByAccess(AccessTypeEnum access, Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(
            qDataViewFlowPage.accessType.stringValue(),
            qDataViewFlowPage.visitorCount.sum(),
            qDataViewFlowPage.viewCount.sum(),
            qDataViewFlowPage.clickCount.sum(),
            qDataViewFlowPage.clickNumber.sum(),
            qDataViewFlowPage.clickRate.avg(),
            qDataViewFlowPage.jumpRate.avg(),
            qDataViewFlowPage.averageStayTime.avg())
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.accessType.eq(access))
        .where(qDataViewFlowPage.createTime.between(start, end))
        .fetchResults()
        .getResults();
  }

  /**
   * 根据页面类型，当前时间获取访客数
   *
   * @author: lingjian @Date: 2019/5/21 9:46
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllByAccessBy(Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(qDataViewFlowPage.accessType.stringValue(), qDataViewFlowPage.visitorCount.sum())
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.createTime.between(start, end))
        .groupBy(qDataViewFlowPage.accessType)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间获取跳失率,返回对象
   *
   * @author: lingjian @Date: 2019/5/21 9:47
   * @param start
   * @param end
   * @return
   */
  public Double findByCreateTimeObject(Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(qDataViewFlowPage.jumpRate.avg().as("jumpRate"))
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.createTime.between(start, end))
        .fetchFirst();
  }

  /**
   * 根据时间获取跳失率，访客数，浏览量，平均停留时长,返回对象
   *
   * @author: lingjian @Date: 2019/5/21 9:47
   * @param start
   * @param end
   * @return
   */
  public PageViewObject findAllByCreateTimeObject(Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    return getQuery()
        .select(
            Projections.bean(
                PageViewObject.class,
                qDataViewFlowPage.visitorCount.sum().as("visitorCount"),
                qDataViewFlowPage.viewCount.sum().as("viewCount"),
                qDataViewFlowPage.jumpRate.avg().as("jumpRate"),
                qDataViewFlowPage.averageStayTime.avg().as("averageStayTime")))
        .from(qDataViewFlowPage)
        .where(qDataViewFlowPage.createTime.between(start, end))
        .fetchFirst();
  }

  /**
   * 根据时间获取跳失率，访客数，浏览量，平均停留时长,返回集合
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

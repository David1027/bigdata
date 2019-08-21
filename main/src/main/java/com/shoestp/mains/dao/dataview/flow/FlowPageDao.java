package com.shoestp.mains.dao.dataview.flow;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataview.flow.QDataViewFlowPage;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.repositorys.dataview.flow.FlowPageRepository;
import com.shoestp.mains.views.dataview.flow.PageViewObject;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description: 流量-数据层
 * @author: lingjian @Date: 2019/5/9 10:12
 */
@Repository
public class FlowPageDao extends BaseDao<DataViewFlowPage> {

  @Resource private FlowPageRepository flowPageRepository;

  /**
   * 新增页面分析表
   *
   * @author: lingjian @Date: 2019/8/8 13:34
   * @param dataViewFlowPage 页面分析对象
   */
  public void saveFlowPage(DataViewFlowPage dataViewFlowPage) {
    flowPageRepository.save(dataViewFlowPage);
  }

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
    Integer result =
        getQuery()
            .select(qDataViewFlowPage.visitorCount.sum())
            .from(qDataViewFlowPage)
            .where(qDataViewFlowPage.createTime.between(start, end))
            .fetchFirst();
    return result == null ? 0 : result;
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
   * 根据页面类型，时间获取访客数
   *
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countAccessByType(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QDataViewFlowPage qDataViewFlowPage = QDataViewFlowPage.dataViewFlowPage;
    Integer result =
        getQuery()
            .select(qDataViewFlowPage.visitorCount.sum())
            .from(qDataViewFlowPage)
            .where(qDataViewFlowPage.accessType.eq(accessTypeEnum))
            .where(qDataViewFlowPage.createTime.between(start, end))
            .fetchFirst();
    return result == null ? 0 : result;
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

  public void save(DataViewFlowPage flowPage) {
    flowPageRepository.save(flowPage);
  }
}

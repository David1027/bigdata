package com.shoestp.mains.dao.xwt.dataview.repository.impl;

import java.util.Date;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.xwt.dataview.repository.XwtViewFlowPageRepository;
import com.shoestp.mains.entitys.xwt.dataview.flow.QXwtViewFlowPage;
import com.shoestp.mains.entitys.xwt.dataview.flow.XwtViewFlowPage;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;

import org.springframework.stereotype.Repository;

/**
 * (FlowPage)表数据访问层自定义接口实现类
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
@Repository
public class XwtViewFlowPageRepositoryImpl extends BaseDao<XwtViewFlowPage>
    implements XwtViewFlowPageRepository {

  private QXwtViewFlowPage qXwtViewFlowPage = QXwtViewFlowPage.xwtViewFlowPage;

  /**
   * 根据时间获取跳失率
   *
   * @author: lingjian @Date: 2020/1/3 11:14
   * @param start 开始时间
   * @param end 结束时间
   * @return Double 平均跳失率
   */
  @Override
  public Double findByCreateTimeBetween(Date start, Date end) {
    Double result =
        getQuery()
            .select(qXwtViewFlowPage.jumpRate.avg().as("jumpRate"))
            .from(qXwtViewFlowPage)
            .where(qXwtViewFlowPage.createTime.between(start, end))
            .fetchFirst();
    return result == null ? 0.0 : result;
  }

  /**
   * 根据页面类型和时间获取访客数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param access 页面类型
   * @return Long 访客数
   */
  @Override
  public Long countByCreateTimeAndAccess(Date start, Date end, OAccessTypeEnum access) {
    JPAQuery<Long> query =
        getQuery().select(qXwtViewFlowPage.visitorCount.sum()).from(qXwtViewFlowPage);
    // 页面类型
    if (access != null) {
      query.where(qXwtViewFlowPage.accessType.eq(access));
    }
    Long result = query.where(qXwtViewFlowPage.createTime.between(start, end)).fetchFirst();
    return result == null ? 0 : result;
  }

  /**
   * 根据页面类型和时间获取流量页面对象
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param access 页面类型
   * @return XwtViewFlowPage 流量页面对象
   */
  @Override
  public XwtViewFlowPage listByCreateTimeAndAccess(Date start, Date end, OAccessTypeEnum access) {
    JPAQuery<XwtViewFlowPage> query =
        getQuery()
            .select(
                Projections.bean(
                    XwtViewFlowPage.class,
                    qXwtViewFlowPage.accessType.as("accessType"),
                    qXwtViewFlowPage.visitorCount.sum().as("visitorCount"),
                    qXwtViewFlowPage.viewCount.sum().as("viewCount"),
                    qXwtViewFlowPage.clickRate.avg().as("clickRate"),
                    qXwtViewFlowPage.jumpRate.avg().as("jumpRate"),
                    qXwtViewFlowPage.averageStayTime.avg().as("averageStayTime")))
            .from(qXwtViewFlowPage);
    if (access != null) {
      query.where(qXwtViewFlowPage.accessType.eq(access));
    }
    return query.where(qXwtViewFlowPage.createTime.between(start, end)).fetchFirst();
  }
}

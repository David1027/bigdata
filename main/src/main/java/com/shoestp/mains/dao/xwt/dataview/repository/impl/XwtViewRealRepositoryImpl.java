package com.shoestp.mains.dao.xwt.dataview.repository.impl;

import java.util.Date;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.xwt.dataview.repository.XwtViewRealRepository;
import com.shoestp.mains.entitys.xwt.dataview.real.QXwtViewReal;
import com.shoestp.mains.entitys.xwt.dataview.real.XwtViewReal;

import org.springframework.stereotype.Repository;

/**
 * (Real)表数据访问层自定义接口实现类
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
@Repository
public class XwtViewRealRepositoryImpl extends BaseDao<XwtViewReal>
    implements XwtViewRealRepository {

  private QXwtViewReal qXwtViewReal = QXwtViewReal.xwtViewReal;

  /**
   * 根据时间获取实时表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewReal 实时表对象
   */
  @Override
  public XwtViewReal findByCreateTimeBetween(Date start, Date end) {
    return getQuery()
        .select(
            Projections.bean(
                XwtViewReal.class,
                qXwtViewReal.visitorCount.sum().as("visitorCount"),
                qXwtViewReal.visitorCountPc.sum().as("visitorCountPc"),
                qXwtViewReal.visitorCountWap.sum().as("visitorCountWap"),
                qXwtViewReal.pageViewsCount.sum().as("pageViewsCount"),
                qXwtViewReal.registerCount.sum().as("registerCount")))
        .from(qXwtViewReal)
        .where(qXwtViewReal.createTime.between(start, end))
        .fetchOne();
  }
}

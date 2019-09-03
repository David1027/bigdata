package com.shoestp.mains.dao.dataview.real;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.entitys.dataview.real.QDataViewReal;
import com.shoestp.mains.repositorys.dataview.real.RealRepository;
import com.shoestp.mains.views.dataview.real.IndexGrand;
import com.shoestp.mains.views.dataview.real.RealView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description: 国家-数据层
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@Repository
public class RealDao extends BaseDao<DataViewReal> {

  @Resource private RealRepository realRepository;

  /**
   * 新增DataViewReal记录
   *
   * @author: lingjian @Date: 2019/8/13 10:08
   * @param dataViewReal real对象
   */
  public void save(DataViewReal dataViewReal) {
    realRepository.save(dataViewReal);
  }

  /**
   * 根据时间间隔获取实时表所有的记录
   *
   * @author: lingjian @Date: 2019/8/15 14:00
   * @param start 开始时间
   * @param end 结束时间
   * @return RealView 实时前端展示类
   */
  public RealView findAllByCreateTimeBetween(Date start, Date end) {
    QDataViewReal qDataViewReal = QDataViewReal.dataViewReal;
    return getQuery()
        .select(
            Projections.bean(
                RealView.class,
                qDataViewReal.visitorCount.sum().as("visitorCount"),
                qDataViewReal.pageViewsCount.sum().as("viewCount"),
                qDataViewReal.registerCount.sum().as("registerCount"),
                qDataViewReal.inquiryCount.sum().as("inquiryCount"),
                qDataViewReal.rfqCount.sum().as("rfqCount")))
        .from(qDataViewReal)
        .where(qDataViewReal.createTime.between(start, end))
        .fetchOne();
  }

  /**
   * 获取今天之前累计的询盘量，RFQ数，注册量
   *
   * @author: lingjian @Date: 2019/8/15 10:14
   * @param date 时间
   * @return IndexGrand首页实时前端展示类
   */
  public IndexGrand findByCreateTimeBefore(Date date) {
    QDataViewReal qDataViewReal = QDataViewReal.dataViewReal;
    return getQuery()
        .select(
            Projections.bean(
                IndexGrand.class,
                qDataViewReal.registerCount.sum().as("grandRegister"),
                qDataViewReal.inquiryCount.sum().as("grandInquiry"),
                qDataViewReal.rfqCount.sum().as("grandRfq")))
        .from(qDataViewReal)
        .where(qDataViewReal.createTime.before(date))
        .fetchOne();
  }
}

package com.shoestp.mains.dao.DataView.real;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.real.DataViewReal;
import com.shoestp.mains.entitys.DataView.real.QDataViewReal;
import com.shoestp.mains.entitys.QUser;
import com.shoestp.mains.repositorys.DataView.real.RealRepository;
import com.shoestp.mains.views.DataView.real.RealView;
import com.shoestp.mains.views.DataView.user.DataViewUserView;

import org.springframework.stereotype.Repository;

/**
 * @description: 实时-数据层
 * @author: lingjian @Date: 2019/5/9 10:23
 */
@Repository
public class RealDao extends BaseDao<DataViewReal> {

  @Resource private RealRepository realRepository;

  /**
   * 根据时间间隔获取所有的记录
   *
   * @author: lingjian @Date: 2019/5/9 15:47
   * @return DataViewReal
   */
  public RealView findAllByCreateTimeBetween(Date start, Date end) {
    QDataViewReal qDataViewReal = QDataViewReal.dataViewReal;
    return getQuery()
        .select(
            Projections.bean(
                RealView.class,
                qDataViewReal.visitorCount.sum().as("visitorCount"),
                qDataViewReal.viewCount.sum().as("viewCount"),
                qDataViewReal.registerCount.sum().as("registerCount"),
                qDataViewReal.inquiryCount.sum().as("inquiryCount"),
                qDataViewReal.rfqCount.sum().as("rfqCount")))
        .from(qDataViewReal)
        .where(qDataViewReal.createTime.between(start, end))
        .fetchOne();
  }

  @Override
  public DataViewReal find(DataViewReal dataViewReal) {
    return null;
  }

  @Override
  public DataViewReal findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewReal dataViewReal) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewReal> list) {
    return 0;
  }

  @Override
  public int remove(DataViewReal dataViewReal) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

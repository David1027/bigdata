package com.shoestp.mains.dao.DataView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.DataViewReal;
import com.shoestp.mains.entitys.DataView.QDataViewReal;
import com.shoestp.mains.entitys.QUser;
import com.shoestp.mains.repositorys.DataView.RealRepository;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;

import jdk.nashorn.internal.objects.annotations.Where;
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
   * @return List<DataViewReal>
   */
  public List<DataViewReal> findAllByCreateTimeBetween(Date start, Date end) {
    return realRepository.findAllByCreateTimeBetween(start, end);
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

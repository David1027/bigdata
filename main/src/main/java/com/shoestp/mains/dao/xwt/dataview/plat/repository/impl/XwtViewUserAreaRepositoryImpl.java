package com.shoestp.mains.dao.xwt.dataview.plat.repository.impl;

import java.util.Date;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewUserAreaRepository;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.QXwtViewUserArea;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUserArea;

import org.springframework.stereotype.Repository;

/**
 * (XwtDataViewUserArea)表数据访问层自定义接口实现类
 *
 * @author lingjian
 * @since 2020-01-02 14:01:11
 */
@Repository
public class XwtViewUserAreaRepositoryImpl extends BaseDao<XwtViewUserArea>
    implements XwtViewUserAreaRepository {

  private QXwtViewUserArea qXwtViewUserArea = QXwtViewUserArea.xwtViewUserArea;

  /**
   * 根据时间和地域分组获取用户地域表记录
   *
   * @author: lingjian @Date: 2020/1/3 16:30
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewUserArea> 用户地域表集合对象
   */
  @Override
  public List<XwtViewUserArea> listByCreateTimeBetween(Date start, Date end) {
    return getQuery()
        .select(
            Projections.bean(
                XwtViewUserArea.class,
                qXwtViewUserArea.area.as("area"),
                qXwtViewUserArea.areaCount.sum().as("areaCount")))
        .from(qXwtViewUserArea)
        .where(qXwtViewUserArea.createTime.between(start, end))
        .groupBy(qXwtViewUserArea.area)
        .fetchResults()
        .getResults();
  }
}

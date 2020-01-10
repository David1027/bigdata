package com.shoestp.mains.dao.xwt.dataview.plat.repository.impl;

import java.util.Date;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewUserRepository;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.QXwtViewUser;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUser;

import org.springframework.stereotype.Repository;

/**
 * (User)表数据访问层自定义接口实现类
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
@Repository
public class XwtViewUserRepositoryImpl extends BaseDao<XwtViewUser>
    implements XwtViewUserRepository {

  private QXwtViewUser qXwtViewUser = QXwtViewUser.xwtViewUser;

  /**
   * 根据时间获取用户表记录
   *
   * @author: lingjian @Date: 2020/1/3 15:52
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewUser 用户表对象
   */
  @Override
  public XwtViewUser getByCreateTimeBetween(Date start, Date end) {
    return getQuery()
        .select(
            Projections.bean(
                XwtViewUser.class,
                qXwtViewUser.visitorCount.sum().as("visitorCount"),
                qXwtViewUser.newVisitorCount.sum().as("newVisitorCount"),
                qXwtViewUser.oldVisitorCount.sum().as("oldVisitorCount"),
                qXwtViewUser.registerCount.sum().as("registerCount"),
                qXwtViewUser.generalCount.sum().as("generalCount"),
                qXwtViewUser.shoesCount.sum().as("shoesCount"),
                qXwtViewUser.materialCount.sum().as("materialCount"),
                qXwtViewUser.designerCount.sum().as("designerCount")))
        .from(qXwtViewUser)
        .where(qXwtViewUser.createTime.between(start, end))
        .fetchFirst();
  }
}

package com.shoestp.mains.dao.dataview.user;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.user.DataViewUser;
import com.shoestp.mains.entitys.dataview.user.QDataViewUser;
import com.shoestp.mains.repositorys.dataview.user.UserRepository;
import com.shoestp.mains.views.dataview.real.IndexGrand;
import com.shoestp.mains.views.dataview.user.DataViewUserView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: 用户概况-数据层
 * @author: lingjian @Date: 2019/5/13 14:21
 */
@Repository
public class UserDao extends BaseDao<DataViewUser> {

  @Resource private UserRepository userRepository;

  /**
   * 获取今天之前累计的询盘量，RFQ数，注册量
   *
   * @author: lingjian @Date: 2019/5/23 14:23
   * @param date
   * @return
   */
  public IndexGrand findByCreateTimeBefore(Date date) {
    QDataViewUser qDataViewUser = QDataViewUser.dataViewUser;
    return getQuery()
        .select(
            Projections.bean(
                IndexGrand.class,
                qDataViewUser.purchaseCount.sum().as("grandPurchase"),
                qDataViewUser.supplierCount.sum().as("grandSupplier")))
        .from(qDataViewUser)
        .where(qDataViewUser.createTime.before(date))
        .fetchOne();
  }

  /**
   * 根据时间获取用户表的数据总和,返回对象
   *
   * @author: lingjian @Date: 2019/5/16 15:06
   * @param start
   * @param end
   * @return
   */
  public DataViewUserView findUserByCreateTimeBetweenObject(Date start, Date end) {
    QDataViewUser qDataViewUser = QDataViewUser.dataViewUser;
    return getQuery()
        .select(
            Projections.bean(
                DataViewUserView.class,
                qDataViewUser.visitorCount.sum().as("visitorCount"),
                qDataViewUser.newVisitorCount.sum().as("newVisitorCount"),
                qDataViewUser.oldVisitorCount.sum().as("oldVisitorCount"),
                qDataViewUser.registerCount.sum().as("registerCount"),
                qDataViewUser.purchaseCount.sum().as("purchaseCount"),
                qDataViewUser.supplierCount.sum().as("supplierCount")))
        .from(qDataViewUser)
        .where(qDataViewUser.createTime.between(start, end))
        .fetchFirst();
  }
  /**
   * 根据时间获取用户表的数据总和，返回集合
   *
   * @author: lingjian @Date: 2019/5/13 14:50
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findUserByCreateTimeBetween(Date start, Date end) {
    QDataViewUser qDataViewUser = QDataViewUser.dataViewUser;
    return getQuery()
        .select(
            qDataViewUser.visitorCount.sum(),
            qDataViewUser.newVisitorCount.sum(),
            qDataViewUser.oldVisitorCount.sum(),
            qDataViewUser.registerCount.sum(),
            qDataViewUser.purchaseCount.sum(),
            qDataViewUser.supplierCount.sum())
        .from(qDataViewUser)
        .where(qDataViewUser.createTime.between(start, end))
        .fetchResults()
        .getResults();
  }

  public void save(DataViewUser user) {
    userRepository.save(user);
  }

  public Optional<DataViewUser> getLastUser() {
    return userRepository.findTopByOrderByCreateTimeDescIdDesc();
  }
}

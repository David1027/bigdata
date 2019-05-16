package com.shoestp.mains.dao.DataView.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.user.DataViewUser;
import com.shoestp.mains.entitys.DataView.user.QDataViewUser;
import com.shoestp.mains.repositorys.DataView.user.UserRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 用户概况-数据层
 * @author: lingjian
 * @Date: 2019/5/13 14:21
 */
@Repository
public class UserDao extends BaseDao<DataViewUser> {

  @Resource private UserRepository userRepository;

  /**
   * 根据时间获取用户表的数据总和
   * @author: lingjian
   * @Date: 2019/5/13 14:50
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findUserByCreateTimeBetween(Date start, Date end) {
    QDataViewUser qDataViewUser = QDataViewUser.dataViewUser;
    return getQuery().select(
            qDataViewUser.visitorCount.sum(),
            qDataViewUser.newVisitorCount.sum(),
            qDataViewUser.oldVisitorCount.sum(),
            qDataViewUser.registerCount.sum(),
            qDataViewUser.purchaseCount.sum(),
            qDataViewUser.supplierCount.sum()
    ).from(qDataViewUser).where(qDataViewUser.createTime.between(start,
            end)).fetchResults().getResults();

  }

  @Override
  public DataViewUser find(DataViewUser dataViewUser) {
    return null;
  }

  @Override
  public DataViewUser findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewUser dataViewUser) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewUser> list) {
    return 0;
  }

  @Override
  public int remove(DataViewUser dataViewUser) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

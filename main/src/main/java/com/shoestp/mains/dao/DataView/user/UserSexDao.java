package com.shoestp.mains.dao.DataView.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.user.DataViewUser;
import com.shoestp.mains.entitys.DataView.user.DataViewUserSex;
import com.shoestp.mains.entitys.DataView.user.QDataViewUser;
import com.shoestp.mains.entitys.DataView.user.QDataViewUserSex;
import com.shoestp.mains.repositorys.DataView.user.UserRepository;
import com.shoestp.mains.repositorys.DataView.user.UserSexRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 用户性别-数据层
 * @author: lingjian
 * @Date: 2019/5/13 15:59
 */
@Repository
public class UserSexDao extends BaseDao<DataViewUserSex> {

  @Resource private UserSexRepository userSexRepository;

  /**
   * 根据时间获取用户性别表的数据总和
   *
   * @author: lingjian @Date: 2019/5/13 15:59
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findUserSexByCreateTimeBetween(Date start, Date end) {
    QDataViewUserSex qDataViewUserSex = QDataViewUserSex.dataViewUserSex;
    return getQuery().select(
            qDataViewUserSex.manSexCount.sum(),
            qDataViewUserSex.womanSexCount.sum(),
            qDataViewUserSex.unknownSexCount.sum()
    ).from(qDataViewUserSex).where(qDataViewUserSex.createTime.between(start,
            end)).fetchResults().getResults();

  }

  @Override
  public DataViewUserSex find(DataViewUserSex dataViewUserSex) {
    return null;
  }

  @Override
  public DataViewUserSex findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewUserSex dataViewUserSex) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewUserSex> list) {
    return 0;
  }

  @Override
  public int remove(DataViewUserSex dataViewUserSex) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

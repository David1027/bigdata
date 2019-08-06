package com.shoestp.mains.dao.dataview.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.user.DataViewUserSex;
import com.shoestp.mains.entitys.dataview.user.QDataViewUserSex;
import com.shoestp.mains.entitys.metadata.enums.SexEnum;
import com.shoestp.mains.repositorys.dataview.user.UserSexRepository;

/**
 * @description: 用户性别-数据层
 * @author: lingjian @Date: 2019/5/13 15:59
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
    return getQuery()
        .select(qDataViewUserSex.sex.stringValue(), qDataViewUserSex.sexCount.sum())
        .from(qDataViewUserSex)
        .where(qDataViewUserSex.createTime.between(start, end))
        .groupBy(qDataViewUserSex.sex)
        .fetchResults()
        .getResults();
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

  public void save(DataViewUserSex userSex) {
    userSexRepository.save(userSex);
  }

  public Optional<DataViewUserSex> getLastUserSexByType(SexEnum sex) {
    return userSexRepository.findTopBySexOrderByCreateTimeDesc(sex);
  }
}

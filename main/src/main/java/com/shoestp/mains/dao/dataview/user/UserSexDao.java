package com.shoestp.mains.dao.dataview.user;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.user.DataViewUserSex;
import com.shoestp.mains.entitys.dataview.user.QDataViewUserSex;
import com.shoestp.mains.entitys.metadata.enums.SexEnum;
import com.shoestp.mains.repositorys.dataview.user.UserSexRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

  public void save(DataViewUserSex userSex) {
    userSexRepository.save(userSex);
  }

  public Optional<DataViewUserSex> getLastUserSexByType(SexEnum sex) {
    return userSexRepository.findTopBySexOrderByCreateTimeDesc(sex);
  }
}

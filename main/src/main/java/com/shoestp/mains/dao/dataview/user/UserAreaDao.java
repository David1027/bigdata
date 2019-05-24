package com.shoestp.mains.dao.dataview.user;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.user.DataViewUserArea;
import com.shoestp.mains.entitys.dataview.user.QDataViewUserArea;
import com.shoestp.mains.repositorys.dataview.user.UserAreaRepository;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @description: 用户性别-数据层
 * @author: lingjian @Date: 2019/5/13 15:59
 */
@Repository
public class UserAreaDao extends BaseDao<DataViewUserArea> {

  @Resource private UserAreaRepository userAreaRepository;

  /**
   * 根据时间获取用户地域表的数据总和
   *
   * @author: lingjian @Date: 2019/5/13 15:59
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findUserAreaByCreateTimeBetween(Date start, Date end) {
    QDataViewUserArea qDataViewUserArea = QDataViewUserArea.dataViewUserArea;
    return getQuery()
        .select(qDataViewUserArea.area, qDataViewUserArea.areaCount.sum())
        .from(qDataViewUserArea)
        .where(qDataViewUserArea.createTime.between(start, end))
        .groupBy(qDataViewUserArea.area)
        .fetchResults()
        .getResults();
  }

  @Override
  public DataViewUserArea find(DataViewUserArea dataViewUserArea) {
    return null;
  }

  @Override
  public DataViewUserArea findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewUserArea dataViewUserArea) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewUserArea> list) {
    return 0;
  }

  @Override
  public int remove(DataViewUserArea dataViewUserArea) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }

  public void save(DataViewUserArea area) {
    userAreaRepository.save(area);
  }
}

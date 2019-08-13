package com.shoestp.mains.dao.dataview.user;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.user.DataViewUserArea;
import com.shoestp.mains.entitys.dataview.user.QDataViewUserArea;
import com.shoestp.mains.repositorys.dataview.user.UserAreaRepository;
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
public class UserAreaDao extends BaseDao<DataViewUserArea> {

  @Resource private UserAreaRepository userAreaRepository;

  /**
   * 新增用户地域表记录
   *
   * @author: lingjian @Date: 2019/8/13 9:20
   * @param dataViewUserArea 用户地域对象
   */
  public void save(DataViewUserArea dataViewUserArea) {
    userAreaRepository.save(dataViewUserArea);
  }

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

  public Optional<DataViewUserArea> getLastUserArea(String area) {
    return userAreaRepository.findTopByAreaOrderByCreateTimeDesc(area);
  }
}

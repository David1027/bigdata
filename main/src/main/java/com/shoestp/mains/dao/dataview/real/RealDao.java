package com.shoestp.mains.dao.dataview.real;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import com.shoestp.mains.entitys.dataview.country.QDataViewCountry;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.repositorys.dataview.real.RealRepository;
import com.shoestp.mains.repositorys.dataview.realcountry.RealCountryRepository;
import com.shoestp.mains.views.dataview.real.IndexGrand;
import com.shoestp.mains.views.dataview.real.RealView;

import org.springframework.stereotype.Repository;

/**
 * @description: 国家-数据层
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@Repository
public class RealDao extends BaseDao<DataViewReal> {

  @Resource private RealRepository realRepository;

  /**
   * 新增DataViewReal记录
   *
   * @param dataViewReal real对象
   */
  public void saveReal(DataViewReal dataViewReal) {
    realRepository.save(dataViewReal);
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

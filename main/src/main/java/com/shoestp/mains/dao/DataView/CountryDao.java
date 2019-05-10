package com.shoestp.mains.dao.DataView;

import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.DataViewCountry;
import com.shoestp.mains.repositorys.DataView.CountryRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 国家-数据层
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@Repository
public class CountryDao extends BaseDao<DataViewCountry> {

  @Resource private CountryRepository countryRepository;

  @Override
  public DataViewCountry find(DataViewCountry dataViewCountry) {
    return null;
  }

  @Override
  public DataViewCountry findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewCountry dataViewCountry) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewCountry> list) {
    return 0;
  }

  @Override
  public int remove(DataViewCountry dataViewCountry) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

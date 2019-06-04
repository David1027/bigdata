package com.shoestp.mains.repositorys.dataview.realcountry;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.dataview.country.DataViewCountry;

/**
 * @description: 国家-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:40
 */
public interface RealCountryRepository
    extends PagingAndSortingRepository<DataViewCountry, Integer> {
  /**
   * 根据时间获取数据
   *
   * @param start
   * @param end
   * @return List
   */
  List findAllByCreateTimeBetween(Date start, Date end);

  public Optional<DataViewCountry> findTopByCountryNameOrderByCreateTimeDesc(String countryName);
}

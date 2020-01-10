package com.shoestp.mains.dao.xwt.dataview.plat.repository;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.xwt.dataview.plat.country.XwtViewCountry;

/**
 * (Country)表数据访问层自定义接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewCountryRepository {

  /**
   * 根据时间获取国家表数据
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewCountry> 国家表集合对象
   */
  List<XwtViewCountry> findAllByCountry(Date start, Date end);
}

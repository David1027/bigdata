package com.shoestp.mains.dao.xwt.dataview.repository.impl;

import java.util.Date;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.xwt.dataview.repository.XwtViewCountryRepository;
import com.shoestp.mains.entitys.xwt.dataview.country.QXwtViewCountry;
import com.shoestp.mains.entitys.xwt.dataview.country.XwtViewCountry;

import org.springframework.stereotype.Repository;

/**
 * (Country)表数据访问层自定义接口实现类
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
@Repository
public class XwtViewCountryRepositoryImpl extends BaseDao<XwtViewCountry>
    implements XwtViewCountryRepository {

  private QXwtViewCountry qXwtViewCountry = QXwtViewCountry.xwtViewCountry;

  /**
   * 根据时间获取国家表数据
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewCountry> 国家表集合对象
   */
  @Override
  public List<XwtViewCountry> findAllByCountry(Date start, Date end) {
    return getQuery()
        .select(
            Projections.bean(
                XwtViewCountry.class,
                qXwtViewCountry.countryName,
                qXwtViewCountry.countryEnglishName,
                qXwtViewCountry.countryImage,
                qXwtViewCountry.visitorCount.sum().as("visitorCount")))
        .from(qXwtViewCountry)
        .where(qXwtViewCountry.createTime.between(start, end))
        .groupBy(qXwtViewCountry.countryEnglishName)
        .orderBy(qXwtViewCountry.visitorCount.sum().desc())
        .fetchResults()
        .getResults();
  }
}

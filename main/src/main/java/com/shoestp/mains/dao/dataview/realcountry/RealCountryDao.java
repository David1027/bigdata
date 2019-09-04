package com.shoestp.mains.dao.dataview.realcountry;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import com.shoestp.mains.entitys.dataview.country.QDataViewCountry;
import com.shoestp.mains.entitys.metadata.QWebVisitInfo;
import com.shoestp.mains.repositorys.dataview.realcountry.RealCountryRepository;
import com.shoestp.mains.views.dataview.country.CountryView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: 国家-数据层
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@Repository
public class RealCountryDao extends BaseDao<DataViewCountry> {

  @Resource private RealCountryRepository realCountryRepository;

  /**
   * 新增国家表记录
   *
   * @author: lingjian @Date: 2019/8/19 9:51
   * @param dataViewCountry 国家表对象
   */
  public void save(DataViewCountry dataViewCountry) {
    realCountryRepository.save(dataViewCountry);
  }

  /**
   * 根据时间获取国家地区类数据
   *
   * @author: lingjian @Date: 2019/5/13 11:14
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewCountry>
   */
  public List<CountryView> findAllByCountry(Date start, Date end) {
    QDataViewCountry qDataViewCountry = QDataViewCountry.dataViewCountry;
    return getQuery()
        .select(
            Projections.bean(
                CountryView.class,
                qDataViewCountry.countryName,
                qDataViewCountry.countryEnglishName,
                qDataViewCountry.countryImage,
                qDataViewCountry.visitorCount.sum().as("visitorCount")))
        .from(qDataViewCountry)
        .where(qDataViewCountry.createTime.between(start, end))
        .groupBy(qDataViewCountry.countryEnglishName)
        .orderBy(qDataViewCountry.visitorCount.sum().desc())
        .fetchResults()
        .getResults();
  }

  public Optional<DataViewCountry> getLastCountryByCountryName(String name) {
    return realCountryRepository.findTopByCountryNameOrderByCreateTimeDesc(name);
  }
}

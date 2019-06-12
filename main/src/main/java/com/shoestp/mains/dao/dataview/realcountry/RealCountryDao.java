package com.shoestp.mains.dao.dataview.realcountry;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import com.shoestp.mains.entitys.dataview.country.QDataViewCountry;
import com.shoestp.mains.repositorys.dataview.realcountry.RealCountryRepository;
import com.shoestp.mains.views.dataview.real.IndexGrand;
import com.shoestp.mains.views.dataview.real.RealView;

/**
 * @description: 国家-数据层
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@Repository
public class RealCountryDao extends BaseDao<DataViewCountry> {

  @Resource private RealCountryRepository realCountryRepository;

  /**
   * 根据时间获取国家地区类数据
   *
   * @author: lingjian @Date: 2019/5/13 11:14
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewCountry>
   */
  public List<Tuple> findAllByCountry(Date start, Date end) {
    //    return realCountryRepository.findAllByCreateTimeBetween(start, end);
    QDataViewCountry qDataViewCountry = QDataViewCountry.dataViewCountry;
    return getQuery()
        .select(
            qDataViewCountry.countryName,
            qDataViewCountry.countryEnglishName,
            qDataViewCountry.countryImage,
            qDataViewCountry.visitorCount.sum(),
            qDataViewCountry.visitorCountPc.sum(),
            qDataViewCountry.visitorCountWap.sum())
        .from(qDataViewCountry)
        .where(qDataViewCountry.createTime.between(start, end))
        .groupBy(qDataViewCountry.countryEnglishName)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间间隔获取所有的记录
   *
   * @author: lingjian @Date: 2019/5/22 9:11
   * @param start
   * @param end
   * @return
   */
  public RealView findAllByCreateTimeBetween(Date start, Date end) {
    QDataViewCountry qDataViewCountry = QDataViewCountry.dataViewCountry;
    return getQuery()
        .select(
            Projections.bean(
                RealView.class,
                qDataViewCountry.visitorCount.sum().as("visitorCount"),
                qDataViewCountry.pageViewsCount.sum().as("viewCount"),
                qDataViewCountry.registerCount.sum().as("registerCount"),
                qDataViewCountry.inquiryCount.sum().as("inquiryCount"),
                qDataViewCountry.rfqCount.sum().as("rfqCount")))
        .from(qDataViewCountry)
        .where(qDataViewCountry.createTime.between(start, end))
        .fetchOne();
  }

  /**
   * 获取今天之前累计的询盘量，RFQ数，注册量
   *
   * @author: lingjian @Date: 2019/5/23 14:14
   * @param date
   * @return
   */
  public IndexGrand findByCreateTimeBefore(Date date) {
    QDataViewCountry qDataViewCountry = QDataViewCountry.dataViewCountry;
    return getQuery()
        .select(
            Projections.bean(
                IndexGrand.class,
                qDataViewCountry.registerCount.sum().as("grandRegister"),
                qDataViewCountry.inquiryCount.sum().as("grandInquiry"),
                qDataViewCountry.rfqCount.sum().as("grandRfq")))
        .from(qDataViewCountry)
        .where(qDataViewCountry.createTime.before(date))
        .fetchOne();
  }

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

  public void save(DataViewCountry country) {
    realCountryRepository.save(country);
  }

  public Optional<DataViewCountry> getLastCountryByCountryName(String name) {
    return realCountryRepository.findTopByCountryNameOrderByCreateTimeDesc(name);
  }
}

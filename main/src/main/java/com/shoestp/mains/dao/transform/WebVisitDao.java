package com.shoestp.mains.dao.transform;

import java.util.Date;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.QWebVisitInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.EquipmentPlatform;

import org.springframework.stereotype.Repository;

/**
 * @description: 源数据层 - 数据访问层
 * @author: lingjian
 * @create: 2019/8/6 13:58
 */
@Repository
public class WebVisitDao extends BaseDao<WebVisitInfo> {

  /**
   * 根据时间获取国家列表
   *
   * @param start
   * @param end
   * @return
   */
  public List<PltCountry> getCountryList(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    List<PltCountry> results =
        getQuery()
            .select(qWebVisitInfo.location)
            .where(qWebVisitInfo.createTime.between(start, end))
            .groupBy(qWebVisitInfo.location)
            .from(qWebVisitInfo)
            .fetchResults()
            .getResults();
    return results;
  }

  /**
   * 根据时间获取记录列表
   *
   * @param start
   * @param end
   * @return
   */
  public List<WebVisitInfo> getWebVisitInfo(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.createTime.between(start, end))
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间获取浏览量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countWebVisitInfo(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return (int)
        getQuery()
            .select(qWebVisitInfo)
            .where(qWebVisitInfo.createTime.between(start, end))
            .from(qWebVisitInfo)
            .fetchCount();
  }

  /**
   * 根据设备来源，时间获取访客数
   * @param equipmentPlatform 设备来源
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countVisitor(
      EquipmentPlatform equipmentPlatform, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    JPAQuery<WebVisitInfo> query =
        getQuery()
            .select(qWebVisitInfo)
            .where(qWebVisitInfo.createTime.between(start, end));
    if (equipmentPlatform != null) {
      query.where(qWebVisitInfo.equipmentPlatform.eq(equipmentPlatform));
    }
    return (int) query.groupBy(qWebVisitInfo.ip).from(qWebVisitInfo).fetchCount();
  }

  @Override
  public WebVisitInfo find(WebVisitInfo webVisitInfo) {
    return null;
  }

  @Override
  public WebVisitInfo findById(Integer id) {
    return null;
  }

  @Override
  public int update(WebVisitInfo webVisitInfo) {
    return 0;
  }

  @Override
  public int updateByList(List<WebVisitInfo> list) {
    return 0;
  }

  @Override
  public int remove(WebVisitInfo webVisitInfo) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

package com.shoestp.mains.dao.transform;

import java.util.Date;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QWebVisitInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;

import org.springframework.stereotype.Repository;

/**
 * @description: 源数据层 - 数据访问层
 * @author: lingjian
 * @create: 2019/8/6 13:58
 */
@Repository
public class WebVisitDao extends BaseDao<WebVisitInfo> {

  /**
   * 根据来源设备，时间获取记录列表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<WebVisitInfo> 源数据集合对象
   */
  public List<WebVisitInfo> getWebVisitInfo(DeviceTypeEnum deviceTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.equipmentPlatform.eq(deviceTypeEnum))
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
   *
   * @param deviceTypeEnum 设备来源
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countVisitor(DeviceTypeEnum deviceTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    JPAQuery<WebVisitInfo> query =
        getQuery().select(qWebVisitInfo).where(qWebVisitInfo.createTime.between(start, end));
    if (deviceTypeEnum != null) {
      query.where(qWebVisitInfo.equipmentPlatform.eq(deviceTypeEnum));
    }
    return (int) query.groupBy(qWebVisitInfo.ip).from(qWebVisitInfo).fetchCount();
  }

  /**
   * 根据页面类型，时间获取浏览量
   *
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countPageTypeView(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return (int)
        getQuery()
            .select(qWebVisitInfo)
            .where(qWebVisitInfo.pageType.eq(accessTypeEnum))
            .where(qWebVisitInfo.createTime.between(start, end))
            .from(qWebVisitInfo)
            .fetchCount();
  }

  /**
   * 根据页面类型，时间获取访客数
   *
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countPageTypeVisitor(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return (int)
        getQuery()
            .select(qWebVisitInfo)
            .where(qWebVisitInfo.pageType.eq(accessTypeEnum))
            .where(qWebVisitInfo.createTime.between(start, end))
            .groupBy(qWebVisitInfo.ip)
            .from(qWebVisitInfo)
            .fetchCount();
  }

  /**
   * 根据页面类型，时间获取点击量
   *
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer getPageTypeClickCount(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    JPAQuery<Integer> query = getQuery().select(qWebVisitInfo.clickCount.sum());
    if (accessTypeEnum != null) {
      query.where(qWebVisitInfo.pageType.eq(accessTypeEnum));
    }
    Integer result =
        query.where(qWebVisitInfo.createTime.between(start, end)).from(qWebVisitInfo).fetchFirst();
    return result == null ? 0 : result;
  }

  /**
   * 根据页面类型，时间获取访问次数（会话总数）
   *
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countPageTypeSession(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return (int)
        getQuery()
            .select(qWebVisitInfo)
            .where(qWebVisitInfo.pageType.eq(accessTypeEnum))
            .where(qWebVisitInfo.createTime.between(start, end))
            .groupBy(qWebVisitInfo.session)
            .from(qWebVisitInfo)
            .fetchCount();
  }

  /**
   * 根据页面类型，时间获取会话总数
   *
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return List<String>
   */
  public List<String> getPageTypeSession(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo.session)
        .where(qWebVisitInfo.pageType.eq(accessTypeEnum))
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.session)
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据会话id，时间获取记录
   *
   * @param session 会话id
   * @param start 开始时间
   * @param end 结束时间
   * @return List<WebVisitInfo>
   */
  public List<WebVisitInfo> countPageTypeSession(String session, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.session.eq(session))
        .where(qWebVisitInfo.createTime.between(start, end))
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据页面类型，时间获取页面停留时长
   *
   * @author: lingjian @Date: 2019/8/8 13:45
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return Long
   */
  public Long getPageTypeTimeOnPage(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    Long result =
        getQuery()
            .select(qWebVisitInfo.timeOnPage.sum())
            .where(qWebVisitInfo.pageType.eq(accessTypeEnum))
            .where(qWebVisitInfo.createTime.between(start, end))
            .from(qWebVisitInfo)
            .fetchFirst();
    return result == null ? 0 : result;
  }

  public List<WebVisitInfo> getWebVisitUserId(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.userId)
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
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

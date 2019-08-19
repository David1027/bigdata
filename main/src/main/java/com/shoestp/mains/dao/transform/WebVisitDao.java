package com.shoestp.mains.dao.transform;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QWebVisitInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

  /**
   * 根据时间分页获取源数据表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param offset 开始条数
   * @param limit 显示条数
   * @return List<WebVisitInfo> 源数据表集合对象
   */
  public List<WebVisitInfo> getWebVisitUserId(Date start, Date end, Long offset, int limit) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.userId)
        .from(qWebVisitInfo)
        .limit(limit)
        .offset(offset)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据地域，时间获取源数据表记录
   *
   * @author: lingjian @Date: 2019/8/13 9:19
   * @param start 开始时间
   * @param end 结束时间
   * @return List<WebVisitInfo>
   */
  public List<WebVisitInfo> getWebVisitUserArea(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.location)
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据地域，时间获取访客数
   *
   * @author: lingjian @Date: 2019/8/13 9:14
   * @param areaId 地域id
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countWebVisitUserArea(Integer areaId, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return (int)
        getQuery()
            .select(qWebVisitInfo)
            .where(qWebVisitInfo.location.id.eq(areaId))
            .where(qWebVisitInfo.createTime.between(start, end))
            .groupBy(qWebVisitInfo.ip)
            .from(qWebVisitInfo)
            .fetchCount();
  }

  /**
   * 获取start时间之前所有去重后的ip集合
   *
   * @author: lingjian @Date: 2019/8/19 14:52
   * @param start 时间
   * @return List<String>
   */
  public List<String> listWebVisitIp(Date start) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo.ip)
        .where(qWebVisitInfo.createTime.before(start))
        .groupBy(qWebVisitInfo.ip)
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
  }

  /**
   * 获取开始时间和结束时间之间的去重后ip的集合
   *
   * @author: lingjian @Date: 2019/8/19 15:19
   * @param start 开始时间
   * @param end 结束时间
   * @return List<String>
   */
  public List<String> listWebVisitIp(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo.ip)
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.ip)
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
  }
}

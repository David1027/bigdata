package com.shoestp.mains.dao.transform;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QWebVisitInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.views.dataview.real.RealVisitorPageView;
import com.shoestp.mains.views.dataview.real.RealVisitorView;
import com.shoestp.mains.views.dataview.real.VisitorView;
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
   * 根据时间分组获取ip的集合数据
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<String>
   */
  public List<String> listIpGroup(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    JPAQuery<String> query =
        getQuery().select(qWebVisitInfo.ip).where(qWebVisitInfo.createTime.between(start, end));
    if (accessTypeEnum != null) {
      query.where(qWebVisitInfo.pageType.eq(accessTypeEnum));
    }
    return query.groupBy(qWebVisitInfo.ip).from(qWebVisitInfo).fetchResults().getResults();
  }

  /**
   * 根据时间获取ip和设备来源的数据记录
   *
   * @param start 开始时间
   * @param end 结束世家
   * @return List<VisitorView> ip和设备类型的dto类
   */
  public List<VisitorView> listWebVisitInfoByIp(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;

    JPAQuery<VisitorView> query =
        getQuery()
            .select(
                Projections.bean(
                    VisitorView.class,
                    qWebVisitInfo.ip.as("ip"),
                    qWebVisitInfo.equipmentPlatform.as("deviceTypeEnum")))
            .where(qWebVisitInfo.createTime.between(start, end));
    return query.groupBy(qWebVisitInfo.ip).from(qWebVisitInfo).fetchResults().getResults();
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
  public List<WebVisitInfo> countPageTypeVisitor(
      AccessTypeEnum accessTypeEnum, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.pageType.eq(accessTypeEnum))
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.ip)
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
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
  public List<String> countWebVisitUserArea(Integer areaId, Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo.ip)
        .where(qWebVisitInfo.location.id.eq(areaId))
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.ip)
        .from(qWebVisitInfo)
        .fetchResults()
        .getResults();
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
   * @author: lingjian @Date: 2019/8/19 15:31
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

  /**
   * 根据流量来源类型匹配源数据表的记录
   *
   * @author: lingjian @Date: 2019/8/20 14:04
   * @param sourceType 流量来源类型
   * @param qWebVisitInfo 源数据表的Q类型
   * @param query 查询语句
   */
  private void matchFlowSourceType(
      SourceTypeEnum sourceType, QWebVisitInfo qWebVisitInfo, JPAQuery<RealVisitorView> query) {
    if (sourceType != null) {
      if (sourceType.equals(SourceTypeEnum.GOOGLE)) {
        query.where(qWebVisitInfo.referer.like("%www.google%"));
      } else if (sourceType.equals(SourceTypeEnum.BAIDU)) {
        query.where(qWebVisitInfo.referer.like("%www.baidu%"));
      } else if (sourceType.equals(SourceTypeEnum.INTERVIEW)) {
        query.where(qWebVisitInfo.referer.isEmpty());
      } else {
        query.where(qWebVisitInfo.referer.notLike("%www.google%"));
        query.where(qWebVisitInfo.referer.notLike("%www.baidu%"));
        query.where(qWebVisitInfo.referer.isNotEmpty());
      }
    }
  }

  /**
   * 根据搜索条件，时间分页获取源数据表记录的方法
   *
   * @author: lingjian @Date: 2019/8/20 14:02
   * @param start 开始时间
   * @param end 结束时间
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 国家
   * @return List<RealVisitorView> 实时访客前端展示类集合对象
   */
  private JPAQuery<RealVisitorView> getRealVisitorViewJPAQuery(
      Date start,
      Date end,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    JPAQuery<RealVisitorView> query =
        getQuery()
            .select(
                Projections.bean(
                    RealVisitorView.class,
                    qWebVisitInfo.id.as("id"),
                    qWebVisitInfo.url.as("url"),
                    qWebVisitInfo.referer.as("referer"),
                    qWebVisitInfo.location.id.as("countryId"),
                    qWebVisitInfo.location.name.as("countryName"),
                    qWebVisitInfo.userId.type.as("type"),
                    qWebVisitInfo.createTime.as("createTime")))
            .where(qWebVisitInfo.createTime.between(start, end));
    // 访客类型
    if (visitType == 1) {
      query.where(qWebVisitInfo.userId.type.ne(RegisterTypeEnum.VISITOR));
    }
    // 被访问页面
    if (urlPage != null && !"".equals(urlPage)) {
      query.where(qWebVisitInfo.url.eq(urlPage));
    }
    // 访客位置
    if (country != null) {
      query.where(qWebVisitInfo.location.id.eq(country));
    }
    // 流量来源类型
    matchFlowSourceType(sourceType, qWebVisitInfo, query);

    query.from(qWebVisitInfo).orderBy(qWebVisitInfo.createTime.desc());
    return query;
  }

  /**
   * 根据搜索条件，时间分页获取源数据表记录
   *
   * @author: lingjian @Date: 2019/8/20 14:01
   * @param start 开始时间
   * @param end 结束时间
   * @param page 开始条数
   * @param limit 显示条数
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 国家
   * @return List<RealVisitorView> 实时访客前端展示类集合对象
   */
  public List<RealVisitorView> listWebVisit(
      Date start,
      Date end,
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country) {
    JPAQuery<RealVisitorView> query =
        getRealVisitorViewJPAQuery(start, end, visitType, sourceType, urlPage, country);
    return query.offset(page).limit(limit).fetchResults().getResults();
  }

  /**
   * 根据搜索条件，时间分页获取源数据表记录的总条数
   *
   * @author: lingjian @Date: 2019/8/20 14:01
   * @param start 开始时间
   * @param end 结束时间
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 国家
   * @return List<RealVisitorView> 实时访客前端展示类集合对象
   */
  public Long countWebVisit(
      Date start,
      Date end,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country) {
    JPAQuery<RealVisitorView> query =
        getRealVisitorViewJPAQuery(start, end, visitType, sourceType, urlPage, country);
    return query.fetchCount();
  }

  /**
   * 根据时间分组获取url集合列表
   *
   * @author: lingjian @Date: 2019/8/29 14:46
   * @param start 开始时间
   * @param end 结束时间
   * @return List<RealVisitorPageView>
   */
  public List<RealVisitorPageView> getWebVisitUrl(
      Date start, Date end, Integer page, Integer limit) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(
            Projections.bean(
                RealVisitorPageView.class,
                qWebVisitInfo.url.as("url"),
                qWebVisitInfo.ip.countDistinct().as("visitorCount"),
                qWebVisitInfo.count().as("viewCount"),
                qWebVisitInfo.timeOnPage.avg().as("averageTime")))
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.url)
        .from(qWebVisitInfo)
        .orderBy(qWebVisitInfo.id.count().desc())
        .orderBy(qWebVisitInfo.ip.countDistinct().desc())
        .offset(page)
        .limit(limit)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间分组获取url集合总数量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return Long
   */
  public Long countWebVisitUrl(Date start, Date end) {
    QWebVisitInfo qWebVisitInfo = QWebVisitInfo.webVisitInfo;
    return getQuery()
        .select(qWebVisitInfo)
        .where(qWebVisitInfo.createTime.between(start, end))
        .groupBy(qWebVisitInfo.url)
        .from(qWebVisitInfo)
        .fetchCount();
  }
}

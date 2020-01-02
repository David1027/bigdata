package com.shoestp.mains.dao.xwt.metadata.repository.impl;

import java.util.Date;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.xwt.metadata.repository.XwtMetaAccessLogRepository;
import com.shoestp.mains.entitys.xwt.metadata.QXwtMetaAccessLog;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.enums.xwt.OMemberRoleEnum;
import com.shoestp.mains.views.transform.DeviceVisitorVO;

import org.springframework.stereotype.Repository;

/**
 * @description: 访问日志数据访问层自定义接口实现类
 * @author: lingjian
 * @create: 2019/12/27 10:32
 */
@Repository
public class XwtMetaAccessLogRepositoryImpl extends BaseDao<XwtMetaAccessLog>
    implements XwtMetaAccessLogRepository {

  private QXwtMetaAccessLog qAccessLog = QXwtMetaAccessLog.xwtMetaAccessLog;

  /**
   * 根据时间分组获取日志信息表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DeviceVisitorVO> 设备和访客的VO集合对象
   */
  @Override
  public List<DeviceVisitorVO> listByCreateTimeGroupByMemberId(Date start, Date end) {
    return getQuery()
        .select(
            Projections.bean(
                DeviceVisitorVO.class,
                qAccessLog.memberInfoId.as("memberId"),
                qAccessLog.deviceType.as("deviceType")))
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .groupBy(qAccessLog.memberInfoId)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间获取用户信息表id的集合记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<Integer> 用户信息表id的集合记录
   */
  @Override
  public List<Integer> listGroupByMemberId(Date start, Date end) {
    return getQuery()
        .select(qAccessLog.memberInfoId)
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .groupBy(qAccessLog.memberInfoId)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间，页面类型和会话id分组获取日志信息的次数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param accessType 页面类型
   * @return Long 会话次数
   */
  @Override
  public Long countByCreateTimeAndAccessTypeGroupBySsId(
      Date start, Date end, OAccessTypeEnum accessType) {
    return getQuery()
        .select(qAccessLog)
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .where(qAccessLog.accessType.eq(accessType))
        .groupBy(qAccessLog.ssId)
        .fetchCount();
  }

  /**
   * 根据时间，页面类型和会话id分组,且分组条数只有一条的日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param accessType 页面类型
   * @return List 日志信息集合对象
   */
  @Override
  public List countByCreateTimeAndAccessTypeGroupBySsIdHavingSsCount(
      Date start, Date end, OAccessTypeEnum accessType) {
    JPAQuery query =
        getQuery()
            .select(qAccessLog)
            .from(qAccessLog)
            .where(qAccessLog.createTime.after(start))
            .where(qAccessLog.createTime.before(end))
            .where(qAccessLog.accessType.eq(accessType))
            .groupBy(qAccessLog.ssId)
            .having(qAccessLog.count().eq(1L));
    return query.fetch();
  }

  /**
   * 根据时间和页面类型获取平均停留时长
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param accessType 页面类型
   * @return Double 平均停留时长
   */
  @Override
  public Double getTimeOnPageAvg(Date start, Date end, OAccessTypeEnum accessType) {
    Double result =
        getQuery()
            .select(qAccessLog.timeOnPage.avg())
            .from(qAccessLog)
            .where(qAccessLog.createTime.between(start, end))
            .where(qAccessLog.accessType.eq(accessType))
            .where(qAccessLog.timeOnPage.ne(0L))
            .fetchOne();
    return result == null ? 0.0 : result;
  }

  /**
   * 根据时间和用户角色获取日志信息记录的数量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param memberRole 用户角色
   * @return Long 访客数
   */
  @Override
  public Long countByCreateTimeAndUserRole(Date start, Date end, OMemberRoleEnum memberRole) {
    return getQuery()
        .select(qAccessLog)
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .where(qAccessLog.xwtMetaMemberInfo.userRole.eq(memberRole))
        .groupBy(qAccessLog.memberInfoId)
        .fetchCount();
  }

  /**
   * 根据时间分组获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  @Override
  public List<XwtMetaAccessLog> listByCreateTimeGroupByProvince(Date start, Date end) {
    return getQuery()
        .select(qAccessLog)
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .groupBy(qAccessLog.provinceId)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间和国家分组获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  @Override
  public List<XwtMetaAccessLog> listByCreateTimeGroupByCountry(Date start, Date end) {
    return getQuery()
        .select(qAccessLog)
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .groupBy(qAccessLog.countryId)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间地域获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param privinceId 地域id
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  @Override
  public List<XwtMetaAccessLog> listByCreateTimeAndProvince(
      Date start, Date end, Integer privinceId) {
    return getQuery()
        .select(qAccessLog)
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .where(qAccessLog.provinceId.eq(privinceId))
        .groupBy(qAccessLog.memberInfoId)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间国家获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param countryId 地域id
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  @Override
  public List<XwtMetaAccessLog> listByCreateTimeAndCountry(
      Date start, Date end, Integer countryId) {
    return getQuery()
        .select(qAccessLog)
        .from(qAccessLog)
        .where(qAccessLog.createTime.between(start, end))
        .where(qAccessLog.countryId.eq(countryId))
        .groupBy(qAccessLog.memberInfoId)
        .fetchResults()
        .getResults();
  }
}

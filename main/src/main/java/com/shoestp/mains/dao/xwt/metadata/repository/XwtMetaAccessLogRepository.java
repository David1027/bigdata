package com.shoestp.mains.dao.xwt.metadata.repository;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.controllers.xwt.dataview.plat.vo.real.XwtRealVisitorPageVO;
import com.shoestp.mains.controllers.xwt.dataview.plat.vo.real.XwtRealVisitorVO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.enums.xwt.OMemberRoleEnum;
import com.shoestp.mains.views.transform.DeviceVisitorVO;

/**
 * @description: 访问日志数据访问层自定义接口
 * @author: lingjian
 * @create: 2019/12/27 10:30
 */
public interface XwtMetaAccessLogRepository {

  /**
   * 根据时间分组获取日志信息表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DeviceVisitorVO> 设备和访客的VO集合对象
   */
  List<DeviceVisitorVO> listByCreateTimeGroupByMemberId(Date start, Date end);

  /**
   * 根据时间获取用户信息表id的集合记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<Integer> 用户信息表id的集合记录
   */
  List<Integer> listGroupByMemberId(Date start, Date end);

  /**
   * 根据时间，页面类型和会话id分组获取日志信息的次数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param accessType 页面类型
   * @return Long 会话次数
   */
  Long countByCreateTimeAndAccessTypeGroupBySsId(Date start, Date end, OAccessTypeEnum accessType);

  /**
   * 根据时间，页面类型和会话id分组,且分组条数只有一条的日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param accessType 页面类型
   * @return Long 会话次数
   */
  List countByCreateTimeAndAccessTypeGroupBySsIdHavingSsCount(
      Date start, Date end, OAccessTypeEnum accessType);

  /**
   * 根据时间和页面类型获取平均停留时长
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param accessType 页面类型
   * @return Double 平均停留时长
   */
  Double getTimeOnPageAvg(Date start, Date end, OAccessTypeEnum accessType);

  /**
   * 根据时间和用户角色获取日志信息记录的数量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param memberRole 用户角色
   * @return Long 访客数
   */
  Long countByCreateTimeAndUserRole(Date start, Date end, OMemberRoleEnum memberRole);

  /**
   * 根据时间和地域分组获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  List<XwtMetaAccessLog> listByCreateTimeGroupByProvince(Date start, Date end);

  /**
   * 根据时间和国家分组获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  List<XwtMetaAccessLog> listByCreateTimeGroupByCountry(Date start, Date end);

  /**
   * 根据时间地域获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param privinceId 地域id
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  List<XwtMetaAccessLog> listByCreateTimeAndProvince(Date start, Date end, Integer privinceId);

  /**
   * 根据时间国家获取日志信息记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param countryId 地域id
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  List<XwtMetaAccessLog> listByCreateTimeAndCountry(Date start, Date end, Integer countryId);

  /**
   * 根据搜索条件，时间分页获取日志信息记录
   *
   * @author: lingjian @Date: 2020/1/3 13:56
   * @param start 开始时间
   * @param end 结束时间
   * @param page 开始条数
   * @param limit 显示条数
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 国家
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  List<XwtRealVisitorVO> listAccessLogs(
      Date start,
      Date end,
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country);

  /**
   * 根据搜索条件，时间分页获取日志信息记录的数量
   *
   * @author: lingjian @Date: 2020/1/3 13:56
   * @param start 开始时间
   * @param end 结束时间
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 国家
   * @return Long 日志信息记录数
   */
  Long countAccessLogs(
      Date start,
      Date end,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country);

  /**
   * 根据时间分组获取url日志信息记录
   *
   * @author: lingjian @Date: 2020/1/3 14:16
   * @param start 开始时间
   * @param end 结束时间
   * @param page 开始条数
   * @param limit 显示条数
   * @return List<XwtRealVisitorPageVO> 常访问页面前端展示类集合对象
   */
  List<XwtRealVisitorPageVO> listAccessLogsUrl(Date start, Date end, Integer page, Integer limit);

  /**
   * 根据时间分组获取url日志信息记录的总条数
   *
   * @author: lingjian @Date: 2020/1/3 14:22
   * @param start 开始时间
   * @param end 结束时间
   * @return Long 日志信息记录数
   */
  Long countAccessLogsUrl(Date start, Date end);
}

package com.shoestp.mains.dao.xwt.dataview.repository;

import java.util.Date;

import com.shoestp.mains.entitys.xwt.dataview.flow.XwtViewFlowPage;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;

/**
 * (FlowPage)表数据访问层自定义接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewFlowPageRepository {

  /**
   * 根据时间获取跳失率
   *
   * @author: lingjian @Date: 2020/1/3 11:15
   * @param start 开始时间
   * @param end 结束时间
   * @return Double 平均跳失率
   */
  Double findByCreateTimeBetween(Date start, Date end);

  /**
   * 根据页面类型和时间获取访客数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param access 页面类型
   * @return Long 访客数
   */
  Long countByCreateTimeAndAccess(Date start, Date end, OAccessTypeEnum access);

  /**
   * 根据页面类型和时间获取流量页面对象
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param access 页面类型
   * @return XwtViewFlowPage 流量页面对象
   */
  XwtViewFlowPage listByCreateTimeAndAccess(Date start, Date end, OAccessTypeEnum access);
}

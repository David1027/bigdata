package com.shoestp.mains.dao.xwt.dataview.plat.repository;

import java.util.Date;

import com.shoestp.mains.entitys.xwt.dataview.plat.real.XwtViewReal;

/**
 * (Real)表数据访问层自定义接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewRealRepository {

  /**
   * 根据时间获取实时表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewReal 实时表对象
   */
  XwtViewReal findByCreateTimeBetween(Date start, Date end);
}

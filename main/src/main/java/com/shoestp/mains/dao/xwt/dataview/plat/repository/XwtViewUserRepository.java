package com.shoestp.mains.dao.xwt.dataview.plat.repository;

import java.util.Date;

import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUser;

/**
 * (User)表数据访问层自定义接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewUserRepository {

  /**
   * 根据时间获取用户表记录
   *
   * @author: lingjian @Date: 2020/1/3 15:53
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewUser 用户表对象
   */
  XwtViewUser getByCreateTimeBetween(Date start, Date end);
}

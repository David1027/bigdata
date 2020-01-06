package com.shoestp.mains.dao.xwt.dataview.repository;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.xwt.dataview.user.XwtViewUserArea;

/**
 * (XwtDataViewUserArea)表数据访问层自定义接口
 *
 * @author lingjian
 * @since 2020-01-02 14:01:11
 */
public interface XwtViewUserAreaRepository {

  /**
   * 根据时间和地域分组获取用户地域表记录
   *
   * @author: lingjian @Date: 2020/1/3 16:30
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewUserArea> 用户地域表集合对象
   */
  List<XwtViewUserArea> listByCreateTimeBetween(Date start, Date end);
}

package com.shoestp.mains.service.xwt.dataview.plat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUser;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUserArea;

/**
 * @description: 用户-服务层接口
 * @author: lingjian
 * @create: 2020/1/3 15:48
 */
public interface XwtViewUserService {

  /**
   * 获取用户概况
   *
   * @author: lingjian @Date: 2020/1/3 16:04
   * @param date 时间
   * @param num 天数
   * @return XwtViewUser 用户表对象
   */
  XwtViewUser getUserOverview(Date date, Integer num);

  /**
   * 获取用户概况中的时段分布(小时)
   *
   * @author: lingjian @Date: 2020/1/3 16:22
   * @param date 时间
   * @return Map<String, Map>
   */
  Map<String, Map> getUserTimeByHour(Date date);

  /**
   * 根据时间获取用户概况中的时段分布(天)
   *
   * @author: lingjian @Date: 2020/1/3 16:22
   * @param date 时间
   * @param num 天数
   * @return Map<String, Map>
   */
  Map<String, Map> getUserTimeByDay(Date date, Integer num);

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2020/1/3 16:27
   * @param date 时间
   * @param num 天数
   * @return List<XwtViewUserArea> 用户地域表
   */
  List<XwtViewUserArea> getUserArea(Date date, Integer num);
}

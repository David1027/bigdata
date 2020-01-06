package com.shoestp.mains.service.xwt.dataview.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.constant.dataview.Contants;
import com.shoestp.mains.dao.dataview.user.UserAreaDao;
import com.shoestp.mains.dao.dataview.user.UserDao;
import com.shoestp.mains.dao.dataview.user.UserSexDao;
import com.shoestp.mains.dao.xwt.dataview.dao.XwtViewUserAreaDAO;
import com.shoestp.mains.dao.xwt.dataview.dao.XwtViewUserDAO;
import com.shoestp.mains.entitys.xwt.dataview.user.XwtViewUser;
import com.shoestp.mains.entitys.xwt.dataview.user.XwtViewUserArea;
import com.shoestp.mains.service.dataview.UserService;
import com.shoestp.mains.service.xwt.dataview.XwtViewUserService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.dataview.user.DataViewUserAreaView;
import com.shoestp.mains.views.dataview.user.DataViewUserSexView;
import com.shoestp.mains.views.dataview.user.DataViewUserView;

import org.springframework.stereotype.Service;

/**
 * @description: 用户-服务层实现类
 * @author: lingjian
 * @create: 2020/1/3 15:48
 */
@Service
public class XwtViewUserServiceImpl implements XwtViewUserService {

  @Resource private UserAreaDao userAreaDao;

  @Resource private XwtViewUserDAO userDAO;
  @Resource private XwtViewUserAreaDAO userAreaDAO;

  /**
   * 判断是否为空处理
   *
   * @author: lingjian @Date: 2020/1/3 15:57
   * @param user 用户表对象
   * @return XwtViewUser 用户表对象
   */
  private XwtViewUser isNullTo(XwtViewUser user) {
    if (null == user.getVisitorCount()) {
      user.setVisitorCount(0L);
      user.setNewVisitorCount(0L);
      user.setOldVisitorCount(0L);
      user.setRegisterCount(0L);
      user.setGeneralCount(0L);
      user.setShoesCount(0L);
      user.setMaterialCount(0L);
      user.setDesignerCount(0L);
    }
    return user;
  }

  /**
   * 根据时间，日期类型获取用户概况
   *
   * @author: lingjian @Date: 2020/1/3 15:57
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return XwtViewUser 用户表对象
   */
  private XwtViewUser getUserOverviewByDate(Date startDate, Date endDate, int start, int end) {
    return isNullTo(
        userDAO.getByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, start),
            DateTimeUtil.getTimesOfDay(endDate, end)));
  }

  /**
   * 根据时间，日期类型获取用户概况
   *
   * @author: lingjian @Date: 2020/1/3 16:20
   * @param date 时间
   * @return XwtViewUser 用户表对象
   */
  @Override
  public XwtViewUser getUserOverview(Date date, Integer num) {
    if (num != null) {
      return getUserOverviewByDate(DateTimeUtil.getDayFromNum(date, num), date, 0, 24);
    } else {
      return getUserOverviewByDate(date, date, 0, 24);
    }
  }

  /**
   * 获取24个小时中每个小时的访客数
   *
   * @author: lingjian @Date: 2020/1/3 16:20
   * @param date 时间
   * @return long[]
   */
  private long[] getEveryHour(Date date) {
    long[] arr = new long[24];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = getUserOverviewByDate(date, date, i, i + 1).getVisitorCount();
    }
    return arr;
  }

  /**
   * 获取用户概况中的时段分布(一天24小时)
   *
   * @author: lingjian @Date: 2020/1/3 16:20
   * @param date 时间
   * @return Map<String, long[]>
   */
  private Map<String, long[]> getUserTimeHourMap(Date date) {
    Map<String, long[]> userTimeHourMap = new HashMap<>(16);
    userTimeHourMap.put(Contants.VISITOR, getEveryHour(date));
    return userTimeHourMap;
  }

  /**
   * 获取用户概况中的时段分布(一天24小时)+小时横坐标
   *
   * @author: lingjian @Date: 2020/1/3 16:20
   * @return Map<String, int[]>
   */
  @Override
  public Map<String, Map> getUserTimeByHour(Date date) {
    Map<String, Map> userTimeMap = new HashMap<>(16);
    userTimeMap.put(Contants.ABSCISSA, DateTimeUtil.getHourAbscissa(1));
    userTimeMap.put(Contants.TODAY, getUserTimeHourMap(date));
    return userTimeMap;
  }

  /**
   * 获取某几天每天的访客数
   *
   * @author: lingjian @Date: 2020/1/3 16:23
   * @param num 天数
   * @param date 时间
   * @return long[]
   */
  private long[] getEveryDay(int num, Date date) {
    long[] arr = new long[num];
    for (int i = 0; i < arr.length; i++) {
      arr[i] =
          getUserOverviewByDate(
                  DateTimeUtil.getDayFromNum(date, num - i - 1),
                  DateTimeUtil.getDayFromNum(date, num - i - 1),
                  0,
                  24)
              .getVisitorCount();
    }
    return arr;
  }

  /**
   * 获取用户概况中的时段分布(num天)
   *
   * @author: lingjian @Date: 2020/1/3 16:23
   * @param num 天数
   * @param date 时间
   * @return Map<String, long[]>
   */
  private Map<String, long[]> getUserTimeDayMap(int num, Date date) {
    Map<String, long[]> userTimeDayMap = new HashMap<>(16);
    userTimeDayMap.put(Contants.VISITOR, getEveryDay(num, date));
    return userTimeDayMap;
  }

  /**
   * 根据时间获取用户概况中的时段分布(天)+日期横坐标
   *
   * @author: lingjian @Date: 2020/1/3 16:22
   * @param date 时间
   * @param num 天数
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getUserTimeByDay(Date date, Integer num) {
    Map<String, Map> userTimeMap = new HashMap<>(16);
    userTimeMap.put(Contants.ABSCISSA, DateTimeUtil.getDayAbscissa(num, date));
    userTimeMap.put(Contants.TODAY, getUserTimeDayMap(num, date));
    return userTimeMap;
  }

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2019/5/21 9:38
   * @param startDate
   * @param endDate
   * @return
   */
  private List<XwtViewUserArea> getUserAreaByDate(Date startDate, Date endDate) {
    return userAreaDAO.listByCreateTimeBetween(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
  }

  /**
   * 根据时间，天数获取用户地域分布
   *
   * @author: lingjian @Date: 2020/1/3 16:27
   * @param date 时间
   * @param num 天数
   * @return List<XwtViewUserArea> 用户地域表
   */
  @Override
  public List<XwtViewUserArea> getUserArea(Date date, Integer num) {
    if (num != null) {
      return getUserAreaByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getUserAreaByDate(date, date);
    }
  }
}

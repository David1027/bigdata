package com.shoestp.mains.service.impl.DataView;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.user.UserAreaDao;
import com.shoestp.mains.dao.DataView.user.UserDao;
import com.shoestp.mains.dao.DataView.user.UserSexDao;
import com.shoestp.mains.service.DataView.UserService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.user.DataViewUserAreaView;
import com.shoestp.mains.views.DataView.user.DataViewUserSexView;
import com.shoestp.mains.views.DataView.user.DataViewUserView;

import org.ehcache.impl.internal.classes.commonslang.ArrayUtils;
import org.springframework.stereotype.Service;

import static com.shoestp.mains.utils.dateUtils.DateTimeUtil.DATE_FARMAT_10;

/**
 * @description: 用户-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:22
 */
@Service
public class UserServiceImpl implements UserService {

  @Resource private UserDao userDao;
  @Resource private UserSexDao userSexDao;
  @Resource private UserAreaDao userAreaDao;

  /**
   * 获取用户概况
   *
   * @author: lingjian @Date: 2019/5/13 14:50
   * @param startDate
   * @param endDate
   * @return DataViewUserView
   */
  @Override
  public DataViewUserView getUserOverview(Date startDate, Date endDate) {
    return userDao.findUserByCreateTimeBetweenObject(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
  }

  /**
   * 根据时间获取用户的访客数
   *
   * @author: lingjian @Date: 2019/5/13 15:50
   * @param date
   * @param start
   * @param end
   * @return
   */
  public List<DataViewUserView> getUserTimeByHour(Date date, int start, int end) {
    return userDao
        .findUserByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end))
        .stream()
        .map(
            bean -> {
              DataViewUserView dataViewUserView = new DataViewUserView();
              dataViewUserView.setVisitorCount(bean.get(0, Integer.class));
              return dataViewUserView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 获取24个小时中每个小时的访客数
   *
   * @author: lingjian @Date: 2019/5/13 15:50
   * @param date
   * @return
   */
  public int[] getEveryHour(Date date) {
    int[] arr = new int[23];
    for (int i = 0; i < arr.length; i++) {
      if (!getUserTimeByHour(date, i, i + 1).isEmpty()) {
        arr[i] = getUserTimeByHour(date, i, i + 1).get(0).getVisitorCount().intValue();
      }
    }
    return arr;
  }

  /**
   * 获取用户概况中的时段分布(一天24小时)
   *
   * @author: lingjian @Date: 2019/5/16 9:33
   * @param date
   * @return
   */
  public Map<String, int[]> getUserTime(Date date) {
    Map<String, int[]> userTimeMap = new HashMap<>();
    userTimeMap.put("visitor", getEveryHour(date));
    return userTimeMap;
  }

  /**
   * 获取用户概况中的时段分布的横坐标-24小时
   *
   * @author: lingjian @Date: 2019/5/16 9:34
   * @return
   */
  public Map<String, String[]> getHourAbscissa() {
    String[] arr = new String[24];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i + 1 + "";
    }
    Map<String, String[]> arrMap = new HashMap<>();
    arrMap.put("hour", arr);
    return arrMap;
  }

  /**
   * 获取用户概况中的时段分布(一天24小时)+小时横坐标
   *
   * @author: lingjian @Date: 2019/5/13 15:53
   * @return Map<String, int[]>
   */
  @Override
  public Map<String, Map> getUserTimeByDay(Date date) {
    Map<String, Map> userTimeMap = new HashMap<>();
    userTimeMap.put("abscissa", getHourAbscissa());
    userTimeMap.put("day", getUserTime(date));
    return userTimeMap;
  }

  /**
   * 获取某几天每天的访客数
   *
   * @author: lingjian @Date: 2019/5/16 10:18
   * @param num
   * @param date
   * @return
   */
  public int[] getEveryDay(int num, Date date) {
    int[] arr = new int[num];
    for (int i = 0; i < arr.length; i++) {
      if (!getUserTimeByHour(DateTimeUtil.getDayFromNum(date, i), 0, 24).isEmpty()) {
        arr[i] =
            getUserTimeByHour(DateTimeUtil.getDayFromNum(date, i), 0, 24)
                .get(0)
                .getVisitorCount()
                .intValue();
      }
    }
    return arr;
  }

  /**
   * 获取用户概况中的时段分布的横坐标-num天
   *
   * @author: lingjian @Date: 2019/5/16 10:20
   * @param num
   * @param date
   * @return
   */
  public Map<String, String[]> getDayAbscissa(int num, Date date) {
    String[] arr = new String[num];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = DateTimeUtil.formatDateToString(DateTimeUtil.getDayFromNum(date, i), DATE_FARMAT_10);
    }
    Map<String, String[]> arrMap = new HashMap<>();
    arrMap.put("everyday", arr);
    return arrMap;
  }
  /**
   * 获取用户概况中的时段分布(num天)
   *
   * @param date
   * @return
   */
  public Map<String, int[]> getUserTimeDayMap(int num, Date date) {
    Map<String, int[]> userTimeWeekMap = new HashMap<>();
    userTimeWeekMap.put("visitor", getEveryDay(num, date));
    return userTimeWeekMap;
  }

  /**
   * 根据时间获取用户概况中的时段分布(一周7天)+横坐标(一周)
   *
   * @author: lingjian @Date: 2019/5/16 10:13
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getUserTimeByWeek(Date date) {
    Map<String, Map> userTimeMap = new HashMap<>();
    userTimeMap.put("abscissa", getDayAbscissa(7, date));
    userTimeMap.put("week", getUserTimeDayMap(7, date));
    return userTimeMap;
  }

  /**
   * 根据时间获取用户概况中的时段分布(一个月30天)
   *
   * @author: lingjian @Date: 2019/5/16 10:40
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getUserTimeByMonth(Date date) {
    Map<String, Map> userTimeMap = new HashMap<>();
    userTimeMap.put("abscissa", getDayAbscissa(30, date));
    userTimeMap.put("week", getUserTimeDayMap(30, date));
    return userTimeMap;
  }

  /**
   * 根据时间获取用户性别数量
   *
   * @author: lingjian @Date: 2019/5/13 16:13
   * @param startDate
   * @param endDate
   * @return List<DataViewUserSexView>
   */
  @Override
  public List<DataViewUserSexView> getUserSex(Date startDate, Date endDate) {
    return userSexDao
        .findUserSexByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
        .stream()
        .map(
            bean -> {
              DataViewUserSexView dataViewUserSexView = new DataViewUserSexView();
              dataViewUserSexView.setManSexCount(bean.get(0, Integer.class));
              dataViewUserSexView.setWomanSexCount(bean.get(1, Integer.class));
              dataViewUserSexView.setUnknownSexCount(bean.get(2, Integer.class));
              return dataViewUserSexView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2019/5/13 16:35
   * @param startDate
   * @param endDate
   * @return List<DataViewUserAreaView>
   */
  @Override
  public List<DataViewUserAreaView> getUserArea(Date startDate, Date endDate) {
    return userAreaDao
        .findUserAreaByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
        .stream()
        .map(
            bean -> {
              DataViewUserAreaView dataViewUserAreaView = new DataViewUserAreaView();
              dataViewUserAreaView.setArea(bean.get(0, String.class));
              dataViewUserAreaView.setAreaCount(bean.get(1, Integer.class));
              return dataViewUserAreaView;
            })
        .collect(Collectors.toList());
  }
}

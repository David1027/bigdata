package com.shoestp.mains.service.impl.DataView;

import java.util.*;
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

import org.springframework.stereotype.Service;

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
   * 判断是否为空处理
   *
   * @author: lingjian @Date: 2019/5/17 9:02
   * @param user
   * @return
   */
  public DataViewUserView isNullTo(DataViewUserView user) {
    if (user.getVisitorCount() == null) {
      user.setVisitorCount(0);
      user.setNewVisitorCount(0);
      user.setOldVisitorCount(0);
      user.setRegisterCount(0);
      user.setPurchaseCount(0);
      user.setSupplierCount(0);
    }
    return user;
  }

  /**
   * 获取用户概况
   *
   * @author: lingjian @Date: 2019/5/13 14:50
   * @param date
   * @return DataViewUserView
   */
  @Override
  public DataViewUserView getUserOverview(Date date, String type) {
    DataViewUserView user = null;
    if ("week".equals(type)) {
      user =
          isNullTo(
              userDao.findUserByCreateTimeBetweenObject(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 7), 0),
                  DateTimeUtil.getTimesOfDay(date, 24)));
    } else if ("month".equals(type)) {
      user =
          isNullTo(
              userDao.findUserByCreateTimeBetweenObject(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 30), 0),
                  DateTimeUtil.getTimesOfDay(date, 24)));
    } else {
      user =
          isNullTo(
              userDao.findUserByCreateTimeBetweenObject(
                  DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24)));
    }

    return user;
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
  public List<DataViewUserView> getUserTime(Date date, int start, int end) {
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
    int[] arr = new int[24];
    for (int i = 0; i < arr.length; i++) {
      if (!getUserTime(date, i, i + 1).isEmpty()) {
        arr[i] = getUserTime(date, i, i + 1).get(0).getVisitorCount();
      }
    }
    return arr;
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
      if (!getUserTime(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24).isEmpty()) {
        arr[i] =
            getUserTime(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getVisitorCount();
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
  public Map<String, int[]> getUserTimeHourMap(Date date) {
    Map<String, int[]> userTimeHourMap = new HashMap<>();
    userTimeHourMap.put("visitor", getEveryHour(date));
    return userTimeHourMap;
  }

  /**
   * 获取用户概况中的时段分布(num天)
   *
   * @param date
   * @return
   */
  public Map<String, int[]> getUserTimeDayMap(int num, Date date) {
    Map<String, int[]> userTimeDayMap = new HashMap<>();
    userTimeDayMap.put("visitor", getEveryDay(num, date));
    return userTimeDayMap;
  }

  /**
   * 获取用户概况中的时段分布(一天24小时)+小时横坐标
   *
   * @author: lingjian @Date: 2019/5/13 15:53
   * @return Map<String, int[]>
   */
  @Override
  public Map<String, Map> getUserTimeByHour(Date date) {
    Map<String, Map> userTimeMap = new HashMap<>();
    userTimeMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    userTimeMap.put("hour", getUserTimeHourMap(date));
    return userTimeMap;
  }

  /**
   * 根据时间获取用户概况中的时段分布(天)+日期横坐标
   *
   * @author: lingjian @Date: 2019/5/16 10:13
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getUserTimeByDay(Date date, Integer day) {
    Map<String, Map> userTimeMap = new HashMap<>();
    userTimeMap.put("abscissa", DateTimeUtil.getDayAbscissa(day, date));
    userTimeMap.put("day", getUserTimeDayMap(day, date));
    return userTimeMap;
  }

  /**
   * 根据时间获取用户性别数量
   *
   * @author: lingjian @Date: 2019/5/13 16:13
   * @param date
   * @param type
   * @return List<DataViewUserSexView>
   */
  @Override
  public List<DataViewUserSexView> getUserSex(Date date, String type) {
    List<DataViewUserSexView> list = null;
    if ("week".equals(type)) {
      list =
          userSexDao
              .findUserSexByCreateTimeBetween(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 7), 0),
                  DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    DataViewUserSexView dataViewUserSexView = new DataViewUserSexView();
                    dataViewUserSexView.setSex(bean.get(0, String.class));
                    dataViewUserSexView.setSexCount(bean.get(1, Integer.class));
                    return dataViewUserSexView;
                  })
              .collect(Collectors.toList());
    } else if ("month".equals(type)) {
      list =
          userSexDao
              .findUserSexByCreateTimeBetween(
                  DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 30), 0),
                  DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    DataViewUserSexView dataViewUserSexView = new DataViewUserSexView();
                    dataViewUserSexView.setSex(bean.get(0, String.class));
                    dataViewUserSexView.setSexCount(bean.get(1, Integer.class));
                    return dataViewUserSexView;
                  })
              .collect(Collectors.toList());
    } else {
      list =
          userSexDao
              .findUserSexByCreateTimeBetween(
                  DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24))
              .stream()
              .map(
                  bean -> {
                    DataViewUserSexView dataViewUserSexView = new DataViewUserSexView();
                    dataViewUserSexView.setSex(bean.get(0, String.class));
                    dataViewUserSexView.setSexCount(bean.get(1, Integer.class));
                    return dataViewUserSexView;
                  })
              .collect(Collectors.toList());
    }
    return list;
  }

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2019/5/13 16:35
   * @param date
   * @param type
   * @return List<DataViewUserAreaView>
   */
  @Override
  public List<DataViewUserAreaView> getUserArea(Date date, String type) {
    List<DataViewUserAreaView> list = null;
    if ("week".equals(type)) {
      list = userAreaDao
          .findUserAreaByCreateTimeBetween(
              DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 7), 0),
              DateTimeUtil.getTimesOfDay(date, 24))
          .stream()
          .map(
              bean -> {
                DataViewUserAreaView dataViewUserAreaView = new DataViewUserAreaView();
                dataViewUserAreaView.setArea(bean.get(0, String.class));
                dataViewUserAreaView.setAreaCount(bean.get(1, Integer.class));
                return dataViewUserAreaView;
              })
          .collect(Collectors.toList());
    } else if ("month".equals(type)) {
      list = userAreaDao
          .findUserAreaByCreateTimeBetween(
              DateTimeUtil.getTimesOfDay(DateTimeUtil.getDayFromNum(date, 30), 0),
              DateTimeUtil.getTimesOfDay(date, 24))
          .stream()
          .map(
              bean -> {
                DataViewUserAreaView dataViewUserAreaView = new DataViewUserAreaView();
                dataViewUserAreaView.setArea(bean.get(0, String.class));
                dataViewUserAreaView.setAreaCount(bean.get(1, Integer.class));
                return dataViewUserAreaView;
              })
          .collect(Collectors.toList());
    } else {
      list = userAreaDao
          .findUserAreaByCreateTimeBetween(
              DateTimeUtil.getTimesOfDay(date, 0), DateTimeUtil.getTimesOfDay(date, 24))
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
    return list;
  }
}

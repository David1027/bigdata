package com.shoestp.mains.service.impl.DataView;

import com.shoestp.mains.dao.dataView.user.UserAreaDao;
import com.shoestp.mains.dao.dataView.user.UserDao;
import com.shoestp.mains.dao.dataView.user.UserSexDao;
import com.shoestp.mains.service.dataView.UserService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.user.DataViewUserAreaView;
import com.shoestp.mains.views.DataView.user.DataViewUserSexView;
import com.shoestp.mains.views.DataView.user.DataViewUserView;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
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
   * 根据时间，日期类型获取用户概况
   *
   * @author: lingjian @Date: 2019/5/20 16:44
   * @param startDate
   * @param endDate
   * @return
   */
  public DataViewUserView getUserOverviewByDate(Date startDate, Date endDate) {
    return isNullTo(
        userDao.findUserByCreateTimeBetweenObject(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
  }

  /**
   * 根据时间，日期类型获取用户概况
   *
   * @author: lingjian @Date: 2019/5/13 14:50
   * @param date
   * @return DataViewUserView
   */
  @Override
  public DataViewUserView getUserOverview(Date date, Integer num) {
    if (num != null) {
      return getUserOverviewByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getUserOverviewByDate(date, date);
    }
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
    userTimeMap.put("day", getUserTimeHourMap(date));
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
  public Map<String, Map> getUserTimeByDay(Date date, Integer num) {
    Map<String, Map> userTimeMap = new HashMap<>();
    userTimeMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    userTimeMap.put("day", getUserTimeDayMap(num, date));
    return userTimeMap;
  }

  /**
   * 根据时间获取用户性别数量
   *
   * @author: lingjian @Date: 2019/5/20 16:46
   * @param startDate
   * @param endDate
   * @return
   */
  public List<DataViewUserSexView> getUserSexByDate(Date startDate, Date endDate) {
    return userSexDao
        .findUserSexByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
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

  /**
   * 根据时间,日期类型获取用户性别数量
   *
   * @author: lingjian @Date: 2019/5/13 16:13
   * @param date
   * @param num
   * @return List<DataViewUserSexView>
   */
  @Override
  public List<DataViewUserSexView> getUserSex(Date date, Integer num) {
    if (num != null) {
      return getUserSexByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getUserSexByDate(date, date);
    }
  }

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2019/5/21 9:38
   * @param startDate
   * @param endDate
   * @return
   */
  public List<DataViewUserAreaView> getUserAreaByDate(Date startDate, Date endDate) {
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

  /**
   * 根据时间，天数获取用户地域分布
   *
   * @author: lingjian @Date: 2019/5/13 16:35
   * @param date
   * @param num
   * @return List<DataViewUserAreaView>
   */
  @Override
  public List<DataViewUserAreaView> getUserArea(Date date, Integer num) {
    if (num != null) {
      return getUserAreaByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getUserAreaByDate(date, date);
    }
  }
}

package com.shoestp.mains.service.impl.DataView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
   * 获取用户概况
   *
   * @author: lingjian @Date: 2019/5/13 14:50
   * @param startDate
   * @param endDate
   * @return List<DataViewUserView>
   */
  @Override
  public List<DataViewUserView> getUserOverview(Date startDate, Date endDate) {
    return userDao
        .findUserByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
        .stream()
        .map(
            bean -> {
              DataViewUserView dataViewUserView = new DataViewUserView();
              dataViewUserView.setVisitorCount(bean.get(0, Integer.class));
              dataViewUserView.setNewVisitorCount(bean.get(1, Integer.class));
              dataViewUserView.setOldVisitorCount(bean.get(2, Integer.class));
              dataViewUserView.setRegisterCount(bean.get(3, Integer.class));
              dataViewUserView.setPurchaseCount(bean.get(4, Integer.class));
              dataViewUserView.setSupplierCount(bean.get(5, Integer.class));
              return dataViewUserView;
            })
        .collect(Collectors.toList());
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
   * 获取用户概况中的时段分布
   *
   * @author: lingjian @Date: 2019/5/13 15:53
   * @return Map<String, int[]>
   */
  @Override
  public Map<String, int[]> getUserTime() {
    Map<String, int[]> userTimeMap = new HashMap<>();
    userTimeMap.put("visitor", getEveryHour(new Date()));
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

package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.entitys.DataView.user.DataViewUser;
import com.shoestp.mains.views.DataView.user.DataViewUserAreaView;
import com.shoestp.mains.views.DataView.user.DataViewUserSexView;
import com.shoestp.mains.views.DataView.user.DataViewUserView;

/**
 * @description: 用户-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:22
 */
public interface UserService {

  /**
   * 获取用户概况
   *
   * @author: lingjian @Date: 2019/5/13 14:49
   * @param date
   * @return List<DataViewUserView>
   */
  DataViewUserView getUserOverview(Date date,String type);

  /**
   * 获取用户概况中的时段分布(小时)
   *
   * @author: lingjian @Date: 2019/5/13 15:53
   * @return Map<String, int[]>
   */
  Map<String, Map> getUserTimeByHour(Date date);

  /**
   * 根据时间获取用户概况中的时段分布(天)
   *
   * @author: lingjian @Date: 2019/5/16 10:13
   * @param date
   * @return
   */
  Map<String, Map> getUserTimeByDay(Date date,Integer day);

  /**
   * 根据时间获取用户性别数量
   *
   * @author: lingjian @Date: 2019/5/13 16:13
   * @param startDate
   * @param endDate
   * @return List<DataViewUserSexView>
   */
  List<DataViewUserSexView> getUserSex(Date startDate, Date endDate);

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2019/5/13 16:35
   * @param startDate
   * @param endDate
   * @return List<DataViewUserAreaView>
   */
  List<DataViewUserAreaView> getUserArea(Date startDate, Date endDate);
}

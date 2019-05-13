package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.List;

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
   * @param startDate
   * @param endDate
   * @return List<DataViewUserView>
   */
  List<DataViewUserView> getUserOverview(Date startDate, Date endDate);
}

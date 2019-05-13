package com.shoestp.mains.service.impl.DataView;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.user.UserDao;
import com.shoestp.mains.service.DataView.UserService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.flow.FlowDeviceView;
import com.shoestp.mains.views.DataView.user.DataViewUserView;

import org.springframework.stereotype.Service;

/**
 * @description: 用户-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:22
 */
@Service
public class UserServiceImpl implements UserService {

  @Resource private UserDao userDao;

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
}

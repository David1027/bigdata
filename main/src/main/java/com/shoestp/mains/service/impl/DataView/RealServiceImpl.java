package com.shoestp.mains.service.impl.DataView;

import java.util.*;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.RealDao;
import com.shoestp.mains.entitys.DataView.DataViewReal;
import com.shoestp.mains.service.DataView.RealService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.DataViewRealView;
import com.sun.org.apache.bcel.internal.generic.NEW;

import org.springframework.stereotype.Service;

/**
 * @description: 实时-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:22
 */
@Service
public class RealServiceImpl implements RealService {

  @Resource private RealDao realDao;

  /**
   * 累加数据
   *
   * @author: lingjian @Date: 2019/5/9 15:30
   * @param real
   * @return
   */
  public int[] add(List<DataViewReal> real) {
    int[] arr = new int[5];
    for (int i = 0; i < real.size(); i++) {
      arr[0] += real.get(i).getVisitorCount();
      arr[1] += real.get(i).getViewCount();
      arr[2] += real.get(i).getRegisterCount();
      arr[3] += real.get(i).getInquiryCount();
      arr[4] += real.get(i).getRfqCount();
    }
    return arr;
  }

  /**
   * 获取实时概况的值
   *
   * @author: lingjian @Date: 2019/5/9 15:31
   * @return DataViewRealView对象
   */
  @Override
  public DataViewRealView getRealOverview() {
    // 获取当天的值
    List<DataViewReal> realToday =
        realDao.findAllByCreateTimeBetween(
            DateTimeUtil.getTimesmorning(), DateTimeUtil.getTimesnight());
    // 获取昨天的值
    List<DataViewReal> realendYesterday =
        realDao.findAllByCreateTimeBetween(
            DateTimeUtil.getYesterdaymorning(), DateTimeUtil.getYesterdaynight());
    // 获取上周同一天的值
    List<DataViewReal> realWeek =
        realDao.findAllByCreateTimeBetween(
            DateTimeUtil.getWeekmorning(), DateTimeUtil.getWeeknight());

    // 获取当天所有的累加值
    int[] arrToday = add(realToday);
    // 获取昨天所有的累加值
    int[] arrYesterday = add(realendYesterday);
    // 获取上周同一天所有的累加值
    int[] arrWeek = add(realendYesterday);

    DataViewRealView realView = new DataViewRealView();
    // 访客数
    realView.setVisitorCount(arrToday[0]);
    realView.setVisitorCompareYesterday((arrToday[0] - arrYesterday[0]) / (arrYesterday[0] * 1.0));
    realView.setVisitorCompareWeek((arrToday[0] - arrWeek[0]) / (arrWeek[0] * 1.0));
    // 浏览量
    realView.setViewCount(arrToday[1]);
    realView.setViewCompareYesterday((arrToday[1] - arrYesterday[1]) / (arrYesterday[1] * 1.0));
    realView.setViewCompareWeek((arrToday[1] - arrWeek[1]) / (arrWeek[1] * 1.0));
    // 注册量
    realView.setRegisterCount(arrToday[2]);
    realView.setRegisterCompareYesterday((arrToday[2] - arrYesterday[2]) / (arrYesterday[2] * 1.0));
    realView.setRegisterCompareWeek((arrToday[2] - arrWeek[2]) / (arrWeek[2] * 1.0));
    // 询盘量
    realView.setInquiryCount(arrToday[3]);
    realView.setInquiryCompareYesterday((arrToday[3] - arrYesterday[3]) / (arrYesterday[3] * 1.0));
    realView.setInquiryCompareWeek((arrToday[3] - arrWeek[3]) / (arrWeek[3] * 1.0));
    // RFQ数
    realView.setRfqCount(arrToday[4]);
    realView.setRfqCompareYesterday((arrToday[4] - arrYesterday[4]) / (arrYesterday[4] * 1.0));
    realView.setRfqCompareWeek((arrToday[4] - arrWeek[4]) / (arrWeek[4] * 1.0));
    // 更新时间
    realView.setUpdateTime(new Date());

    return realView;
  }

  /**
   * 获取实时趋势的值
   *
   * @author: lingjian @Date: 2019/5/9 16:08
   * @param date
   * @return
   */
  @Override
  public Map<String,int[]> getRealTrend(Date date) {



    List<DataViewReal> real0 =
            realDao.findAllByCreateTimeBetween(
                    DateTimeUtil.getTimesOfDay(date,0),DateTimeUtil.getTimesOfDay(date,1));
    int[] arr0 = add(real0);

    List<DataViewReal> real1 =
            realDao.findAllByCreateTimeBetween(
                    DateTimeUtil.getTimesOfDay(date,1),DateTimeUtil.getTimesOfDay(date,2));
    int[] arr1 = add(real1);

    // 访客数
    int[] arrVisitor = new int[2];
    arrVisitor[0] = arr0[0];
    arrVisitor[1] = arr1[0];

    //浏览量
    int[] arrView = new int[2];
    arrView[0] = arr0[1];
    arrView[1] = arr1[1];

    //注册量
    int[] arrRegister = new int[2];
    arrRegister[0] = arr0[2];
    arrRegister[1] = arr1[2];

    //询盘数
    int[] arrInquiry = new int[2];
    arrInquiry[0] = arr0[3];
    arrInquiry[1] = arr1[3];

    Map<String,int[]> visitorMap = new HashMap<>();
    visitorMap.put("visitor",arrVisitor);
    visitorMap.put("view",arrView);
    visitorMap.put("register",arrRegister);
    visitorMap.put("inquiry",arrInquiry);

    return visitorMap;
  }
}

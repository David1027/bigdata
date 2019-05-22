package com.shoestp.mains.service.impl.DataView;

import java.util.*;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.realcountry.RealCountryDao;
import com.shoestp.mains.service.DataView.RealService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.real.RealOverView;
import com.shoestp.mains.views.DataView.real.RealView;

import org.springframework.stereotype.Service;

/**
 * @description: 实时-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:22
 */
@Service
public class RealServiceImpl implements RealService {

  @Resource private RealCountryDao realCountryDao;

  /**
   * 获取两个值的比较率
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param num1
   * @param num2
   * @return
   */
  public Double getCompare(int num1, int num2) {
    if (num2 == 0) {
      num2 = 1;
    }
    return (num1 - num2) / (num2 * 1.0);
  }

  /**
   * 判断是否为空处理
   *
   * @author: lingjian @Date: 2019/5/16 16:20
   * @param real
   * @return
   */
  public RealView isNullTo(RealView real) {
    if (real.getVisitorCount() == null) {
      real.setVisitorCount(0);
      real.setViewCount(0);
      real.setRegisterCount(0);
      real.setInquiryCount(0);
      real.setRfqCount(0);
    }
    return real;
  }

  /**
   * 获取实时概况的值
   *
   * @author: lingjian @Date: 2019/5/9 15:31
   * @return DataViewRealView对象
   */
  @Override
  public RealOverView getRealOverview() {
    // 获取当天，昨天，上周同一天所有的累加值
    RealView today =
        isNullTo(
            realCountryDao.findAllByCreateTimeBetween(
                DateTimeUtil.getTimesmorning(), DateTimeUtil.getTimesnight()));
    RealView yesterday =
        isNullTo(
            realCountryDao.findAllByCreateTimeBetween(
                DateTimeUtil.getYesterdaymorning(), DateTimeUtil.getYesterdaynight()));
    RealView week =
        isNullTo(
            realCountryDao.findAllByCreateTimeBetween(
                DateTimeUtil.getWeekmorning(), DateTimeUtil.getWeeknight()));
    // 创建返回前端对象
    RealOverView realOverView = new RealOverView();
    realOverView.setVisitorCount(today.getVisitorCount());
    realOverView.setVisitorCompareYesterday(
        getCompare(today.getVisitorCount(), yesterday.getVisitorCount()));
    realOverView.setVisitorCompareWeek(getCompare(today.getVisitorCount(), week.getVisitorCount()));
    realOverView.setViewCount(today.getViewCount());
    realOverView.setViewCompareYesterday(
        getCompare(today.getViewCount(), yesterday.getViewCount()));
    realOverView.setViewCompareWeek(getCompare(today.getViewCount(), week.getViewCount()));
    realOverView.setRegisterCount(today.getRegisterCount());
    realOverView.setRegisterCompareYesterday(
        getCompare(today.getRegisterCount(), yesterday.getRegisterCount()));
    realOverView.setRegisterCompareWeek(
        getCompare(today.getRegisterCount(), week.getRegisterCount()));
    realOverView.setInquiryCount(today.getInquiryCount());
    realOverView.setInquiryCompareYesterday(
        getCompare(today.getInquiryCount(), yesterday.getInquiryCount()));
    realOverView.setInquiryCompareWeek(getCompare(today.getInquiryCount(), week.getInquiryCount()));
    realOverView.setRfqCount(today.getRfqCount());
    realOverView.setRfqCompareYesterday(getCompare(today.getRfqCount(), yesterday.getRfqCount()));
    realOverView.setRfqCompareWeek(getCompare(today.getRfqCount(), week.getRfqCount()));
    return realOverView;
  }

  /**
   * 获取一定时间段以内的累加值
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param date
   * @param start
   * @param end
   * @return
   */
  public RealView getAddByTime(Date date, int start, int end) {
    return isNullTo(
            realCountryDao.findAllByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end)));
  }

  /**
   * 获取24个小时中每一个小时的值
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param date
   * @return
   */
  public int[] getEveryHour(Date date, String parameter) {
    int[] arr = new int[12];
    for (int i = 0; i < arr.length; i++) {
      if ("visitorCount".equals(parameter)) {
        arr[i] = getAddByTime(date, i * 2, i * 2 + 2).getVisitorCount();
      } else if ("viewCount".equals(parameter)) {
        arr[i] = getAddByTime(date, i * 2, i * 2 + 2).getViewCount();
      } else if ("registerCount".equals(parameter)) {
        arr[i] = getAddByTime(date, i * 2, i * 2 + 2).getRegisterCount();
      } else if ("inquiryCount".equals(parameter)) {
        arr[i] = getAddByTime(date, i * 2, i * 2 + 2).getInquiryCount();
      }
    }
    return arr;
  }

  /**
   * 获取当前时间的实时趋势的值
   *
   * @param date
   * @return
   */
  public Map<String, int[]> getRealTrendByDay(Date date) {
    Map<String, int[]> visitorMap = new HashMap<>();
    visitorMap.put("visitorCount", getEveryHour(date, "visitorCount"));
    visitorMap.put("viewCount", getEveryHour(date, "viewCount"));
    visitorMap.put("registerCount", getEveryHour(date, "registerCount"));
    visitorMap.put("inquiryCount", getEveryHour(date, "inquiryCount"));
    return visitorMap;
  }

  /**
   * 获取今日和对比日的实时趋势的值
   *
   * @author: lingjian @Date: 2019/5/9 16:08
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getRealTrend(Date date) {
    Map<String, Map> visitorAllMap = new HashMap<>();
    visitorAllMap.put("abscissa", DateTimeUtil.getHourAbscissa(2));
    visitorAllMap.put("today", getRealTrendByDay(new Date()));
    visitorAllMap.put("ratherday", getRealTrendByDay(date));
    return visitorAllMap;
  }
}

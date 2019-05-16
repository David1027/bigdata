package com.shoestp.mains.service.impl.DataView;

import java.util.*;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.real.RealDao;
import com.shoestp.mains.entitys.DataView.real.DataViewReal;
import com.shoestp.mains.service.DataView.RealService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.real.RealView;

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
   * 获取某一天开始时间和结束之间以内的累加值
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param start
   * @param end
   * @return
   */
  public int[] getAddByHour(Date start, Date end) {
    return add(realDao.findAllByCreateTimeBetween(start, end));
  }

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
   * 获取实时概况的值
   *
   * @author: lingjian @Date: 2019/5/9 15:31
   * @return DataViewRealView对象
   */
  @Override
  public RealView getRealOverview() {
    // 获取当天，昨天，上周同一天所有的累加值
    int[] arrToday = getAddByHour(DateTimeUtil.getTimesmorning(), DateTimeUtil.getTimesnight());
    int[] arrYesterday =
        getAddByHour(DateTimeUtil.getYesterdaymorning(), DateTimeUtil.getYesterdaynight());
    int[] arrWeek = getAddByHour(DateTimeUtil.getWeekmorning(), DateTimeUtil.getWeeknight());
    // 创建返回前端对象
    RealView realView = new RealView();
    realView.setVisitorCount(arrToday[0]);
    realView.setVisitorCompareYesterday(getCompare(arrToday[0], arrYesterday[0]));
    realView.setVisitorCompareWeek(getCompare(arrToday[0], arrWeek[0]));
    realView.setViewCount(arrToday[1]);
    realView.setViewCompareYesterday(getCompare(arrToday[1], arrYesterday[1]));
    realView.setViewCompareWeek(getCompare(arrToday[1], arrWeek[1]));
    realView.setRegisterCount(arrToday[2]);
    realView.setRegisterCompareYesterday(getCompare(arrToday[2], arrYesterday[2]));
    realView.setRegisterCompareWeek(getCompare(arrToday[2], arrWeek[2]));
    realView.setInquiryCount(arrToday[3]);
    realView.setInquiryCompareYesterday(getCompare(arrToday[3], arrYesterday[3]));
    realView.setInquiryCompareWeek(getCompare(arrToday[3], arrWeek[3]));
    realView.setRfqCount(arrToday[4]);
    realView.setRfqCompareYesterday(getCompare(arrToday[4], arrYesterday[4]));
    realView.setRfqCompareWeek(getCompare(arrToday[4], arrWeek[4]));
    realView.setUpdateTime(new Date());
    return realView;
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
  public int[] getAddByTime(Date date, int start, int end) {
    List<DataViewReal> real =
        realDao.findAllByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end));
    int[] arr = add(real);
    return arr;
  }

  /**
   * 获取24个小时中每一个小时的值
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param date
   * @return
   */
  public int[] getEveryHour(Date date, int num) {
    int[] arr = new int[24];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = getAddByTime(date, i, i + 1)[num];
    }
    return arr;
  }

  /**
   * 获取实时趋势的横坐标
   *
   * @author: lingjian @Date: 2019/5/15 16:55
   * @return
   */
  public Map<String, String[]> getHourAbscissa() {
    String[] arr = new String[24];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i + 1 + ":00";
    }
    Map<String, String[]> arrMap = new HashMap<>();
    arrMap.put("hour", arr);
    return arrMap;
  }

  /**
   * 获取当前时间的实时趋势的值
   *
   * @param date
   * @return
   */
  public Map<String, int[]> getRealTrendByDay(Date date) {
    Map<String, int[]> visitorMap = new HashMap<>();
    visitorMap.put("visitor", getEveryHour(date, 0));
    visitorMap.put("view", getEveryHour(date, 1));
    visitorMap.put("register", getEveryHour(date, 2));
    visitorMap.put("inquiry", getEveryHour(date, 3));
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
    visitorAllMap.put("abscissa", getHourAbscissa());
    visitorAllMap.put("today", getRealTrendByDay(new Date()));
    visitorAllMap.put("ratherday", getRealTrendByDay(date));
    return visitorAllMap;
  }
}

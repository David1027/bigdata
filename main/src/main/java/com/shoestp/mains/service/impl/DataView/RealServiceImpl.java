package com.shoestp.mains.service.impl.DataView;

import java.util.*;

import javax.annotation.Resource;

import com.shoestp.mains.constant.DataView.Contants;
import com.shoestp.mains.dao.DataView.flow.FlowPageDao;
import com.shoestp.mains.dao.DataView.realcountry.RealCountryDao;
import com.shoestp.mains.dao.DataView.user.UserDao;
import com.shoestp.mains.service.DataView.RealService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.utils.dateUtils.KeyValueViewUtil;
import com.shoestp.mains.views.DataView.real.IndexGrand;
import com.shoestp.mains.views.DataView.real.IndexOverView;
import com.shoestp.mains.views.DataView.real.RealOverView;
import com.shoestp.mains.views.DataView.real.RealView;
import com.shoestp.mains.views.DataView.utils.KeyValue;

import org.springframework.stereotype.Service;

/**
 * @description: 实时-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:22
 */
@Service
public class RealServiceImpl implements RealService {

  @Resource private RealCountryDao realCountryDao;
  @Resource private FlowPageDao flowPageDao;
  @Resource private UserDao userDao;

  /**
   * 获取两个值的比较率
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param num1
   * @param num2
   * @return
   */
  public Double getCompare(double num1, double num2) {
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
   * 判断是否为空处理
   *
   * @author: lingjian @Date: 2019/5/16 16:20
   * @param d
   * @return
   */
  public Double isNullToDouble(Double d) {
    if (d == null) {
      d = 0.0;
    }
    return d;
  }

  /**
   * 获取访客数，浏览量，注册量，询盘数
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public RealView getIndexObject(Date startDate, Date endDate) {
    return isNullTo(
        realCountryDao.findAllByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
  }

  /**
   * 获取跳失率
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public Double getFlowPageObject(Date startDate, Date endDate) {
    return isNullToDouble(
        flowPageDao.findByCreateTimeObject(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
  }

  /**
   * 每num周期的数据
   *
   * @author: lingjian @Date: 2019/5/22 16:18
   * @param date
   * @param parameter
   * @return
   */
  public List getIndex(Date date, String parameter, int num, int day, int start, int end) {
    List list = new ArrayList();
    for (int i = 0; i < num; i++) {
      if (Contants.VISITOR.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - start),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getVisitorCount());
      } else if (Contants.VIEW.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - start),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getViewCount());
      } else if (Contants.REGISTER.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - start),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getRegisterCount());
      } else if (Contants.INQUIRY.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - start),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getInquiryCount());
      } else if (Contants.JUMP.equals(parameter)) {
        list.add(
            getFlowPageObject(
                DateTimeUtil.getDayFromNum(date, (num - i) * day - start),
                DateTimeUtil.getDayFromNum(date, (num - i) * day - end)));
      }
    }
    return list;
  }

  /**
   * 获取每个参数的数据集合
   *
   * @author: lingjian @Date: 2019/5/23 10:03
   * @param date
   * @return
   */
  public List getIndexList(String indexCode, Date date, int num, int day, int start, int end) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            indexCode, getIndex(date, indexCode, num, day, start, end)));
    return keyValues;
  }

  /**
   * 获取首页整体看板时段分布
   *
   * @author: lingjian @Date: 2019/5/23 10:03
   * @param date
   * @param num
   * @return
   */
  @Override
  public Map getIndexOverviewTime(Date date, Integer num, String indexCode) {
    Map<String, List> map = new HashMap<>();
    if (Contants.SEVEN.equals(num)) {
      map.put(Contants.ABSCISSA, DateTimeUtil.getWeek(date));
      map.put(Contants.LIST, getIndexList(indexCode, date, Contants.TWELVE, num, 1, num));
    } else if (Contants.THIRTY.equals(num)) {
      map.put(Contants.ABSCISSA, DateTimeUtil.getMonth(date));
      map.put(Contants.LIST, getIndexList(indexCode, date, Contants.TWELVE, num, 1, num));
    } else {
      map.put(Contants.ABSCISSA, DateTimeUtil.getDay(date));
      map.put(Contants.LIST, getIndexList(indexCode, date, Contants.THIRTY, 1, 1, 1));
    }
    return map;
  }

  /**
   * 根据开始时间和结束时间获取实时概况
   *
   * @author: lingjian @Date: 2019/5/22 14:28
   * @param startDate
   * @param endDate
   * @return
   */
  public IndexOverView getIndexOverviewObject(Date startDate, Date endDate, int ynum, int wnum) {
    RealView today = getIndexObject(startDate, endDate);
    RealView yesterday =
        getIndexObject(
            DateTimeUtil.getDayFromNum(startDate, ynum), DateTimeUtil.getDayFromNum(endDate, ynum));
    RealView week =
        getIndexObject(
            DateTimeUtil.getDayFromNum(startDate, wnum), DateTimeUtil.getDayFromNum(endDate, wnum));

    Double todatJump = getFlowPageObject(startDate, endDate);
    Double yesterdayJump =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(startDate, ynum), DateTimeUtil.getDayFromNum(endDate, ynum));
    Double weekJump =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(startDate, wnum), DateTimeUtil.getDayFromNum(endDate, wnum));

    IndexOverView indexOverView = new IndexOverView();
    indexOverView.setVisitorCount(today.getVisitorCount());
    indexOverView.setVisitorCompareYesterday(
        getCompare(today.getVisitorCount(), yesterday.getVisitorCount()));
    indexOverView.setVisitorCompareWeek(
        getCompare(today.getVisitorCount(), week.getVisitorCount()));
    indexOverView.setViewCount(today.getViewCount());
    indexOverView.setViewCompareYesterday(
        getCompare(today.getViewCount(), yesterday.getViewCount()));
    indexOverView.setViewCompareWeek(getCompare(today.getViewCount(), week.getViewCount()));
    indexOverView.setRegisterCount(today.getRegisterCount());
    indexOverView.setRegisterCompareYesterday(
        getCompare(today.getRegisterCount(), yesterday.getRegisterCount()));
    indexOverView.setRegisterCompareWeek(
        getCompare(today.getRegisterCount(), week.getRegisterCount()));
    indexOverView.setInquiryCount(today.getInquiryCount());
    indexOverView.setInquiryCompareYesterday(
        getCompare(today.getInquiryCount(), yesterday.getInquiryCount()));
    indexOverView.setInquiryCompareWeek(
        getCompare(today.getInquiryCount(), week.getInquiryCount()));
    indexOverView.setJumpRate(todatJump);
    indexOverView.setJumpRateCompareYesterday(getCompare(todatJump, yesterdayJump));
    indexOverView.setJumpRateCompareWeek(getCompare(todatJump, weekJump));
    return indexOverView;
  }

  /**
   * 根据时间，天数获取实时概况
   *
   * @author: lingjian @Date: 2019/5/22 14:27
   * @param date
   * @param num
   * @return
   */
  @Override
  public IndexOverView getIndexOverview(Date date, Integer num) {
    if (num != null) {
      return getIndexOverviewObject(DateTimeUtil.getDayFromNum(date, num), date, num, 365);
    } else {
      return getIndexOverviewObject(date, date, 1, 7);
    }
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
  public List getEveryHour(Date date, String parameter) {
    List list = new ArrayList();
    for (int i = 0; i < Contants.TWELVE; i++) {
      if (Contants.VISITOR.equals(parameter)) {
        list.add(getAddByTime(date, i * 2, i * 2 + 2).getVisitorCount());
      } else if (Contants.VIEW.equals(parameter)) {
        list.add(getAddByTime(date, i * 2, i * 2 + 2).getViewCount());
      } else if (Contants.REGISTER.equals(parameter)) {
        list.add(getAddByTime(date, i * 2, i * 2 + 2).getRegisterCount());
      } else if (Contants.INQUIRY.equals(parameter)) {
        list.add(getAddByTime(date, i * 2, i * 2 + 2).getInquiryCount());
      }
    }
    return list;
  }

  /**
   * 获取当前时间的实时趋势的值
   *
   * @param date
   * @return
   */
  public Map<String, List> getRealTrendByDay(Date date) {
    Map<String, List> visitorMap = new HashMap<>();
    visitorMap.put(Contants.VISITOR, getEveryHour(date, Contants.VISITOR));
    visitorMap.put(Contants.VIEW, getEveryHour(date, Contants.VIEW));
    visitorMap.put(Contants.REGISTER, getEveryHour(date, Contants.REGISTER));
    visitorMap.put(Contants.INQUIRY, getEveryHour(date, Contants.INQUIRY));
    return visitorMap;
  }

  /**
   * 根据关键字获取当前时间的实时趋势的值
   *
   * @author: lingjian @Date: 2019/5/23 13:56
   * @param date
   * @param indexCode
   * @return
   */
  public Map<String, List> getIndexTrendByDay(Date date, String indexCode) {
    Map<String, List> visitorMap = new HashMap<>();
    visitorMap.put(indexCode, getEveryHour(date, indexCode));
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
    visitorAllMap.put(Contants.ABSCISSA, DateTimeUtil.getHourAbscissa(2));
    visitorAllMap.put(Contants.TODAY, getRealTrendByDay(new Date()));
    visitorAllMap.put(Contants.RATHERDAY, getRealTrendByDay(date));
    return visitorAllMap;
  }

  /**
   * 根据时间，关键字获取实时数据分析时段分布
   *
   * @author: lingjian @Date: 2019/5/23 13:56
   * @param date
   * @param indexCode
   * @return
   */
  @Override
  public Map<String, Map> getIndexTrend(Date date, String indexCode) {
    Map<String, Map> visitorAllMap = new HashMap<>();
    visitorAllMap.put(Contants.ABSCISSA, DateTimeUtil.getHourAbscissa(2));
    visitorAllMap.put(Contants.TODAY, getIndexTrendByDay(new Date(), indexCode));
    visitorAllMap.put(Contants.RATHERDAY, getIndexTrendByDay(date, indexCode));
    return visitorAllMap;
  }

  /**
   * 获取首页累计数据
   *
   * @author: lingjian @Date: 2019/5/23 14:23
   * @return
   */
  @Override
  public IndexGrand getIndexGrand() {
    IndexGrand grand = new IndexGrand();
    IndexGrand real = realCountryDao.findByCreateTimeBefore(DateTimeUtil.getTimesnight());
    IndexGrand user = userDao.findByCreateTimeBefore(DateTimeUtil.getTimesnight());
    grand.setGrandInquiry(real.getGrandInquiry());
    grand.setGrandRfq(real.getGrandRfq());
    grand.setGrandRegister(real.getGrandRegister());
    grand.setGrandPurchase(user.getGrandPurchase());
    grand.setGrandSupplier(user.getGrandSupplier());
    return grand;
  }
}

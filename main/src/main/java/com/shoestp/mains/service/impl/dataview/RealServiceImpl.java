package com.shoestp.mains.service.impl.dataview;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.constant.dataview.Contants;
import com.shoestp.mains.dao.dataview.flow.FlowPageDao;
import com.shoestp.mains.dao.dataview.real.RealDao;
import com.shoestp.mains.dao.dataview.user.UserDao;
import com.shoestp.mains.dao.transform.SearchWordInfoDao;
import com.shoestp.mains.dao.transform.WebVisitDao;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.service.dataview.RealService;
import com.shoestp.mains.service.urlmatchdatautil.URLMatchDataUtilService;
import com.shoestp.mains.utils.dateUtils.CalculateUtil;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.utils.dateUtils.KeyValueViewUtil;
import com.shoestp.mains.views.dataview.real.*;
import com.shoestp.mains.views.dataview.utils.KeyValue;

import org.springframework.stereotype.Service;

/**
 * @description: 实时-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:22
 */
@Service
public class RealServiceImpl implements RealService {

  @Resource private RealDao realDao;
  @Resource private FlowPageDao flowPageDao;
  @Resource private UserDao userDao;
  @Resource private WebVisitDao webVisitDao;
  @Resource private SearchWordInfoDao searchWordInfoDao;
  @Resource private URLMatchDataUtilService urlMatchDataUtilService;

  /**
   * 判断是否为空处理
   *
   * @author: lingjian @Date: 2019/5/16 16:20
   * @param real RealView实时前端展示类
   * @return RealView
   */
  private RealView isNullTo(RealView real) {
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
   * @param d Double
   * @return Double
   */
  private Double isNullToDouble(Double d) {
    if (d == null) {
      d = 0.0;
    }
    return CustomDoubleSerialize.setDouble(d);
  }

  /**
   * 获取访客数，浏览量，注册量，询盘数
   *
   * @author: lingjian @Date: 2019/8/15 9:44
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return RealView实时前端展示类
   */
  private RealView getIndexObject(Date startDate, Date endDate) {
    return isNullTo(
        realDao.findAllByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
  }

  /**
   * 获取跳失率
   *
   * @author: lingjian @Date: 2019/8/15 9:44
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Double
   */
  private Double getFlowPageObject(Date startDate, Date endDate) {
    return isNullToDouble(
        flowPageDao.findByCreateTimeObject(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
  }

  /**
   * 每num周期的数据
   *
   * @author: lingjian @Date: 2019/5/22 16:18
   * @param date 开始时间
   * @param parameter 分类关键词
   * @return List<Number>
   */
  private List<Number> getIndex(Date date, String parameter, int num, int day, int end) {
    List<Number> list = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      if (Contants.VISITOR.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - 1),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getVisitorCount());
      } else if (Contants.VIEW.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - 1),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getViewCount());
      } else if (Contants.REGISTER.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - 1),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getRegisterCount());
      } else if (Contants.INQUIRY.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - 1),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getInquiryCount());
      } else if (Contants.JUMP.equals(parameter)) {
        list.add(
            getFlowPageObject(
                DateTimeUtil.getDayFromNum(date, (num - i) * day - 1),
                DateTimeUtil.getDayFromNum(date, (num - i) * day - end)));
      }
    }
    return list;
  }

  /**
   * 获取每个参数的数据集合
   *
   * @author: lingjian @Date: 2019/5/23 10:03
   * @param date 时间
   * @return List<KeyValue>
   */
  private List<KeyValue> getIndexList(String indexCode, Date date, int num, int day, int end) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(indexCode, getIndex(date, indexCode, num, day, end)));
    return keyValues;
  }
  /**
   * 获取首页整体看板时段分布
   *
   * @author: lingjian @Date: 2019/5/23 10:03
   * @param date 时间
   * @param num 天数
   * @return Map
   */
  @Override
  public Map getIndexOverviewTime(Date date, Integer num, String indexCode) {
    Map<String, List> map = new HashMap<>(16);
    if (Contants.SEVEN.equals(num)) {
      map.put(Contants.ABSCISSA, DateTimeUtil.getWeek(date));
      map.put(Contants.LIST, getIndexList(indexCode, date, Contants.TWELVE, num, num));
    } else if (Contants.THIRTY.equals(num)) {
      map.put(Contants.ABSCISSA, DateTimeUtil.getMonth(date));
      map.put(Contants.LIST, getIndexList(indexCode, date, Contants.TWELVE, num, num));
    } else {
      map.put(Contants.ABSCISSA, DateTimeUtil.getDay(date));
      map.put(Contants.LIST, getIndexList(indexCode, date, Contants.THIRTY, 1, 1));
    }
    return map;
  }

  /**
   * 根据开始时间和结束时间获取实时概况
   *
   * @author: lingjian @Date: 2019/5/22 14:28
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return IndexOverView 首页整体看板前端展示类
   */
  private IndexOverView getIndexOverviewObject(Date startDate, Date endDate, int ynum, int wnum) {
    // 获取今天的值
    RealView today = getIndexObject(startDate, endDate);
    // 获取昨天的值
    RealView yesterday =
        getIndexObject(
            DateTimeUtil.getDayFromNum(startDate, ynum), DateTimeUtil.getDayFromNum(endDate, ynum));
    // 获取上周同期的值
    RealView week =
        getIndexObject(
            DateTimeUtil.getDayFromNum(startDate, wnum), DateTimeUtil.getDayFromNum(endDate, wnum));

    // 获取跳失率
    Double todatJump = getFlowPageObject(startDate, endDate);
    Double yesterdayJump =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(startDate, ynum), DateTimeUtil.getDayFromNum(endDate, ynum));
    Double weekJump =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(startDate, wnum), DateTimeUtil.getDayFromNum(endDate, wnum));

    IndexOverView indexOverView = new IndexOverView();
    // 访客数
    indexOverView.setVisitorCount(today.getVisitorCount());
    indexOverView.setVisitorCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                today.getVisitorCount(), yesterday.getVisitorCount())));
    indexOverView.setVisitorCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(today.getVisitorCount(), week.getVisitorCount())));
    // 浏览量
    indexOverView.setViewCount(today.getViewCount());
    indexOverView.setViewCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(today.getViewCount(), yesterday.getViewCount())));
    indexOverView.setViewCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(today.getViewCount(), week.getViewCount())));
    // 注册量
    indexOverView.setRegisterCount(today.getRegisterCount());
    indexOverView.setRegisterCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                today.getRegisterCount(), yesterday.getRegisterCount())));
    indexOverView.setRegisterCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(today.getRegisterCount(), week.getRegisterCount())));
    // 询盘量
    indexOverView.setInquiryCount(today.getInquiryCount());
    indexOverView.setInquiryCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                today.getInquiryCount(), yesterday.getInquiryCount())));
    indexOverView.setInquiryCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(today.getInquiryCount(), week.getInquiryCount())));
    // 跳失率
    indexOverView.setJumpRate(CustomDoubleSerialize.setDouble(todatJump));
    indexOverView.setJumpRateCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(todatJump, yesterdayJump)));
    indexOverView.setJumpRateCompareWeek(
        CustomDoubleSerialize.setDouble(CalculateUtil.getDifferenceExcept(todatJump, weekJump)));
    return indexOverView;
  }

  /**
   * 根据时间，天数获取实时概况
   *
   * @author: lingjian @Date: 2019/5/22 14:27
   * @param date 时间
   * @param num 天数
   * @return IndexOverView 首页整体看板前端展示类
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
            realDao.findAllByCreateTimeBetween(
                DateTimeUtil.getTimesmorning(), DateTimeUtil.getTimesnight()));
    RealView yesterday =
        isNullTo(
            realDao.findAllByCreateTimeBetween(
                DateTimeUtil.getYesterdaymorning(), DateTimeUtil.getYesterdaynight()));
    RealView week =
        isNullTo(
            realDao.findAllByCreateTimeBetween(
                DateTimeUtil.getWeekmorning(), DateTimeUtil.getWeeknight()));
    // 创建返回前端对象
    RealOverView realOverView = new RealOverView();
    // 访客数，与昨日的比较值，与上周同一日的比较值
    realOverView.setVisitorCount(today.getVisitorCount());
    realOverView.setVisitorCompareYesterday(
        CalculateUtil.getDifferenceExcept(today.getVisitorCount(), yesterday.getVisitorCount()));
    realOverView.setVisitorCompareWeek(
        CalculateUtil.getDifferenceExcept(today.getVisitorCount(), week.getVisitorCount()));
    // 浏览量，与昨日的比较值，与上周同一日的比较值
    realOverView.setViewCount(today.getViewCount());
    realOverView.setViewCompareYesterday(
        CalculateUtil.getDifferenceExcept(today.getViewCount(), yesterday.getViewCount()));
    realOverView.setViewCompareWeek(
        CalculateUtil.getDifferenceExcept(today.getViewCount(), week.getViewCount()));
    // 注册量，与昨日的比较值，与上周同一日的比较值
    realOverView.setRegisterCount(today.getRegisterCount());
    realOverView.setRegisterCompareYesterday(
        CalculateUtil.getDifferenceExcept(today.getRegisterCount(), yesterday.getRegisterCount()));
    realOverView.setRegisterCompareWeek(
        CalculateUtil.getDifferenceExcept(today.getRegisterCount(), week.getRegisterCount()));
    // 询盘量，与昨日的比较值，与上周同一日的比较值
    realOverView.setInquiryCount(today.getInquiryCount());
    realOverView.setInquiryCompareYesterday(
        CalculateUtil.getDifferenceExcept(today.getInquiryCount(), yesterday.getInquiryCount()));
    realOverView.setInquiryCompareWeek(
        CalculateUtil.getDifferenceExcept(today.getInquiryCount(), week.getInquiryCount()));
    // rfq数，与昨日的比较值，与上周同一日的比较值
    realOverView.setRfqCount(today.getRfqCount());
    realOverView.setRfqCompareYesterday(
        CalculateUtil.getDifferenceExcept(today.getRfqCount(), yesterday.getRfqCount()));
    realOverView.setRfqCompareWeek(
        CalculateUtil.getDifferenceExcept(today.getRfqCount(), week.getRfqCount()));
    return realOverView;
  }

  /**
   * 获取一定时间段以内的累加值
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param date 时间
   * @param start 小时开始时间
   * @param end 小时结束时间
   * @return RealView 实时前端展示类
   */
  private RealView getAddByTime(Date date, int start, int end) {
    return isNullTo(
        realDao.findAllByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end)));
  }

  /**
   * 获取24个小时中每一个小时的值
   *
   * @author: lingjian @Date: 2019/5/10 9:21
   * @param date 时间
   * @return List<Integer>
   */
  private List<Integer> getEveryHour(Date date, String parameter) {
    List<Integer> list = new ArrayList<>();
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
   * @author: lingjian @Date: 2019/8/15 9:38
   * @param date 时间
   * @return Map<String, List<Integer>>
   */
  private Map<String, List<Integer>> getRealTrendByDay(Date date) {
    Map<String, List<Integer>> visitorMap = new HashMap<>(16);
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
   * @param date 时间
   * @param indexCode 关键字
   * @return Map<String, List<Integer>>
   */
  private Map<String, List<Integer>> getIndexTrendByDay(Date date, String indexCode) {
    Map<String, List<Integer>> visitorMap = new HashMap<>(16);
    visitorMap.put(indexCode, getEveryHour(date, indexCode));
    return visitorMap;
  }

  /**
   * 获取今日和对比日的实时趋势的值
   *
   * @author: lingjian @Date: 2019/5/9 16:08
   * @param date 时间
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getRealTrend(Date date) {
    Map<String, Map> visitorAllMap = new HashMap<>(16);
    visitorAllMap.put(Contants.ABSCISSA, DateTimeUtil.getHourAbscissa(2));
    visitorAllMap.put(Contants.TODAY, getRealTrendByDay(new Date()));
    visitorAllMap.put(Contants.RATHERDAY, getRealTrendByDay(date));
    return visitorAllMap;
  }

  /**
   * 根据时间，关键字获取实时数据分析时段分布
   *
   * @author: lingjian @Date: 2019/5/23 13:56
   * @param date 时间
   * @param indexCode 关键字
   * @return Map<String, Map>
   */
  @Override
  public Map<String, Map> getIndexTrend(Date date, String indexCode) {
    Map<String, Map> visitorAllMap = new HashMap<>(16);
    visitorAllMap.put(Contants.ABSCISSA, DateTimeUtil.getHourAbscissa(2));
    visitorAllMap.put(Contants.TODAY, getIndexTrendByDay(new Date(), indexCode));
    visitorAllMap.put(Contants.RATHERDAY, getIndexTrendByDay(date, indexCode));
    return visitorAllMap;
  }

  /**
   * 获取首页累计数据
   *
   * @author: lingjian @Date: 2019/5/23 14:23
   * @return IndexGrand 累计数据
   */
  @Override
  public IndexGrand getIndexGrand() {
    IndexGrand grand = new IndexGrand();
    IndexGrand real = realDao.findByCreateTimeBefore(DateTimeUtil.getTimesnight());
    IndexGrand user = userDao.findByCreateTimeBefore(DateTimeUtil.getTimesnight());
    grand.setGrandInquiry(real.getGrandInquiry());
    grand.setGrandRfq(real.getGrandRfq());
    grand.setGrandRegister(real.getGrandRegister());
    grand.setGrandPurchase(user.getGrandPurchase());
    grand.setGrandSupplier(user.getGrandSupplier());
    return grand;
  }

  /**
   * 获取流量来源列表
   *
   * @author: lingjian @Date: 2019/8/20 10:18
   * @return List
   */
  @Override
  public List getSourceType() {
    List<KeyValue> list = new ArrayList<>();
    for (SourceTypeEnum s : SourceTypeEnum.values()) {
      KeyValue keyValue = new KeyValue();
      keyValue.setKey(s.toString());
      keyValue.setValue(s.getName());
      list.add(keyValue);
    }
    return list;
  }

  /**
   * 分页获取当天访客列表集合
   *
   * @author: lingjian @Date: 2019/9/2 10:29
   * @param page 开始条数
   * @param limit 显示条数
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 访客位置
   * @param start 开始时间
   * @param end 结束时间
   * @return List<RealVisitorView>
   */
  private List<RealVisitorView> getRealVisitorViews(
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country,
      Date start,
      Date end) {
    // 获取访客集合
    return webVisitDao
        .listWebVisit(start, end, page, limit, visitType, sourceType, urlPage, country).stream()
        .peek(
            bean -> {
              // 流量来源,流量来源名称
              SourceTypeEnum sourceTypeEnum =
                  urlMatchDataUtilService.getSourceType(bean.getReferer());
              bean.setSourceType(sourceTypeEnum);
              bean.setSourceTypeName(sourceTypeEnum.getName());
              // 访客类型名称
              bean.setTypeName(bean.getType().getName());
            })
        .collect(Collectors.toList());
  }

  /**
   * 获取当天访客列表集合总条目数
   *
   * @author: lingjian @Date: 2019/9/2 10:29
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 访客位置
   * @param start 开始时间
   * @param end 结束时间
   * @return Long
   */
  private Long countRealVisitorViews(
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country,
      Date start,
      Date end) {
    return webVisitDao.countWebVisit(start, end, visitType, sourceType, urlPage, country);
  }

  /**
   * 分页获取当天访客列表记录
   *
   * @author: lingjian @Date: 2019/8/20 13:56
   * @param page 开始条数
   * @param limit 显示条数
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 访客位置
   * @return Map
   */
  @Override
  public Map getRealVisitor(
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country) {
    // 获取当天0点，24点时间
    Date start = DateTimeUtil.getTimesmorning();
    Date end = DateTimeUtil.getTimesnight();

    Map<String, Object> map = new HashMap<>(16);
    map.put(
        "list",
        getRealVisitorViews(page, limit, visitType, sourceType, urlPage, country, start, end));
    // 获取访客集合总条数
    map.put("total", countRealVisitorViews(visitType, sourceType, urlPage, country, start, end));
    return map;
  }

  /**
   * 获取常访问页面列表
   *
   * @author: lingjian @Date: 2019/8/20 17:15
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List
   */
  private List getRealVisitPageList(Date startDate, Date endDate, Integer page, Integer limit) {
    return webVisitDao.getWebVisitUrl(
        DateTimeUtil.getTimesOfDay(startDate, 0),
        DateTimeUtil.getTimesOfDay(endDate, 24),
        page,
        limit);
  }

  /**
   * 获取常访问页面列表的总条数
   *
   * @author: lingjian @Date: 2019/8/20 17:15
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Integer
   */
  private Long countRealVisitPage(Date startDate, Date endDate) {
    return webVisitDao.countWebVisitUrl(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
  }

  /**
   * 分页获取首页常访问页面列表
   *
   * @author: lingjian @Date: 2019/8/20 17:14
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return List
   */
  @Override
  public List getRealVisitPage(Date startDate, Date endDate, Integer page, Integer limit) {
    return getRealVisitPageList(startDate, endDate, page, limit);
  }

  /**
   * 分页获取页面分析页面访问排行列表
   *
   * @author: lingjian @Date: 2019/8/20 17:18
   * @param date 时间
   * @param num 天数类型
   * @param page 开始条目
   * @param limit 结束条目
   * @return Map
   */
  @Override
  public Map getRealVisitPage(Date date, Integer num, Integer page, Integer limit) {
    List list;
    Long count;
    if (num != null) {
      list = getRealVisitPageList(DateTimeUtil.getDayFromNum(date, num), date, page, limit);
      count = countRealVisitPage(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      list = getRealVisitPageList(date, date, page, limit);
      count = countRealVisitPage(date, date);
    }
    Map<String, Object> map = new HashMap<>(16);
    map.put("list", list);
    map.put("total", count);
    return map;
  }

  /**
   * 根据开始时间和结束时间获取搜索关键词排行集合
   *
   * @author: lingjian @Date: 2019/8/21 10:31
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List
   */
  private List getHomeSearchList(Date startDate, Date endDate, Integer page, Integer limit) {
    return searchWordInfoDao.listSearchWord(
        DateTimeUtil.getTimesOfDay(startDate, 0),
        DateTimeUtil.getTimesOfDay(endDate, 24),
        page,
        limit);
  }

  /**
   * 根据开始时间和结束时间获取搜索关键词的总条目数
   *
   * @author: lingjian @Date: 2019/9/2 9:50
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Long
   */
  private Long countHomeSearchList(Date startDate, Date endDate) {
    return searchWordInfoDao.countSearchWord(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
  }

  /**
   * 分页获取首页搜索关键词排行
   *
   * @author: lingjian @Date: 2019/8/21 10:30
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return Map
   */
  @Override
  public Map getHomeSearch(Date startDate, Date endDate, Integer page, Integer limit) {
    Map<String, Object> map = new HashMap<>(16);
    // 搜索关键词列表
    map.put("list", getHomeSearchList(startDate, endDate, page, limit));
    // 搜索关键词列表总条数
    map.put("total", countHomeSearchList(startDate, endDate));
    return map;
  }
}

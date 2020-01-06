package com.shoestp.mains.service.xwt.dataview.impl;

import java.util.*;

import javax.annotation.Resource;

import com.shoestp.mains.constant.dataview.Contants;
import com.shoestp.mains.controllers.xwt.dataview.plat.vo.real.XwtRealVisitorVO;
import com.shoestp.mains.dao.xwt.dataview.dao.XwtViewFlowPageDAO;
import com.shoestp.mains.dao.xwt.dataview.dao.XwtViewRealDAO;
import com.shoestp.mains.dao.xwt.metadata.dao.XwtMetaAccessLogDAO;
import com.shoestp.mains.entitys.xwt.dataview.real.XwtViewReal;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.service.xwt.dataview.XwtViewRealService;
import com.shoestp.mains.utils.dateUtils.CalculateUtil;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.utils.dateUtils.KeyValueViewUtil;
import com.shoestp.mains.views.dataview.utils.KeyValue;

import org.springframework.stereotype.Service;

/**
 * @description: 实时-服务层实现类
 * @author: lingjian
 * @create: 2020/1/3 10:04
 */
@Service
public class XwtViewRealServiceImpl implements XwtViewRealService {

  @Resource private XwtViewRealDAO realDAO;
  @Resource private XwtViewFlowPageDAO flowPageDAO;
  @Resource private XwtMetaAccessLogDAO accessLogDAO;

  /**
   * 判断是否为空处理
   *
   * @author: lingjian @Date: 2020/1/3 10:48
   * @param real XwtViewReal实时表对象
   * @return XwtViewReal 实时表对象
   */
  private XwtViewReal isNullTo(XwtViewReal real) {
    if (real.getVisitorCount() == null) {
      real.setVisitorCount(0L);
      real.setVisitorCountPc(0L);
      real.setVisitorCountWap(0L);
      real.setPageViewsCount(0L);
      real.setRegisterCount(0L);
    }
    return real;
  }

  /**
   * 获取指定时间段以内的累加值
   *
   * @author: lingjian @Date: 2020/1/3 10:49
   * @param date 时间
   * @param start 小时开始时间
   * @param end 小时结束时间
   * @return XwtViewReal 实时表对象
   */
  private XwtViewReal getAddByTime(Date date, int start, int end) {
    return isNullTo(
        realDAO.findByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end)));
  }

  /**
   * 获取0-24时间段以内的累加值
   *
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return XwtViewReal 实时表对象
   */
  private XwtViewReal getIndexObject(Date startDate, Date endDate) {
    return isNullTo(
        realDAO.findByCreateTimeBetween(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
  }

  /**
   * 获取24个小时中每一个小时的值
   *
   * @author: lingjian @Date: 2020/1/3 10:49
   * @param date 时间
   * @return List<Integer>
   */
  private List<Long> getEveryHour(Date date, String parameter) {
    List<Long> list = new ArrayList<>();
    for (int i = 0; i < Contants.TWELVE; i++) {
      if (Contants.VISITOR.equals(parameter)) {
        list.add(getAddByTime(date, i * 2, i * 2 + 2).getVisitorCount());
      } else if (Contants.VIEW.equals(parameter)) {
        list.add(getAddByTime(date, i * 2, i * 2 + 2).getPageViewsCount());
      } else if (Contants.REGISTER.equals(parameter)) {
        list.add(getAddByTime(date, i * 2, i * 2 + 2).getRegisterCount());
      }
    }
    return list;
  }

  /**
   * 根据关键字获取当前时间的实时趋势的值
   *
   * @author: lingjian @Date: 2020/1/3 10:49
   * @param date 时间
   * @param indexCode 关键字
   * @return Map<String, List<Integer>>
   */
  private Map<String, List<Long>> getIndexTrendByDay(Date date, String indexCode) {
    Map<String, List<Long>> visitorMap = new HashMap<>(16);
    visitorMap.put(indexCode, getEveryHour(date, indexCode));
    return visitorMap;
  }

  /**
   * 根据时间，关键字获取实时数据分析时段分布
   *
   * @author: lingjian @Date: 2020/1/3 10:49
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
   * 获取跳失率
   *
   * @author: lingjian @Date: 2020/1/3 13:34
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Double 跳失率
   */
  private Double getFlowPageObject(Date startDate, Date endDate) {
    return flowPageDAO.findByCreateTimeBetween(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
  }

  /**
   * 根据开始时间和结束时间获取实时概况
   *
   * @author: lingjian @Date: 2020/1/3 10:58
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param ynum 天数
   * @param wnum 周数
   * @return XwtViewReal 实时表对象
   */
  private XwtViewReal getIndexOverviewObject(Date startDate, Date endDate, int ynum, int wnum) {
    // 获取今天的值
    XwtViewReal real = getIndexObject(startDate, endDate);
    // 获取昨天的值
    XwtViewReal yesterday =
        getIndexObject(
            DateTimeUtil.getDayFromNum(startDate, ynum), DateTimeUtil.getDayFromNum(endDate, ynum));
    // 获取上周同期的值
    XwtViewReal week =
        getIndexObject(
            DateTimeUtil.getDayFromNum(startDate, wnum), DateTimeUtil.getDayFromNum(endDate, wnum));

    // 跳失率
    Double todatJump = getFlowPageObject(startDate, endDate);
    Double yesterdayJump =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(startDate, ynum), DateTimeUtil.getDayFromNum(endDate, ynum));
    Double weekJump =
        getFlowPageObject(
            DateTimeUtil.getDayFromNum(startDate, wnum), DateTimeUtil.getDayFromNum(endDate, wnum));

    // 访客数
    real.setVisitorCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                real.getVisitorCount(), yesterday.getVisitorCount())));

    real.setVisitorCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(real.getVisitorCount(), week.getVisitorCount())));

    // 浏览量
    real.setViewCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                real.getPageViewsCount(), yesterday.getPageViewsCount())));
    real.setViewCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(real.getPageViewsCount(), week.getPageViewsCount())));

    // 注册量
    real.setRegisterCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(
                real.getRegisterCount(), yesterday.getRegisterCount())));
    real.setRegisterCompareWeek(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(real.getRegisterCount(), week.getRegisterCount())));

    // 跳失率
    real.setJumpRate(CustomDoubleSerialize.setDouble(todatJump));
    real.setJumpRateCompareYesterday(
        CustomDoubleSerialize.setDouble(
            CalculateUtil.getDifferenceExcept(todatJump, yesterdayJump)));
    real.setJumpRateCompareWeek(
        CustomDoubleSerialize.setDouble(CalculateUtil.getDifferenceExcept(todatJump, weekJump)));

    return real;
  }

  /**
   * 获取实时概况的值
   *
   * @return XwtViewReal 实时表对象
   */
  @Override
  public XwtViewReal getRealOverview() {
    return getIndexOverviewObject(new Date(), new Date(), 1, 7);
  }

  /**
   * 获取首页整体看板概况
   *
   * @author: lingjian @Date: 2020/1/3 10:48
   * @param date 时间
   * @param num 天数
   * @return XwtViewReal 实时表对象
   */
  @Override
  public XwtViewReal getIndexOverview(Date date, Integer num) {
    if (num != null) {
      return getIndexOverviewObject(DateTimeUtil.getDayFromNum(date, num), date, num, 365);
    } else {
      return getIndexOverviewObject(date, date, 1, 7);
    }
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
                .getPageViewsCount());
      } else if (Contants.REGISTER.equals(parameter)) {
        list.add(
            getIndexObject(
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - 1),
                    DateTimeUtil.getDayFromNum(date, (num - i) * day - end))
                .getRegisterCount());
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
   * @author: lingjian @Date: 2020/1/3 11:10
   * @param date 时间
   * @param num 天数
   * @param indexCode 关键字
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
   * 获取当前时间的实时趋势的值
   *
   * @author: lingjian @Date: 2020/1/3 13:48
   * @param date 时间
   * @return Map<String, List<Integer>>
   */
  private Map<String, List<Long>> getRealTrendByDay(Date date) {
    Map<String, List<Long>> visitorMap = new HashMap<>(16);
    visitorMap.put(Contants.VISITOR, getEveryHour(date, Contants.VISITOR));
    visitorMap.put(Contants.VIEW, getEveryHour(date, Contants.VIEW));
    visitorMap.put(Contants.REGISTER, getEveryHour(date, Contants.REGISTER));
    visitorMap.put(Contants.INQUIRY, getEveryHour(date, Contants.INQUIRY));
    return visitorMap;
  }

  /**
   * 获取今日和对比日的实时趋势的值
   *
   * @author: lingjian @Date: 2020/1/3 13:47
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
   * 获取流量来源列表
   *
   * @author: lingjian @Date: 2020/1/3 13:50
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
  private List<XwtRealVisitorVO> getRealVisitorViews(
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country,
      Date start,
      Date end) {
    // 获取访客集合
    List<XwtRealVisitorVO> list =
        accessLogDAO.listAccessLogs(
            start, end, page, limit, visitType, sourceType, urlPage, country);
    list.forEach(
        bean -> {
          bean.setSourceTypeName(bean.getSourceType().getName());
          bean.setTypeName(bean.getType().getDescription());
        });
    return list;
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
    return accessLogDAO.countAccessLogs(start, end, visitType, sourceType, urlPage, country);
  }

  /**
   * 分页获取当天访客列表记录
   *
   * @author: lingjian @Date: 2020/1/3 13:53
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
   * @author: lingjian @Date: 2020/1/3 14:13
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return List
   */
  private List getRealVisitPageList(Date startDate, Date endDate, Integer page, Integer limit) {
    return accessLogDAO.listAccessLogsUrl(
        DateTimeUtil.getTimesOfDay(startDate, 0),
        DateTimeUtil.getTimesOfDay(endDate, 24),
        page,
        limit);
  }

  /**
   * 分页获取首页常访问页面列表
   *
   * @author: lingjian @Date: 2020/1/3 14:13
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
   * 获取常访问页面列表的总条数
   *
   * @author: lingjian @Date: 2019/8/20 17:15
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @return Integer
   */
  private Long countRealVisitPage(Date startDate, Date endDate) {
    return accessLogDAO.countAccessLogsUrl(
        DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24));
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
}

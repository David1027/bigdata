package com.shoestp.mains.service.xwt.dataview.plat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.entitys.xwt.dataview.plat.real.XwtViewReal;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

/**
 * @description: 实时-服务层接口
 * @author: lingjian
 * @create: 2020/1/3 10:02
 */
public interface XwtViewRealService {

  /**
   * 根据时间，关键字获取实时数据分析时段分布
   *
   * @author: lingjian @Date: 2020/1/3 10:41
   * @param date 时间
   * @param indexCode 关键字
   * @return Map<String, Map>
   */
  Map<String, Map> getIndexTrend(Date date, String indexCode);

  /**
   * 获取实时概况的值
   *
   * @author: lingjian @Date: 2019/5/9 15:31
   * @return XwtViewReal 实时表对象
   */
  XwtViewReal getRealOverview();

  /**
   * 获取首页整体看板概况
   *
   * @author: lingjian @Date: 2020/1/3 10:41
   * @param date 时间
   * @param num 天数
   * @return XwtViewReal 实时表对象
   */
  XwtViewReal getIndexOverview(Date date, Integer num);

  /**
   * 获取首页整体看板时段分布
   *
   * @author: lingjian @Date: 2020/1/3 11:09
   * @param date 时间
   * @param num 天数
   * @param indexCode 关键字
   * @return Map
   */
  Map getIndexOverviewTime(Date date, Integer num, String indexCode);

  /**
   * 获取实时趋势的值
   *
   * @author: lingjian @Date: 2020/1/3 13:47
   * @param date 时间
   * @return Map<String,Map>
   */
  Map<String, Map> getRealTrend(Date date);

  /**
   * 获取流量来源列表
   *
   * @author: lingjian @Date: 2020/1/3 13:49
   * @return List
   */
  List getSourceType();

  /**
   * 获取实时访客列表
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
  Map getRealVisitor(
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country);

  /**
   * 获取首页常访问页面列表
   *
   * @author: lingjian @Date: 2020/1/3 14:20
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return List
   */
  List getRealVisitPage(Date startDate, Date endDate, Integer page, Integer limit);

  /**
   * 获取页面分析页面访问排行列表
   *
   * @author: lingjian @Date: 2020/1/3 14:24
   * @param date 时间
   * @param num 天数类型
   * @param page 开始条目
   * @param limit 结束条目
   * @return Map
   */
  Map getRealVisitPage(Date date, Integer num, Integer page, Integer limit);
}

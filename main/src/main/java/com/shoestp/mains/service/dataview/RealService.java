package com.shoestp.mains.service.dataview;

import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.views.dataview.real.IndexGrand;
import com.shoestp.mains.views.dataview.real.IndexOverView;
import com.shoestp.mains.views.dataview.real.RealOverView;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 实时-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:28
 */
public interface RealService {

  /**
   * 根据时间，关键字获取实时数据分析时段分布
   *
   * @author: lingjian @Date: 2019/5/23 13:56
   * @param date
   * @param indexCode
   * @return
   */
  Map<String, Map> getIndexTrend(Date date, String indexCode);

  /**
   * 获取首页累计数据
   *
   * @author: lingjian @Date: 2019/5/23 14:22
   * @return
   */
  IndexGrand getIndexGrand();

  /**
   * 获取首页整体看板概况
   *
   * @author: lingjian @Date: 2019/5/22 14:27
   * @param date
   * @param num
   * @return IndexOverView对象
   */
  IndexOverView getIndexOverview(Date date, Integer num);

  /**
   * 获取首页整体看板时段分布
   *
   * @param date
   * @param num
   * @param indexCode
   * @return
   */
  Map getIndexOverviewTime(Date date, Integer num, String indexCode);

  /**
   * 获取实时概况的值
   *
   * @author: lingjian @Date: 2019/5/9 15:31
   * @return DataViewRealView对象
   */
  RealOverView getRealOverview();

  /**
   * 获取实时趋势的值
   *
   * @author: lingjian @Date: 2019/5/9 16:08
   * @param date
   * @return Map<String,Map>
   */
  Map<String, Map> getRealTrend(Date date);

  /**
   * 获取流量来源列表
   *
   * @author: lingjian @Date: 2019/8/20 10:17
   * @return List
   */
  List getSourceType();

  /**
   * 获取实时访客列表
   *
   * @author: lingjian @Date: 2019/8/20 13:55
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
   * @author: lingjian @Date: 2019/8/20 17:12
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return List
   */
  List getRealVisitPage(Date startDate, Date endDate, Integer page, Integer limit);

  /**
   * 获取首页搜索关键词排行
   *
   * @author: lingjian @Date: 2019/8/21 10:30
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return Map
   */
  Map getHomeSearch(Date startDate, Date endDate, Integer page, Integer limit);

  /**
   * 获取页面分析页面访问排行列表
   *
   * @author: lingjian @Date: 2019/8/20 17:12
   * @param date 时间
   * @param num 天数类型
   * @param page 开始条目
   * @param limit 结束条目
   * @return Map
   */
  Map getRealVisitPage(Date date, Integer num, Integer page, Integer limit);
}

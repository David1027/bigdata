package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.views.DataView.real.IndexGrand;
import com.shoestp.mains.views.DataView.real.IndexOverView;
import com.shoestp.mains.views.DataView.real.RealOverView;
import com.shoestp.mains.views.DataView.real.RealView;

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
}

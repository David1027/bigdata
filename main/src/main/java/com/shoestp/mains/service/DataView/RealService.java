package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.Map;

import com.shoestp.mains.views.DataView.real.RealView;

/**
 * @description: 实时-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:28
 */
public interface RealService {
  /**
   * 获取实时概况的值
   *
   * @author: lingjian @Date: 2019/5/9 15:31
   * @return DataViewRealView对象
   */
  RealView getRealOverview();

  /**
   * 获取实时趋势的值
   *
   * @author: lingjian @Date: 2019/5/9 16:08
   * @param date
   * @return Map<String,Map>
   */
  Map<String,Map> getRealTrend(Date date);
}

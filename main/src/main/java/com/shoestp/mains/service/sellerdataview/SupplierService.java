package com.shoestp.mains.service.sellerdataview;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.views.sellerdataview.supplier.OverviewView;

/**
 * @description: 商家后台供应商-服务层接口
 * @author: lingjian
 * @create: 2019/5/24 11:46
 */
public interface SupplierService {
  /**
   * 获取首页概况
   *
   * @author: lingjian @Date: 2019/5/27 10:57
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  OverviewView getIndexOverview(Date startDate, Date endDate, Integer supplierid);

  /**
   * 获取数据趋势
   *
   * @author: lingjian @Date: 2019/5/27 11:27
   * @param num
   * @param supplierid
   * @param type
   * @return
   */
  Map getIndexTrend(Integer num, Integer supplierid, String type);

  /**
   * 获取国家用户画像
   *
   * @author: lingjian @Date: 2019/5/27 13:31
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  Map getIndexCountry(Date startDate, Date endDate, Integer supplierid);
}

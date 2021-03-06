package com.shoestp.mains.service.sellerdataview;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.views.dataview.utils.Page;

/**
 * @description: 商家后台商品-服务层接口
 * @author: lingjian
 * @create: 2019/5/24 11:46
 */
public interface ProductService {
  /**
   * 获取实时排行
   *
   * @param date
   * @param datetype
   * @param supplierid
   * @param type
   * @param start
   * @param limit
   * @return
   */
  Page getRealRank(
      Date date,
      String country,
      String datetype,
      Integer supplierid,
      String type,
      Integer start,
      Integer limit);

  /**
   * 获取首页商品排行
   *
   * @author: lingjian @Date: 2019/5/27 13:56
   * @param startDate
   * @param endDate
   * @param supplierid
   * @param type
   * @return
   */
  List getIndexRank(Date startDate, Date endDate, Integer supplierid, String type);

  /**
   * 获取市场分析
   *
   * @author: lingjian @Date: 2019/5/27 14:47
   * @param country
   * @return
   */
  List getIndexMarket(String country);
}

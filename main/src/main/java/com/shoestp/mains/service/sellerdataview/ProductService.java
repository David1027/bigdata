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
      Date date, String datetype, Integer supplierid, String type, Integer start, Integer limit);
}

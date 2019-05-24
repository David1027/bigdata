package com.shoestp.mains.service.sellerdataview;

import java.util.Date;

import com.shoestp.mains.views.dataview.utils.Page;

/**
 * @description: 商家后台用户-服务层接口
 * @author: lingjian
 * @create: 2019/5/24 11:46
 */
public interface SellerUserService {

  /**
   * 获取实时访客，用户分析，用户列表
   *
   * @author: lingjian @Date: 2019/5/24 15:50
   * @param date
   * @param country
   * @param province
   * @param supplierid
   * @param type
   * @param keywords
   * @param start
   * @param limit
   * @return
   */
  Page getRealVisitor(
      Date date,
      String country,
      String province,
      Integer supplierid,
      String type,
      String keywords,
      Integer start,
      Integer limit);
}

package com.shoestp.mains.service.sellerdataview.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.constant.sellerdataview.SellerContants;
import com.shoestp.mains.dao.sellerdataview.product.ProductDao;
import com.shoestp.mains.dao.sellerdataview.supplier.SupplierDao;
import com.shoestp.mains.service.sellerdataview.ProductService;
import com.shoestp.mains.service.sellerdataview.SupplierService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.dataview.utils.Page;
import com.shoestp.mains.views.sellerdataview.product.MarketView;
import com.shoestp.mains.views.sellerdataview.product.RealRankView;

import org.springframework.stereotype.Service;

/**
 * @description: 商家后台商品-服务层实现类
 * @author: lingjian
 * @create: 2019/5/24 11:48
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Resource private ProductDao productDao;

  /**
   * 获取实时排行对象列表
   *
   * @param date
   * @param datetype
   * @param supplierid
   * @param type
   * @param start
   * @param limit
   * @return
   */
  public List getRealRankList(
      Date date,
      String country,
      String datetype,
      Integer supplierid,
      String type,
      Integer start,
      Integer limit) {
    return productDao
        .findProduct(
            DateTimeUtil.getTimesOfDay(date),
            DateTimeUtil.getTimesOfDay(date, 24),
            country,
            datetype,
            supplierid,
            type,
            start,
            limit)
        .stream()
        .map(
            bean -> {
              RealRankView realRankView = new RealRankView();
              realRankView.setSupplierId(bean.getSupplierId());
              realRankView.setCountry(bean.getCountry());
              realRankView.setProductName(bean.getProductName());
              realRankView.setProductImg(bean.getProductImg());
              realRankView.setProductUrl(bean.getProductUrl());
              realRankView.setViewCount(bean.getViewCount());
              realRankView.setInquiryCount(bean.getInquiryCount());
              realRankView.setVisitorCount(bean.getVisitorCount());
              realRankView.setCollectCount(bean.getCollectCount());
              return realRankView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 获取实时排行
   *
   * @author: lingjian @Date: 2019/5/27 9:51
   * @param date
   * @param datetype
   * @param supplierid
   * @param type
   * @param start
   * @param limit
   * @return
   */
  @Override
  public Page getRealRank(
      Date date,
      String country,
      String datetype,
      Integer supplierid,
      String type,
      Integer start,
      Integer limit) {
    Page page = new Page();
    if (date != null) {
      page.setPage(getRealRankList(date, country, datetype, supplierid, type, start, limit));
      page.setTotalCount(
          getRealRankList(
                  date, country, datetype, supplierid, type, SellerContants.NEGATIVEONE, limit)
              .size());
    } else {
      page.setPage(getRealRankList(new Date(), country, datetype, supplierid, type, start, limit));
      page.setTotalCount(
          getRealRankList(
                  new Date(),
                  country,
                  datetype,
                  supplierid,
                  type,
                  SellerContants.NEGATIVEONE,
                  limit)
              .size());
    }

    return page;
  }

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
  @Override
  public List getIndexRank(Date startDate, Date endDate, Integer supplierid, String type) {
    return productDao.findProductRank(
        DateTimeUtil.getTimesOfDay(startDate),
        DateTimeUtil.getTimesOfDay(endDate, 24),
        supplierid,
        type);
  }

  /**
   * 获取市场分析
   *
   * @author: lingjian @Date: 2019/5/27 14:44
   * @param country
   * @return
   */
  @Override
  public List getIndexMarket(String country) {
    return productDao.findProductMarket(new Date(), country);
  }
}

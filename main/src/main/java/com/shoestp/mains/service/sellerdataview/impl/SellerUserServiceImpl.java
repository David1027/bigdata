package com.shoestp.mains.service.sellerdataview.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.constant.sellerdataview.SellerContants;
import com.shoestp.mains.dao.sellerdataview.user.SellerUserDao;
import com.shoestp.mains.service.sellerdataview.SellerUserService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.dataview.utils.Page;
import com.shoestp.mains.views.sellerdataview.user.RealVisitorView;

import org.springframework.stereotype.Service;

/**
 * @description: 商家后台用户-服务层实现类
 * @author: lingjian
 * @create: 2019/5/24 11:48
 */
@Service
public class SellerUserServiceImpl implements SellerUserService {

  @Resource private SellerUserDao sellerUserDao;

  /**
   * 根据条件返回用户表的集合
   *
   * @author: lingjian @Date: 2019/5/24 15:49
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
  public List getRealVisitorList(
      Date date,
      String country,
      String province,
      Integer supplierid,
      String type,
      String keywords,
      Integer start,
      Integer limit) {
    return sellerUserDao
        .findUser(
            DateTimeUtil.getTimesOfDay(date, 0),
            DateTimeUtil.getTimesOfDay(date, 24),
            supplierid,
            country,
            province,
            type,
            keywords,
            start,
            limit)
        .stream()
        .map(
            bean -> {
              RealVisitorView view = new RealVisitorView();
              view.setSupplierId(bean.getSupplierId());
              view.setCountry(bean.getCountry());
              view.setProvince(bean.getProvince());
              view.setVisitorName(bean.getVisitorName());
              view.setPageCount(bean.getPageCount());
              view.setInquiryCount(bean.getInquiryCount());
              view.setKeyWords(bean.getKeyWords());
              view.setFacebook(bean.getFacebook());
              view.setGoogle(bean.getGoogle());
              view.setLinkedin(bean.getLinkedin());
              view.setTwitter(bean.getTwitter());
              return view;
            })
        .collect(Collectors.toList());
  }

  /**
   * 获取实时访客，用户分析，用户列表
   *
   * @author: lingjian @Date: 2019/5/24 15:49
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
  @Override
  public Page getRealVisitor(
      Date date,
      String country,
      String province,
      Integer supplierid,
      String type,
      String keywords,
      Integer start,
      Integer limit) {
    Page page = new Page();
    if (date != null) {
      page.setPage(
          getRealVisitorList(date, country, province, supplierid, type, keywords, start, limit));
      page.setTotalCount(
          getRealVisitorList(
                  date,
                  country,
                  province,
                  supplierid,
                  type,
                  keywords,
                  SellerContants.NEGATIVEONE,
                  limit)
              .size());
    } else {
      page.setPage(
          getRealVisitorList(
              new Date(), country, province, supplierid, type, keywords, start, limit));
      page.setTotalCount(
          getRealVisitorList(
                  new Date(),
                  country,
                  province,
                  supplierid,
                  type,
                  keywords,
                  SellerContants.NEGATIVEONE,
                  limit)
              .size());
    }
    return page;
  }
}

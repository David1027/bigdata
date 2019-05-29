package com.shoestp.mains.dao.sellerdataview.product;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.constant.sellerdataview.SellerContants;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.sellerdataview.product.QSellerDataViewProduct;
import com.shoestp.mains.entitys.sellerdataview.product.SellerDataViewProduct;
import com.shoestp.mains.repositorys.sellerdataview.product.ProductRepository;
import com.shoestp.mains.views.sellerdataview.product.IndexRankView;
import com.shoestp.mains.views.sellerdataview.product.MarketView;

/**
 * @description: 商家后台商品类数据层
 * @author: lingjian
 * @create: 2019/5/24 11:35
 */
@Repository
public class ProductDao extends BaseDao<SellerDataViewProduct> {

  /**
   * 获取产品对象集合-实时
   *
   * @param startDate
   * @param endDate
   * @param datetype
   * @param supplierid
   * @param type
   * @param start
   * @param limit
   * @return
   */
  public List<SellerDataViewProduct> findProduct(
      Date startDate,
      Date endDate,
      String country,
      String datetype,
      Integer supplierid,
      String type,
      Integer start,
      Integer limit) {
    QSellerDataViewProduct qSellerDataViewProduct = QSellerDataViewProduct.sellerDataViewProduct;
    JPAQuery query =
        getQuery()
            .select(qSellerDataViewProduct)
            .from(qSellerDataViewProduct)
            .where(qSellerDataViewProduct.supplierId.eq(supplierid));
    if (country != null) {
      query.where(qSellerDataViewProduct.country.eq(country));
    }
    if (SellerContants.TYPE.equals(datetype)) {
      query.where(qSellerDataViewProduct.createTime.before(startDate));
    } else {
      query.where(qSellerDataViewProduct.createTime.between(startDate, endDate));
    }
    if (!SellerContants.NEGATIVEONE.equals(start)) {
      query.offset(start).limit(limit);
    }
    if (SellerContants.VIEW.equals(type)) {
      query.orderBy(qSellerDataViewProduct.viewCount.desc());
    }
    if (SellerContants.INQUIRY.equals(type)) {
      query.orderBy(qSellerDataViewProduct.inquiryCount.desc());
    }
    if (SellerContants.VISITOR.equals(type)) {
      query.orderBy(qSellerDataViewProduct.visitorCount.desc());
    }
    if (SellerContants.COLLECT.equals(type)) {
      query.orderBy(qSellerDataViewProduct.collectCount.desc());
    }
    return query.fetchResults().getResults();
  }

  /**
   * 获取商品对象集合-首页
   *
   * @author: lingjian @Date: 2019/5/27 13:56
   * @param startDate
   * @param endDate
   * @param supplierid
   * @param type
   * @return
   */
  public List findProductRank(Date startDate, Date endDate, Integer supplierid, String type) {
    QSellerDataViewProduct qSellerDataViewProduct = QSellerDataViewProduct.sellerDataViewProduct;
    JPAQuery query =
        getQuery()
            .select(
                Projections.bean(
                    IndexRankView.class,
                    qSellerDataViewProduct.supplierId.as("supplierId"),
                    qSellerDataViewProduct.productName.as("productName"),
                    qSellerDataViewProduct.productImg.as("productImg"),
                    qSellerDataViewProduct.productUrl.as("productUrl"),
                    qSellerDataViewProduct.inquiryCount.sum().as("inquiryCount"),
                    qSellerDataViewProduct.viewCount.sum().as("viewCount")))
            .from(qSellerDataViewProduct)
            .where(qSellerDataViewProduct.supplierId.eq(supplierid))
            .where(qSellerDataViewProduct.createTime.between(startDate, endDate))
            .groupBy(qSellerDataViewProduct.productName)
            .limit(10);
    if (SellerContants.VIEW.equals(type)) {
      query.orderBy(qSellerDataViewProduct.viewCount.sum().desc());
    }
    if (SellerContants.INQUIRY.equals(type)) {
      query.orderBy(qSellerDataViewProduct.inquiryCount.sum().desc());
    }
    return query.fetchResults().getResults();
  }

  /**
   * 根据国家获取市场指数对象
   *
   * @author: lingjian @Date: 2019/5/27 14:44
   * @param date
   * @param country
   * @return
   */
  public List<SellerDataViewProduct> findProductMarket(Date date, String country) {
    QSellerDataViewProduct qSellerDataViewProduct = QSellerDataViewProduct.sellerDataViewProduct;
    JPAQuery query =
        getQuery()
            .select(
                Projections.bean(
                    MarketView.class,
                    qSellerDataViewProduct.country.as("country"),
                    qSellerDataViewProduct.productName.as("productName"),
                    qSellerDataViewProduct.productUrl.as("productUrl"),
                    qSellerDataViewProduct.productImg.as("productImg"),
                    qSellerDataViewProduct.marketCount.sum().as("marketCount")))
            .from(qSellerDataViewProduct)
            .where(qSellerDataViewProduct.createTime.before(date))
            .groupBy(qSellerDataViewProduct.productName)
            .orderBy(qSellerDataViewProduct.marketCount.sum().desc())
            .limit(10);
    if (country != null) {
      query.where(qSellerDataViewProduct.country.eq(country));
    }
    return query.fetchResults().getResults();
  }

  @Resource private ProductRepository productRepository;

  @Override
  public SellerDataViewProduct find(SellerDataViewProduct sellerDataViewProduct) {
    return null;
  }

  @Override
  public SellerDataViewProduct findById(Integer id) {
    return null;
  }

  @Override
  public int update(SellerDataViewProduct sellerDataViewProduct) {
    return 0;
  }

  @Override
  public int updateByList(List<SellerDataViewProduct> list) {
    return 0;
  }

  @Override
  public int remove(SellerDataViewProduct sellerDataViewProduct) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }

  public void save(SellerDataViewProduct pdt) {
    productRepository.save(pdt);
  }

  public Optional<SellerDataViewProduct> findTopByOrderByCreateTimeDesc() {
    return productRepository.findTopByOrderByCreateTimeDesc();
  }
}

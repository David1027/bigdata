package com.shoestp.mains.dao.sellerdataview.product;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.constant.sellerdataview.SellerContants;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.sellerdataview.product.QSellerDataViewProduct;
import com.shoestp.mains.entitys.sellerdataview.product.SellerDataViewProduct;
import com.shoestp.mains.entitys.sellerdataview.user.QSellerDataViewUser;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;
import com.shoestp.mains.repositorys.sellerdataview.product.ProductRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 商家后台商品类数据层
 * @author: lingjian
 * @create: 2019/5/24 11:35
 */
@Repository
public class ProductDao extends BaseDao<SellerDataViewProduct> {

  /**
   * 获取产品对象集合
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
}

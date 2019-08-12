package com.shoestp.mains.dao.sellerdataview.supplier;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.sellerdataview.supplier.QSellerDataViewSupplier;
import com.shoestp.mains.entitys.sellerdataview.supplier.SellerDataViewSupplier;
import com.shoestp.mains.repositorys.sellerdataview.supplier.SupplierRepository;
import com.shoestp.mains.views.sellerdataview.supplier.CountryView;
import com.shoestp.mains.views.sellerdataview.supplier.OverviewView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: 商家后台供应商总数据类数据层
 * @author: lingjian
 * @create: 2019/5/24 11:35
 */
@Repository
public class SupplierDao extends BaseDao<SellerDataViewSupplier> {

  /**
   * 获取首页概况
   *
   * @author: lingjian @Date: 2019/5/27 11:00
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  public OverviewView findSupplier(Date startDate, Date endDate, Integer supplierid) {
    QSellerDataViewSupplier qSellerDataViewSupplier =
        QSellerDataViewSupplier.sellerDataViewSupplier;
    return getQuery()
        .select(
            Projections.bean(
                OverviewView.class,
                qSellerDataViewSupplier.supplierId.as("supplierId"),
                qSellerDataViewSupplier.visitorCount.sum().as("visitorCount"),
                qSellerDataViewSupplier.viewCount.sum().as("viewCount"),
                qSellerDataViewSupplier.inquiryCount.sum().as("inquiryCount"),
                qSellerDataViewSupplier.inquiryNumber.sum().as("inquiryNumber")))
        .from(qSellerDataViewSupplier)
        .where(qSellerDataViewSupplier.supplierId.eq(supplierid))
        .where(qSellerDataViewSupplier.createTime.between(startDate, endDate))
        .fetchFirst();
  }

  /**
   * 获取国家-访客数集合
   *
   * @author: lingjian @Date: 2019/5/27 13:33
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  public List<CountryView> findCountry(Date startDate, Date endDate, Integer supplierid) {
    QSellerDataViewSupplier qSellerDataViewSupplier =
        QSellerDataViewSupplier.sellerDataViewSupplier;
    return getQuery()
        .select(
            Projections.bean(
                CountryView.class,
                qSellerDataViewSupplier.country.as("country"),
                qSellerDataViewSupplier.visitorCount.sum().as("visitorCount")))
        .from(qSellerDataViewSupplier)
        .where(qSellerDataViewSupplier.supplierId.eq(supplierid))
        .where(qSellerDataViewSupplier.createTime.between(startDate, endDate))
        .orderBy(qSellerDataViewSupplier.visitorCount.desc())
        .limit(10)
        .groupBy(qSellerDataViewSupplier.country)
        .fetchResults()
        .getResults();
  }

  @Resource private SupplierRepository supplierRepository;

  public void save(SellerDataViewSupplier sup) {
    supplierRepository.save(sup);
  }

  public void saveAll(List<SellerDataViewSupplier> list) {
    supplierRepository.saveAll(list);
  }

  public Optional<SellerDataViewSupplier> findTopByOrderByCreateTimeDesc() {
    return supplierRepository.findTopByOrderByCreateTimeDesc();
  }
}

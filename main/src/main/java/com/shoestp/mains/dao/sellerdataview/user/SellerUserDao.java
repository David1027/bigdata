package com.shoestp.mains.dao.sellerdataview.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.constant.sellerdataview.SellerContants;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.sellerdataview.user.QSellerDataViewUser;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;
import com.shoestp.mains.repositorys.sellerdataview.user.SellerUserRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 商家后台用户数据层
 * @author: lingjian
 * @create: 2019/5/24 11:35
 */
@Repository
public class SellerUserDao extends BaseDao<SellerDataViewUser> {

  /**
   * 根据条件返回用户表的集合
   *
   * @author: lingjian @Date: 2019/5/24 15:50
   * @param startDate
   * @param endDate
   * @param supplierid
   * @param country
   * @param province
   * @param type
   * @param keywords
   * @param start
   * @param limit
   * @return
   */
  public List<SellerDataViewUser> findUser(
      Date startDate,
      Date endDate,
      Integer supplierid,
      String country,
      String province,
      String type,
      String keywords,
      Integer start,
      Integer limit) {
    QSellerDataViewUser qSellerDataViewUser = QSellerDataViewUser.sellerDataViewUser;
    JPAQuery query =
        getQuery()
            .select(qSellerDataViewUser)
            .from(qSellerDataViewUser)
            .where(qSellerDataViewUser.supplierId.eq(supplierid));
    if (country != null) {
      query.where(qSellerDataViewUser.country.eq(country));
    }
    if (province != null) {
      query.where(qSellerDataViewUser.province.eq(province));
    }
    if (keywords != null) {
      query.where(qSellerDataViewUser.keyWords.like(keywords));
    }
    if (SellerContants.TYPE.equals(type)) {
      query.where(qSellerDataViewUser.createTime.before(startDate));
    } else {
      query.where(qSellerDataViewUser.createTime.between(startDate, endDate));
    }
    if (!SellerContants.NEGATIVEONE.equals(start)) {
      query.offset(start).limit(limit);
    }
    return query.fetchResults().getResults();
  }

  @Resource private SellerUserRepository sellerUserRepository;

  @Override
  public SellerDataViewUser find(SellerDataViewUser sellerDataViewUser) {
    return null;
  }

  @Override
  public SellerDataViewUser findById(Integer id) {
    return null;
  }

  @Override
  public int update(SellerDataViewUser sellerDataViewUser) {
    return 0;
  }

  @Override
  public int updateByList(List<SellerDataViewUser> list) {
    return 0;
  }

  @Override
  public int remove(SellerDataViewUser sellerDataViewUser) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

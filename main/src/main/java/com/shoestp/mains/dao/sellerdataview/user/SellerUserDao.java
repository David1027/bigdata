package com.shoestp.mains.dao.sellerdataview.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoestp.mains.constant.sellerdataview.SellerContants;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.sellerdataview.user.QSellerDataViewUser;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;
import com.shoestp.mains.repositorys.sellerdataview.user.SellerUserRepository;
import com.shoestp.mains.views.dataview.metadata.Data;

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
  public List<Tuple> findUser(
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
            .select(
                qSellerDataViewUser.supplierId,
                qSellerDataViewUser.country,
                qSellerDataViewUser.province,
                qSellerDataViewUser.visitorName,
                qSellerDataViewUser.pageCount.sum(),
                qSellerDataViewUser.inquiryCount.sum(),
                qSellerDataViewUser.keyWords,
                qSellerDataViewUser.createTime)
            .from(qSellerDataViewUser)
            .where(qSellerDataViewUser.supplierId.eq(supplierid))
            .groupBy(qSellerDataViewUser.visitorName);
    if (country != null) {
      query.where(qSellerDataViewUser.country.eq(country));
    }
    if (province != null) {
      query.where(qSellerDataViewUser.province.eq(province));
    }
    if (keywords != null) {
      query.where(qSellerDataViewUser.keyWords.like("%" + keywords + "%"));
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

  public void save(SellerDataViewUser usr) {
    sellerUserRepository.save(usr);
  }

  public Optional<SellerDataViewUser> findTopByOrderByCreateTimeDesc() {
    return sellerUserRepository.findTopByOrderByCreateTimeDesc();
  }

  public List<Data> getUserRelation() {
    QSellerDataViewUser qs = QSellerDataViewUser.sellerDataViewUser;
    JPAQueryFactory queryFactory = getQueryFactory();
    JPAQuery<Data> select =
        queryFactory.select(
            Projections.bean(Data.class, qs.supplierId.as("number"), qs.sign.as("key")));
    List<Data> fetch = select.fetch();
    return fetch;
  }
}

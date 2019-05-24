package com.shoestp.mains.dao.sellerdataview.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.user.QDataViewUser;
import com.shoestp.mains.entitys.sellerdataview.user.QSellerDataViewUser;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;
import com.shoestp.mains.repositorys.sellerdataview.user.UserRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 商家后台用户数据层
 * @author: lingjian
 * @create: 2019/5/24 11:35
 */
@Repository
public class UserDao extends BaseDao<SellerDataViewUser> {

  public List findUser(Date date, String country, String province, Integer supplierid){
    QSellerDataViewUser qSellerDataViewUser = QSellerDataViewUser.sellerDataViewUser;
    JPAQuery<SellerDataViewUser> query = getQuery().select(qSellerDataViewUser).from(qSellerDataViewUser)
            .where(qSellerDataViewUser.supplierId.eq(supplierid))
            .where(qSellerDataViewUser.createTime.in(date));
    if(country != null){
      query.where(qSellerDataViewUser.country.eq(country));
    }
    if(province != null){
      query.where(qSellerDataViewUser.province.eq(province));
    }
    return query.fetchResults().getResults();
  }

  @Resource private UserRepository userRepository;

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

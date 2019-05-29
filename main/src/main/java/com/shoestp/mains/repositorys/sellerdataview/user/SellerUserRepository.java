package com.shoestp.mains.repositorys.sellerdataview.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;

/**
 * @description: 商家后台用户类-实现JPA接口
 * @author: lingjian
 * @create: 2019/5/24 11:40
 */
public interface SellerUserRepository
    extends PagingAndSortingRepository<SellerDataViewUser, Integer> {

  Optional<SellerDataViewUser> findTopByOrderByCreateTimeDesc();

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(
      value = "UPDATE seller_data_view_user set key_words = CONCAT(key_words,?2 ) WHERE sign = ?1 ",
      nativeQuery = true)
  void updBySign(String sign, String keyword);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(
      value =
          "UPDATE seller_data_view_user set page_count = page_count + ?3,inquiry_count=inquiry_count + ?4 WHERE sign = ?2 AND supplier_id = ?1 ",
      nativeQuery = true)
  void updBySupAndSign(Integer sup, String sign, Integer pageCount, Integer inquiryCount);
}

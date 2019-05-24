package com.shoestp.mains.repositorys.sellerdataview.user;

import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 商家后台用户类-实现JPA接口
 * @author: lingjian
 * @create: 2019/5/24 11:40
 */
public interface SellerUserRepository extends PagingAndSortingRepository<SellerDataViewUser, Integer> {}

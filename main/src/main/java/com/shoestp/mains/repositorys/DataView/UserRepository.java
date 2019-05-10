package com.shoestp.mains.repositorys.DataView;

import com.shoestp.mains.entitys.DataView.DataViewReal;
import com.shoestp.mains.entitys.DataView.DataViewUser;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 用户-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:29
 */
public interface UserRepository extends PagingAndSortingRepository<DataViewUser, Integer> {}

package com.shoestp.mains.repositorys.dataView.user;

import com.shoestp.mains.entitys.dataView.user.DataViewUserArea;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 用户概况-实现JPA接口
 * @author: lingjian @Date: 2019/5/13 14:20
 */
public interface UserAreaRepository extends PagingAndSortingRepository<DataViewUserArea, Integer> {}

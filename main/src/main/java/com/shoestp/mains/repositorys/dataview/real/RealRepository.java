package com.shoestp.mains.repositorys.dataview.real;

import com.shoestp.mains.entitys.dataview.real.DataViewReal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 展示数据-实现JPA接口
 * @author: lingjian @Date: 2019/8/7 10:52
 */
public interface RealRepository extends PagingAndSortingRepository<DataViewReal, Integer> {}

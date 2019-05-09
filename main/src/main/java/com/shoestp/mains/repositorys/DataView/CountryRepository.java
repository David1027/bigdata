package com.shoestp.mains.repositorys.DataView;

import com.shoestp.mains.entitys.DataView.DataViewCountry;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 国家-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:40
 */
public interface CountryRepository extends PagingAndSortingRepository<DataViewCountry, Integer> {}

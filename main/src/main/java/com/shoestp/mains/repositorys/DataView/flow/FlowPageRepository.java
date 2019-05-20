package com.shoestp.mains.repositorys.DataView.flow;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.DataView.flow.DataViewFlowPage;

/**
 * @description: 页面-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:13
 */
public interface FlowPageRepository extends PagingAndSortingRepository<DataViewFlowPage, Integer> {}

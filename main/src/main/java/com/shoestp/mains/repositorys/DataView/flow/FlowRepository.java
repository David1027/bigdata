package com.shoestp.mains.repositorys.DataView.flow;



import com.shoestp.mains.entitys.DataView.flow.DataViewFlow;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 流量-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:13
 */
public interface FlowRepository extends PagingAndSortingRepository<DataViewFlow, Integer> {
}

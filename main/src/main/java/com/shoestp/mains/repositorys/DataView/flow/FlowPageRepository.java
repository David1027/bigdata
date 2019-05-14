package com.shoestp.mains.repositorys.DataView.flow;



import com.shoestp.mains.entitys.DataView.flow.DataViewFlow;
import com.shoestp.mains.entitys.DataView.flow.DataViewFlowPage;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 流量-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:13
 */
public interface FlowPageRepository extends PagingAndSortingRepository<DataViewFlowPage, Integer> {
}

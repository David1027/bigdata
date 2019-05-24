package com.shoestp.mains.repositorys.dataview.flow;

import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 页面-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:13
 */
public interface FlowPageRepository extends PagingAndSortingRepository<DataViewFlowPage, Integer> {}

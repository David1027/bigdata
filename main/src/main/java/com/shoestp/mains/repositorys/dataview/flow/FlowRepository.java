package com.shoestp.mains.repositorys.dataview.flow;

import com.shoestp.mains.entitys.dataView.flow.DataViewFlow;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @description: 流量-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:13
 */
public interface FlowRepository extends PagingAndSortingRepository<DataViewFlow, Integer> {

  public Optional<DataViewFlow> findTopByOrderByCreateTimeDesc();
}

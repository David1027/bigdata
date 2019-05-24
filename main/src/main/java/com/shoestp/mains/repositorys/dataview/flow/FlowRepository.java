package com.shoestp.mains.repositorys.dataview.flow;

import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 流量-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:13
 */
public interface FlowRepository extends PagingAndSortingRepository<DataViewFlow, Integer> {

  public Optional<DataViewFlow> findTopByOrderByCreateTimeDesc();
}

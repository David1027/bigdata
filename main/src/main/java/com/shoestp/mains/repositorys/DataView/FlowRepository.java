package com.shoestp.mains.repositorys.DataView;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.DataView.DataViewCountry;
import com.shoestp.mains.entitys.DataView.DataViewFlow;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 流量-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:13
 */
public interface FlowRepository extends PagingAndSortingRepository<DataViewFlow, Integer> {
}

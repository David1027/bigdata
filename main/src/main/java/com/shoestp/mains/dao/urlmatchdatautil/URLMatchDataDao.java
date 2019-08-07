package com.shoestp.mains.dao.urlmatchdatautil;

import com.shoestp.mains.entitys.urlmatchdatautil.URLMatchDataEntity;
import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface URLMatchDataDao extends PagingAndSortingRepository<URLMatchDataEntity, Integer> {

  List<URLMatchDataEntity> findByType(URLDataTypeEnum type);
}

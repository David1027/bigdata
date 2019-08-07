package com.shoestp.mains.dao.urlmatchdatautil;

import com.shoestp.mains.entitys.urlmatchdatautil.URLMatchDataEntity;
import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;
import java.util.List;

@CacheDefaults(cacheName = "One_Minutes")
public interface URLMatchDataDao extends PagingAndSortingRepository<URLMatchDataEntity, Integer> {
  @CacheResult
  List<URLMatchDataEntity> findByType(URLDataTypeEnum type);
}

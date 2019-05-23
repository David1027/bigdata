package com.shoestp.mains.service.metaData.impl;

import com.shoestp.mains.dao.metaData.PltCountryDao;
import com.shoestp.mains.entitys.metaData.PltCountry;
import com.shoestp.mains.service.metaData.CountryService;
import java.util.List;
import javax.annotation.Resource;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;
import org.springframework.stereotype.Service;

@Service(value = "pltCountryService")
@CacheDefaults(cacheName = "month")
public class CountryServiceImpl implements CountryService {

  @Resource(name = "pltCountryDao")
  private PltCountryDao countryDao;

  @Override
  @CacheResult
  public List<PltCountry> getCountryList() {
    return countryDao.findAll();
  }
}

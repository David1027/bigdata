package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.metadata.PltCountryDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.service.metadata.CountryService;
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

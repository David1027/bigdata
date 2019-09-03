package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.metadata.PltCountryDao;
import com.shoestp.mains.dao.metadata.ProvinceDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.Province;
import com.shoestp.mains.service.metadata.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.start2do.utils.iputils.IpUtils;

import javax.annotation.Resource;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;
import java.util.List;
import java.util.Optional;

@Service
@CacheDefaults(cacheName = "15_Minutes")
public class LocationServiceImpl implements LocationService {
  private static final Logger logger = LogManager.getLogger(LocationServiceImpl.class);
  @Resource private PltCountryDao countryDao;
  @Resource private ProvinceDao provinceDao;


  @Override
  @CacheResult
  public PltCountry getCountry(String name) {
    logger.debug("getCountry=>name:{}", name);
    if (name != null) {
      Optional<PltCountry> result = countryDao.findByNameLike(name);
      if (result.isPresent()) {
        return result.get();
      }
    }
    return countryDao.findById(6).get();
  }

  @Override
  public Province getProvince(String address) {
    Optional<Province> result = provinceDao.findByNameLike("%" + address + "%");
    if (result.isPresent()) {
      return result.get();
    }
    return null;
  }

  @Override
  @CacheResult
  public List<PltCountry> getCountryList() {
    return countryDao.findAll();
  }

  @Override
  @CacheResult
  public PltCountry getCountryByIp(String ip) {
    String[] strings = getAddress(ip);
    if (strings != null && strings.length == 0) {
      return getCountry(null);
    }
    return getCountry(strings[0]);
  }

  /**
   * Get address 根据Ip获取国家地区
   *
   * @param ip the ip
   * @return the string [ ]
   * @author lijie
   * @date 2019 /08/09
   * @since string [ ].
   */
  @Override
  @CacheResult
  public String[] getAddress(String ip) {
    return IpUtils.find(ip);
  }

  /**
   * 根据 ISO-3166 alpha-3 三位地区简写查询国家
   *
   * @param country
   */
  @Override
  @CacheResult
  public PltCountry getCountryByShortName(String country) {
    Optional<PltCountry> result = countryDao.findFirstByShortName(country);
    if (result.isPresent()) {
      return result.get();
    }
    return countryDao.findById(6).get();
  }
}

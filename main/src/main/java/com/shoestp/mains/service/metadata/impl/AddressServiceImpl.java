package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.metadata.PltCountryDao;
import com.shoestp.mains.dao.metadata.ProvinceDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.Province;
import com.shoestp.mains.service.metadata.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
  @Resource private PltCountryDao countryDao;
  @Resource private ProvinceDao provinceDao;

  @Override
  public PltCountry getCountry(String name) {
    Optional<PltCountry> result = countryDao.findByName(name);
    if (result.isPresent()) {
      return result.get();
    }
    return countryDao.findById(6).get();
  }

  @Override
  public Province getProvince(String address) {
    Optional<Province> result = provinceDao.findByName(address);
    if (result.isPresent()) {
      return result.get();
    }
    return null;
  }
}

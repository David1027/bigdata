package com.shoestp.mains.service.metadata.impl;

import java.util.List;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoestp.mains.dao.metadata.ProvinceDao;
import com.shoestp.mains.entitys.metadata.Province;
import com.shoestp.mains.service.metadata.ProvinceService;

@Service
@CacheDefaults(cacheName = "month")
public class ProvinceServiceImpl implements ProvinceService {

  @Autowired private ProvinceDao provinceDao;

  @Override
  @CacheResult
  public List<Province> getAll() {
    return provinceDao.findAll();
  }
}

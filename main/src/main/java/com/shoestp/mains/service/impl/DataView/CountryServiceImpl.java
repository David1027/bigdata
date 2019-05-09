package com.shoestp.mains.service.impl.DataView;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.CountryDao;
import com.shoestp.mains.service.DataView.CountryService;

import org.springframework.stereotype.Service;

/**
 * @description: 国家-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:40
 */
@Service
public class CountryServiceImpl implements CountryService {

  @Resource private CountryDao countryDao;
}

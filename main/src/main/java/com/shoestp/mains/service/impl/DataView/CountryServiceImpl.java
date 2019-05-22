package com.shoestp.mains.service.impl.DataView;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.realcountry.RealCountryDao;
import com.shoestp.mains.service.DataView.CountryService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.country.CountryView;

import org.springframework.stereotype.Service;

/**
 * @description: 国家-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:40
 */
@Service
public class CountryServiceImpl implements CountryService {

  @Resource private RealCountryDao realCountryDao;

    /**
     * 获取国家地区访客数，时间=>当天
     * @return List<CountryView>
     */
  @Override
  public List<CountryView> getCountryArea() {
    List<CountryView> collect =
        realCountryDao.findAllByCountry(DateTimeUtil.getTimesmorning(), DateTimeUtil.getTimesnight())
            .stream()
            .map(
                bean -> {
                  CountryView countryView = new CountryView();
                  countryView.setCountryName(bean.getCountryName());
                  countryView.setCountryEnglishName(bean.getCountryEnglishName());
                  countryView.setCountryImage(bean.getCountryImage());
                  countryView.setVisitorCount(bean.getVisitorCount());
                  countryView.setVisitorCountPc(bean.getVisitorCountPc());
                  countryView.setVisitorCountWap(bean.getVisitorCountWap());
                  return countryView;
                })
            .collect(Collectors.toList());
    return collect;
  }
}

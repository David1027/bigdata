package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.views.DataView.country.CountryView;

/**
 * @description: 国家-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:40
 */
public interface CountryService {
  /**
   * 获取国家地区访客数
   *
   * @author: lingjian @Date: 2019/5/13 11:12
   * @return List<CountryView>
   */
  List<CountryView> getCountryArea();
}

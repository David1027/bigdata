package com.shoestp.mains.service.dataview;

import com.shoestp.mains.views.dataview.country.CountryView;
import java.util.List;

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

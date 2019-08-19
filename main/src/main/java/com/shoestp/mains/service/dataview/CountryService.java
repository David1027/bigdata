package com.shoestp.mains.service.dataview;

import com.shoestp.mains.views.dataview.country.CountryView;

import java.util.Date;
import java.util.List;

/**
 * @description: 国家-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:40
 */
public interface CountryService {
  /**
   * 获取国家地区访客数
   *
   * @author: lingjian @Date: 2019/8/19 10:34
   * @param date 时间
   * @param num 天数类型
   * @return List<CountryView> 国家前端展示类集合对象
   */
  List<CountryView> getCountry(Date date, Integer num);
}

package com.shoestp.mains.service.xwt.dataview;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.xwt.dataview.country.XwtViewCountry;
import com.shoestp.mains.views.dataview.country.CountryView;

/**
 * @description: 国家-服务层接口
 * @author: lingjian
 * @create: 2020/1/3 9:09
 */
public interface XwtViewCountryService {
  /**
   * 获取国家地区访客数
   *
   * @param date 时间
   * @param num 天数类型
   * @return List<XwtViewCountry> 国家表集合对象
   */
  List<XwtViewCountry> getCountry(Date date, Integer num);
}

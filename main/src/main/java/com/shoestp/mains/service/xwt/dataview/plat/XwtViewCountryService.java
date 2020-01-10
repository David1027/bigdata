package com.shoestp.mains.service.xwt.dataview.plat;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.xwt.dataview.plat.country.XwtViewCountry;

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

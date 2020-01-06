package com.shoestp.mains.service.xwt.dataview.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.xwt.dataview.dao.XwtViewCountryDAO;
import com.shoestp.mains.entitys.xwt.dataview.country.XwtViewCountry;
import com.shoestp.mains.service.xwt.dataview.XwtViewCountryService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;

import org.springframework.stereotype.Service;

/**
 * @description: 国家-服务层实现类
 * @author: lingjian
 * @create: 2020/1/3 9:29
 */
@Service
public class XwtViewCountryServiceImpl implements XwtViewCountryService {

  @Resource private XwtViewCountryDAO dao;

  /**
   * 获取国家地区访客数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewCountry>
   */
  private List<XwtViewCountry> getAllCountry(Date start, Date end) {
    return dao.findAllByCountry(
        DateTimeUtil.getTimesOfDay(start, 0), DateTimeUtil.getTimesOfDay(end, 24));
  }

  /**
   * 根据时间，天数类型获取国家地区访客数
   *
   * @param date 时间
   * @param num 天数类型
   * @return List<XwtViewCountry>
   */
  @Override
  public List<XwtViewCountry> getCountry(Date date, Integer num) {
    if (num != null) {
      return getAllCountry(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getAllCountry(date, date);
    }
  }
}

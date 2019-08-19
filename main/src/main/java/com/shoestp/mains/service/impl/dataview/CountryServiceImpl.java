package com.shoestp.mains.service.impl.dataview;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.dataview.realcountry.RealCountryDao;
import com.shoestp.mains.service.dataview.CountryService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.dataview.country.CountryView;

import org.springframework.stereotype.Service;

/**
 * @description: 国家-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:40
 */
@Service
public class CountryServiceImpl implements CountryService {

  @Resource private RealCountryDao realCountryDao;

  /**
   * 获取国家地区访客数
   *
   * @author: lingjian @Date: 2019/8/19 10:35
   * @param start 开始时间
   * @param end 结束时间
   * @return List<CountryView>
   */
  private List<CountryView> getAllCountry(Date start, Date end) {
    return realCountryDao.findAllByCountry(
        DateTimeUtil.getTimesOfDay(start, 0), DateTimeUtil.getTimesOfDay(end, 24));
  }

  /**
   * 根据时间，天数类型获取国家地区访客数
   *
   * @author: lingjian @Date: 2019/8/19 10:21
   * @param date 时间
   * @param num 天数类型
   * @return List<CountryView>
   */
  @Override
  public List<CountryView> getCountry(Date date, Integer num) {
    if (num != null) {
      return getAllCountry(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getAllCountry(date, date);
    }
  }
}

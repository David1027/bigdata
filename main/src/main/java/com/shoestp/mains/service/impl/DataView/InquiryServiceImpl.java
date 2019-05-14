package com.shoestp.mains.service.impl.DataView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.inquiry.InquiryDao;
import com.shoestp.mains.dao.DataView.inquiry.InquiryRankDao;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.service.DataView.InquiryService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.DataView.inquiry.InquiryRankView;
import com.shoestp.mains.views.DataView.inquiry.InquiryView;

import org.springframework.stereotype.Service;

/**
 * @description: 询盘-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:18
 */
@Service
public class InquiryServiceImpl implements InquiryService {

  @Resource private InquiryDao inquiryDao;
  @Resource private InquiryRankDao inquiryRankDao;

  /**
   * 根据时间获取询盘概况
   *
   * @author: lingjian @Date: 2019/5/14 10:08
   * @param startDate
   * @param endDate
   * @return
   */
  @Override
  public List<InquiryView> getInquiryOverview(Date startDate, Date endDate) {
    return inquiryDao
        .findAllByCreateTime(
            DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24))
        .stream()
        .map(
            bean -> {
              InquiryView inquiryView = new InquiryView();
              inquiryView.setVisitorCount(bean.get(0, Integer.class));
              inquiryView.setInquiryNumber(bean.get(1, Integer.class));
              inquiryView.setInquiryCount(bean.get(2, Integer.class));
              inquiryView.setTotalInquiryCount(inquiryDao.findAllByInquiry());
              return inquiryView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 根据时间获取用户的询盘数
   *
   * @author: lingjian @Date: 2019/5/14 11:04
   * @param date
   * @param start
   * @param end
   * @return List<InquiryView>
   */
  public List<InquiryView> getInquiryByHour(Date date, int start, int end) {
    return inquiryDao
        .findAllByCreateTime(
            DateTimeUtil.getTimesOfDay(date, start), DateTimeUtil.getTimesOfDay(date, end))
        .stream()
        .map(
            bean -> {
              InquiryView inquiryView = new InquiryView();
              inquiryView.setInquiryCount(bean.get(2, Integer.class));
              return inquiryView;
            })
        .collect(Collectors.toList());
  }

  /**
   * 获取24个小时中每个小时的询盘数
   *
   * @author: lingjian @Date: 2019/5/14 11:04
   * @param date
   * @return
   */
  public int[] getEveryHour(Date date) {
    int[] arr = new int[23];
    for (int i = 0; i < arr.length; i++) {
      if (!getInquiryByHour(date, i, i + 1).isEmpty()) {
        arr[i] = getInquiryByHour(date, i, i + 1).get(0).getInquiryCount().intValue();
      }
    }
    return arr;
  }

  /**
   * 获取询盘概况中的时段分布
   *
   * @author: lingjian @Date: 2019/5/14 11:03
   * @return
   */
  @Override
  public Map<String, int[]> getInquiryTime() {
    Map<String, int[]> inquiryTimeMap = new HashMap<>();
    inquiryTimeMap.put("inquiryCount", getEveryHour(new Date()));
    return inquiryTimeMap;
  }

  /**
   * 根据询盘类型获取询盘排行
   *
   * @author: lingjian @Date: 2019/5/14 11:44
   * @param inquiryType
   * @return List<InquiryRankView>
   */
  @Override
  public List<InquiryRankView> getInquiryRank(InquiryTypeEnum inquiryType) {
      return inquiryRankDao.findAllByInquiryType(inquiryType).stream()
        .map(bean -> {
            InquiryRankView inquiryRankView = new InquiryRankView();
            inquiryRankView.setInquiryName(bean.getInquiryName());
            inquiryRankView.setVisitorCount(bean.getVisitorCount());
            inquiryRankView.setViewCount(bean.getViewCount());
            inquiryRankView.setInquiryCount(bean.getInquiryCount());
            inquiryRankView.setInquiryNumber(bean.getInquiryNumber());
            inquiryRankView.setInquiryAmount(bean.getInquiryAmount());
            return inquiryRankView;
        })
        .collect(Collectors.toList());
  }
}

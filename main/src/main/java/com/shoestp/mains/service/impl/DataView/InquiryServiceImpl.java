package com.shoestp.mains.service.impl.DataView;

import com.shoestp.mains.dao.dataView.inquiry.InquiryDao;
import com.shoestp.mains.dao.dataView.inquiry.InquiryRankDao;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.service.dataView.InquiryService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.utils.dateUtils.KeyValueViewUtil;
import com.shoestp.mains.views.DataView.inquiry.InquiryRankView;
import com.shoestp.mains.views.DataView.inquiry.InquiryTypeView;
import com.shoestp.mains.views.DataView.inquiry.InquiryView;
import com.shoestp.mains.views.DataView.utils.KeyValue;
import com.shoestp.mains.views.DataView.utils.Page;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
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
   * 判断非空处理
   *
   * @author: lingjian @Date: 2019/5/17 11:16
   * @param inquiry 询盘对象
   * @return
   */
  private InquiryView isNullTo(InquiryView inquiry) {
    if (inquiry.getVisitorCount() == null) {
      inquiry.setVisitorCount(0);
      inquiry.setInquiryNumber(0);
      inquiry.setInquiryCount(0);
    }
    return inquiry;
  }

  /**
   * 根据开始时间和结束时间获取询盘概况
   *
   * @author: lingjian @Date: 2019/5/20 16:50
   * @param startDate
   * @param endDate
   * @return
   */
  public InquiryView getInquiryOverviewByDate(Date startDate, Date endDate) {
    InquiryView inquiry =
        isNullTo(
            inquiryDao.findAllByCreateTimeObject(
                DateTimeUtil.getTimesOfDay(startDate, 0), DateTimeUtil.getTimesOfDay(endDate, 24)));
    inquiry.setTotalInquiryCount(
        inquiryDao.findAllByInquiry().isEmpty() ? 0 : inquiryDao.findAllByInquiry().get(0));
    return inquiry;
  }
  /**
   * 根据时间,日期类型获取询盘概况
   *
   * @author: lingjian @Date: 2019/5/14 10:08
   * @param date
   * @param num
   * @return
   */
  @Override
  public InquiryView getInquiryOverview(Date date, Integer num) {
    if (num != null) {
      return getInquiryOverviewByDate(DateTimeUtil.getDayFromNum(date, num), date);
    } else {
      return getInquiryOverviewByDate(date, date);
    }
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
  public List<InquiryView> getInquiry(Date date, int start, int end) {
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
    int[] arr = new int[24];
    for (int i = 0; i < arr.length; i++) {
      if (!getInquiry(date, i, i + 1).isEmpty()) {
        arr[i] = getInquiry(date, i, i + 1).get(0).getInquiryCount();
      }
    }
    return arr;
  }

  /**
   * 获取num天内每天的询盘数
   *
   * @author: lingjian @Date: 2019/5/16 11:24
   * @param num
   * @param date
   * @return
   */
  public int[] getEveryDay(int num, Date date) {
    int[] arr = new int[num];
    for (int i = 0; i < arr.length; i++) {
      if (!getInquiry(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24).isEmpty()) {
        arr[i] =
            getInquiry(DateTimeUtil.getDayFromNum(date, num - i - 1), 0, 24)
                .get(0)
                .getInquiryCount();
      }
    }
    return arr;
  }

  /**
   * 获取询盘概况中的时段分布(小时)
   *
   * @author: lingjian @Date: 2019/5/16 11:14
   * @param date 时间
   * @return Map<String, int[]>
   */
  public Map<String, int[]> getInquiryTimeHourMap(Date date) {
    Map<String, int[]> inquiryTimeHourMap = new HashMap<>();
    inquiryTimeHourMap.put("inquiryCount", getEveryHour(date));
    return inquiryTimeHourMap;
  }

  /**
   * 获取询盘概况中的时段分布(天)
   *
   * @author: lingjian @Date: 2019/5/16 11:26
   * @param num
   * @param date
   * @return
   */
  public Map<String, int[]> getInquiryTimeDayMap(int num, Date date) {
    Map<String, int[]> inquiryTimeDayMap = new HashMap<>();
    inquiryTimeDayMap.put("inquiryCount", getEveryDay(num, date));
    return inquiryTimeDayMap;
  }

  /**
   * 获取询盘概况中的时段分布(小时)+横坐标(小时)
   *
   * @author: lingjian @Date: 2019/5/14 11:03
   * @return
   */
  @Override
  public Map<String, Map> getInquiryTimeByHour(Date date) {
    Map<String, Map> inquiryTimeMap = new HashMap<>();
    inquiryTimeMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    inquiryTimeMap.put("day", getInquiryTimeHourMap(date));
    return inquiryTimeMap;
  }

  /**
   * 获取询盘概况中的时段分布(天)+横坐标(日期)
   *
   * @author: lingjian @Date: 2019/5/16 11:24
   * @param date
   * @return
   */
  @Override
  public Map<String, Map> getInquiryTimeByDay(Date date, Integer num) {
    Map<String, Map> inquiryTimeMap = new HashMap<>();
    inquiryTimeMap.put("abscissa", DateTimeUtil.getDayAbscissa(num, date));
    inquiryTimeMap.put("day", getInquiryTimeDayMap(num, date));
    return inquiryTimeMap;
  }

  public List<InquiryRankView> getInquiryRankPage(
      InquiryTypeEnum inquiryType, Integer page, Integer pageSize) {
    return inquiryRankDao
        .findAllByInquiryType(inquiryType, DateTimeUtil.getTimesmorning(), page, pageSize).stream()
        .map(
            bean -> {
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

  /**
   * 根据询盘类型获取询盘排行
   *
   * @author: lingjian @Date: 2019/5/14 11:44
   * @param inquiryType
   * @return List<InquiryRankView>
   */
  @Override
  public Page getInquiryRank(InquiryTypeEnum inquiryType, Integer page, Integer pageSize) {
    Page page1 = new Page();
    page1.setPage(getInquiryRankPage(inquiryType, page, pageSize));
    page1.setTotalCount(getInquiryRankPage(inquiryType, null, 0).size());
    return page1;
  }

  /**
   * 根据询盘类型获取实时询盘排行+分页
   *
   * @author: lingjian @Date: 2019/5/16 14:16
   * @param inquiryType
   * @return
   */
  public List getInquiryRealRankPage(
      InquiryTypeEnum inquiryType, Date startDate, Date endDate, Integer page, Integer pageSize) {
    return inquiryRankDao
        .findAllByInquiryTypeBetween(
            inquiryType,
            DateTimeUtil.getTimesOfDay(startDate, 0),
            DateTimeUtil.getTimesOfDay(endDate, 24),
            page,
            pageSize)
        .stream()
        .map(
            bean -> {
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

  /**
   * 根据询盘类型获取实时询盘排行+分页
   *
   * @author: lingjian @Date: 2019/5/16 14:16
   * @param inquiryType
   * @return
   */
  @Override
  public Page getInquiryRealRank(
      InquiryTypeEnum inquiryType, Date startDate, Date endDate, Integer page, Integer pageSize) {
    Page page1 = new Page();
    page1.setPage(getInquiryRealRankPage(inquiryType, startDate, endDate, page, pageSize));
    page1.setTotalCount(getInquiryRealRankPage(inquiryType, startDate, endDate, null, 0).size());
    return page1;
  }

  /**
   * 根据询盘类型，询盘名称，开始时间和结束时间获取询盘排行的数据
   *
   * @param inquiryType
   * @param inquiryName
   * @param date
   * @param start
   * @param end
   * @return
   */
  public List<InquiryRankView> getInquiryRealRankByHour(
      InquiryTypeEnum inquiryType, String inquiryName, Date date, int start, int end) {
    return inquiryRankDao
        .findAllByInquiryTypeAndInquiryNameBetween(
            inquiryType,
            inquiryName,
            DateTimeUtil.getTimesOfDay(date, start),
            DateTimeUtil.getTimesOfDay(date, end))
        .stream()
        .map(
            bean -> {
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

  /**
   * 根据询盘类型，询盘名称获取24个小时每个小时的数据
   *
   * @author: lingjian @Date: 2019/5/16 14:45
   * @param inquiryType
   * @param inquiryName
   * @param date
   * @param parameter
   * @return
   */
  public double[] getEveryHourByInquiryName(
      InquiryTypeEnum inquiryType, String inquiryName, Date date, String parameter) {
    double[] arr = new double[24];
    for (int i = 0; i < arr.length; i++) {
      if ("visitorCount".equals(parameter)
          && !getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1).isEmpty()) {
        arr[i] =
            getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1)
                .get(0)
                .getVisitorCount();
      } else if ("viewCount".equals(parameter)
          && !getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1).isEmpty()) {
        arr[i] =
            getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1)
                .get(0)
                .getViewCount();
      } else if ("inquiryCount".equals(parameter)
          && !getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1).isEmpty()) {
        arr[i] =
            getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1)
                .get(0)
                .getInquiryCount();
      } else if ("inquiryNumber".equals(parameter)
          && !getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1).isEmpty()) {
        arr[i] =
            getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1)
                .get(0)
                .getInquiryNumber();
      } else if ("inquiryAmount".equals(parameter)
          && !getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1).isEmpty()) {
        arr[i] =
            getInquiryRealRankByHour(inquiryType, inquiryName, date, i, i + 1)
                .get(0)
                .getInquiryAmount();
      }
    }
    return arr;
  }

  /**
   * 根据询盘类型，询盘名称获取实时排行时段分析(一天24小时)
   *
   * @author: lingjian @Date: 2019/5/16 14:45
   * @param inquiryType
   * @param inquiryName
   * @param date
   * @return
   */
  public Map<String, List> getInquiryTimeHourMap(
      InquiryTypeEnum inquiryType, String inquiryName, Date date) {
    List<KeyValue> keyValue = new ArrayList<>();
    keyValue.add(
        KeyValueViewUtil.getFlowKeyValue(
            "visitorCount",
            getEveryHourByInquiryName(inquiryType, inquiryName, date, "visitorCount")));
    keyValue.add(
        KeyValueViewUtil.getFlowKeyValue(
            "viewCount", getEveryHourByInquiryName(inquiryType, inquiryName, date, "viewCount")));
    keyValue.add(
        KeyValueViewUtil.getFlowKeyValue(
            "inquiryCount",
            getEveryHourByInquiryName(inquiryType, inquiryName, date, "inquiryCount")));
    keyValue.add(
        KeyValueViewUtil.getFlowKeyValue(
            "inquiryNumber",
            getEveryHourByInquiryName(inquiryType, inquiryName, date, "inquiryNumber")));
    keyValue.add(
        KeyValueViewUtil.getFlowKeyValue(
            "inquiryAmount",
            getEveryHourByInquiryName(inquiryType, inquiryName, date, "inquiryAmount")));
    Map<String, List> inquiryTimeMap = new HashMap<>();
    inquiryTimeMap.put("day", keyValue);
    return inquiryTimeMap;
  }

  /**
   * 根据询盘类型，询盘名称获取实时排行时段分析(一天24小时)+横坐标
   *
   * @author: lingjian @Date: 2019/5/16 14:45
   * @param inquiryType
   * @param inquiryName
   * @return
   */
  @Override
  public Map<String, Map> getInquiryRealRankByHour(
      InquiryTypeEnum inquiryType, String inquiryName) {
    Map<String, Map> inquiryTimeMap = new HashMap<>();
    inquiryTimeMap.put("abscissa", DateTimeUtil.getHourAbscissa(1));
    inquiryTimeMap.put("inquiryTime", getInquiryTimeHourMap(inquiryType, inquiryName, new Date()));
    return inquiryTimeMap;
  }

  /**
   * 根据搜索名称，日期，实时类型获取询盘搜索
   *
   * @author: lingjian @Date: 2019/5/20 9:35
   * @param inquirySearch
   * @param type
   * @return
   */
  @Override
  public List getInquirySearch(String inquirySearch, String type, int page, int pageSize) {
    return inquiryRankDao
        .findInquiryByInquiryName(
            inquirySearch,
            type,
            DateTimeUtil.getTimesmorning(),
            DateTimeUtil.getTimesnight(),
            page,
            pageSize)
        .stream()
        .map(
            bean -> {
              InquiryTypeView inquiryTypeView = new InquiryTypeView();
              inquiryTypeView.setInquiryType(bean.getInquiryType());
              inquiryTypeView.setInquiryName(bean.getInquiryName());
              inquiryTypeView.setVisitorCount(bean.getVisitorCount());
              inquiryTypeView.setViewCount(bean.getViewCount());
              inquiryTypeView.setInquiryCount(bean.getInquiryCount());
              inquiryTypeView.setInquiryNumber(bean.getInquiryNumber());
              inquiryTypeView.setInquiryAmount(bean.getInquiryAmount());
              return inquiryTypeView;
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<KeyValue> getRanking(String type, Date endTime, Integer num, int start, int limit) {
    if (endTime == null) {
      endTime = new Date();
    }
    Date startTime = null;
    if (num.equals(7) || num.equals(30)) {
      num -= num * 2;
      startTime = DateTimeUtil.countDate(endTime, Calendar.DAY_OF_YEAR, num);
    } else {
      startTime = DateTimeUtil.getTimesOfDay(endTime);
      endTime = DateTimeUtil.getTimesnight(endTime);
    }
    List<Object> ranking = inquiryRankDao.getRanking(type, startTime, endTime, start, limit);
    List<KeyValue> kv = new ArrayList<>();
    for (Object item : ranking) {
      Object[] o = (Object[]) item;
      KeyValue k = new KeyValue();
      k.setKey(o[0].toString());
      k.setValue(o[1].toString());
      kv.add(k);
    }
    return kv;
  }
}

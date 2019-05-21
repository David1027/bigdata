package com.shoestp.mains.service.DataView;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.views.DataView.inquiry.InquiryRankView;
import com.shoestp.mains.views.DataView.inquiry.InquiryView;

/**
 * @description: 询盘-服务层接口
 * @author: lingjian @Date: 2019/5/9 10:17
 */
public interface InquiryService {

  /**
   * 根据时间获取询盘概况
   *
   * @author: lingjian @Date: 2019/5/14 10:08
   * @param date
   * @param num
   * @return List<InquiryView>
   */
  InquiryView getInquiryOverview(Date date, Integer num);

  /**
   * 获取询盘概况中的时段分布(一天24小时)
   *
   * @author: lingjian @Date: 2019/5/14 11:02
   * @return Map<String, int[]>
   */
  Map<String, Map> getInquiryTimeByHour(Date date);

  /**
   * 获取询盘概况中的时段分布(一周七天)
   *
   * @author: lingjian @Date: 2019/5/16 11:23
   * @param date
   * @return
   */
  Map<String, Map> getInquiryTimeByDay(Date date, Integer num);

  /**
   * 根据询盘类型获取询盘排行
   *
   * @author: lingjian @Date: 2019/5/14 11:42
   * @param inquiryType
   * @return List<InquiryRankView>
   */
  List<InquiryRankView> getInquiryRank(InquiryTypeEnum inquiryType);

  /**
   * 根据询盘类型获取实时询盘排行
   *
   * @param inquiryType
   * @return
   */
  List<InquiryRankView> getInquiryRealRank(InquiryTypeEnum inquiryType);

  /**
   * 根据询盘类型，询盘名称获取实时排行时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/16 14:45
   * @param inquiryType
   * @param inquiryName
   * @return
   */
  Map<String, Map> getInquiryRealRankByHour(InquiryTypeEnum inquiryType, String inquiryName);

  /**
   * 根据搜索名称获取询盘搜索
   *
   * @author: lingjian @Date: 2019/5/20 9:35
   * @param inquirySearch
   * @param type
   * @return
   */
  List getInquirySearch(String inquirySearch, String type);
}

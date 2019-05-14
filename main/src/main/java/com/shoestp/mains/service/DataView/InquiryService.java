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
   * @param startDate
   * @param endDate
   * @return List<InquiryView>
   */
  List<InquiryView> getInquiryOverview(Date startDate, Date endDate);

  /**
   * 获取询盘概况中的时段分布
   *
   * @author: lingjian @Date: 2019/5/14 11:02
   * @return Map<String, int[]>
   */
  Map<String, int[]> getInquiryTime();

  /**
   * 根据询盘类型获取询盘排行
   *
   * @author: lingjian @Date: 2019/5/14 11:42
   * @param inquiryType
   * @return List<InquiryRankView>
   */
  List<InquiryRankView> getInquiryRank(InquiryTypeEnum inquiryType);
}

package com.shoestp.mains.controllers.DataView;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.DataView.InquiryService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 询盘-控制器
 * @author: lingjian @Date: 2019/5/9 10:15
 */
@RestController
@RequestMapping("/api/platform")
public class InquiryController {
  private static final Logger logger = LogManager.getLogger(InquiryController.class);

  @Resource private InquiryService inquiryService;

  /**
   * 根据时间获取询盘概况
   *
   * @author: lingjian @Date: 2019/5/14 10:07
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/inquiryoverview")
  public Object getInquiryOverview(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryOverview(startDate, endDate))
        .build();
  }

  /**
   * 获取询盘概况中的时段分布(一天24小时)
   *
   * @author: lingjian @Date: 2019/5/14 11:02
   * @param date
   * @return
   */
  @PostMapping(value = "/inquirytimebyday")
  public Object getInquiryTimeByDay(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryTimeByDay(date))
        .build();
  }

  /**
   * 获取询盘概况中的时段分布(一周七天)
   *
   * @author: lingjian @Date: 2019/5/16 11:23
   * @param date
   * @return
   */
  @PostMapping(value = "/inquirytimebyweek")
  public Object getInquiryTimeByWeek(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryTimeByWeek(date))
        .build();
  }

  /**
   * 获取询盘概况中的时段分布(一个月三十天)
   *
   * @author: lingjian @Date: 2019/5/16 11:29
   * @param date
   * @return
   */
  @PostMapping(value = "/inquirytimebymonth")
  public Object getInquiryTimeByMonth(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryTimeByMonth(date))
        .build();
  }

  /**
   * 根据时间，询盘类型获取询盘排行（全部）
   *
   * @author: lingjian @Date: 2019/5/14 11:41
   * @param inquiryType
   * @return
   */
  @PostMapping(value = "/inquiryrank")
  public Object getInquiryRank(InquiryTypeEnum inquiryType) {
    logger.debug(inquiryType);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryRank(inquiryType))
        .build();
  }

  /**
   * 根据询盘类型获取实时询盘排行
   *
   * @author: lingjian @Date: 2019/5/16 14:15
   * @param inquiryType
   * @return
   */
  @PostMapping(value = "/inquiryrealrank")
  public Object getInquiryRealRank(InquiryTypeEnum inquiryType) {
    logger.debug(inquiryType);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryRealRank(inquiryType))
        .build();
  }

  /**
   * 根据询盘类型，询盘名称获取实时排行时段分析(一天24小时)
   *
   * @author: lingjian @Date: 2019/5/16 14:44
   * @param inquiryType
   * @param inquiryName
   * @return
   */
  @PostMapping(value = "/inquiryrealrankbyday")
  public Object getInquiryRealRankByDay(InquiryTypeEnum inquiryType, String inquiryName) {
    logger.debug(inquiryType);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryRealRankByDay(inquiryType, inquiryName))
        .build();
  }
}

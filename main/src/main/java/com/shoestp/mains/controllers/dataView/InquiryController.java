package com.shoestp.mains.controllers.dataView;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataView.InquiryService;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
   * @param date
   * @param num
   * @return
   */
  @PostMapping(value = "/inquiryoverview")
  public Object getInquiryOverview(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(inquiryService.getInquiryOverview(date, num))
        .build();
  }

  /**
   * 获取询盘概况中的时段分布(小时)
   *
   * @author: lingjian @Date: 2019/5/14 11:02
   * @param date
   * @return
   */
  @PostMapping(value = "/inquirytimebyhour")
  public Object getInquiryTimeByHour(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(inquiryService.getInquiryTimeByHour(date))
        .build();
  }

  /**
   * 获取询盘概况中的时段分布(天)
   *
   * @author: lingjian @Date: 2019/5/16 11:23
   * @param date
   * @return
   */
  @PostMapping(value = "/inquirytimebyday")
  public Object getInquiryTimeByDay(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(inquiryService.getInquiryTimeByDay(date, num))
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
  public Object getInquiryRank(InquiryTypeEnum inquiryType, Integer page, Integer pageSize) {
    logger.debug(inquiryType);
    return MessageResult.builder()
        .code(1)
        .result(inquiryService.getInquiryRank(inquiryType, page, pageSize))
        .build();
  }

  /**
   * 根据询盘类型,时间获取询盘排行
   *
   * @author: lingjian @Date: 2019/5/16 14:15
   * @param inquiryType
   * @return
   */
  @PostMapping(value = "/inquiryrealrank")
  public Object getInquiryRealRank(
      InquiryTypeEnum inquiryType,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      Integer page,
      Integer pageSize) {
    logger.debug(inquiryType);
    return MessageResult.builder()
        .code(1)
        .result(inquiryService.getInquiryRealRank(inquiryType, startDate, endDate, page, pageSize))
        .build();
  }

  /**
   * 根据询盘类型，询盘名称获取实时排行时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/16 14:44
   * @param inquiryType
   * @param inquiryName
   * @return
   */
  @PostMapping(value = "/inquiryrealrankbyhour")
  public Object getInquiryRealRankByHour(InquiryTypeEnum inquiryType, String inquiryName) {
    logger.debug(inquiryType);
    return MessageResult.builder()
        .code(1)
        .result(inquiryService.getInquiryRealRankByHour(inquiryType, inquiryName))
        .build();
  }

  /**
   * 根据搜索名称获取询盘搜索
   *
   * @author: lingjian @Date: 2019/5/20 9:37
   * @param inquirySearch
   * @param type
   * @return
   */
  @PostMapping(value = "/inquirysearch")
  public Object getInquirySearch(String inquirySearch, String type, int page, int pageSize) {
    logger.debug(inquirySearch);
    return MessageResult.builder()
        .result(inquiryService.getInquirySearch(inquirySearch, type, page, pageSize))
        .build();
  }

  @GetMapping(value = "/getSupRanking")
  public Object getSupRanking(
      Date endTime,
      Integer num,
      @RequestParam(defaultValue = "0") int start,
      @RequestParam(defaultValue = "10") int limit) {
    return MessageResult.builder()
        .code(1)
        .result(
            inquiryService.getRanking(
                InquiryTypeEnum.SUPPLIER.toString(), endTime, num, start, limit))
        .build();
  }

  @GetMapping(value = "/getPdtRanking")
  public Object getPdtRanking(
      Date endTime,
      Integer num,
      @RequestParam(defaultValue = "0") int start,
      @RequestParam(defaultValue = "10") int limit) {
    return MessageResult.builder()
        .result(
            inquiryService.getRanking(
                InquiryTypeEnum.COMMODITY.toString(), endTime, num, start, limit))
        .build();
  }
}

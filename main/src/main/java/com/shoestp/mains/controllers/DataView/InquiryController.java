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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
   * 获取询盘概况中的时段分布
   *
   * @author: lingjian @Date: 2019/5/14 11:02
   * @param httpRequest
   * @return
   */
  @GetMapping(value = "/inquirytime")
  public Object getInquiryTime(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(inquiryService.getInquiryTime())
        .build();
  }

  /**
   * 根据询盘类型获取询盘排行
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
}

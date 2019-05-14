package com.shoestp.mains.controllers.DataView;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.DataView.FlowService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 流量-控制器
 * @author: lingjian @Date: 2019/5/9 10:08
 */
@RestController
@RequestMapping("/api/platform")
public class FlowController {
  private static final Logger logger = LogManager.getLogger(FlowController.class);

  @Resource private FlowService flowService;

  /**
   * 获取实时来源
   *
   * @author: lingjian @Date: 2019/5/10 16:45
   * @param httpRequest
   * @return
   */
  @GetMapping(value = "/realsource")
  public Object getRealSource(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).msg("Hello").result(flowService.getRealSource()).build();
  }

  /**
   * 根据时间获取设备来源
   *
   * @author: lingjian @Date: 2019/5/13 9:58
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/flowdevice")
  public Object getFlowDevice(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(flowService.getFlowDevice(startDate, endDate))
        .build();
  }

  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2019/5/14 14:11
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/flowsourcetype")
  public Object getFlowSourceType(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(flowService.getFlowSourceType(startDate, endDate))
        .build();
  }

  /**
   * 根据时间获取流量概况
   *
   * @author: lingjian @Date: 2019/5/14 15:05
   * @param date
   * @return
   */
  @PostMapping(value = "/flowsourcetypetime")
  public Object getFlowSourceTypeTime(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(flowService.getFlowSourceTypeTime(date))
        .build();
  }

  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:25
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/flowsourcepage")
  public Object getFlowSourcePage(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(flowService.getFlowSourcePage(startDate, endDate))
        .build();
  }

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/flowpageanalysis")
  public Object getFlowPageAnalysis(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate + "====" + endDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(flowService.getFlowPageAnalysis(startDate, endDate))
        .build();
  }

  /**
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/5/14 16:46
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/flowpage")
  public Object getFlowPage(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate + "====" + endDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(flowService.getFlowPage(startDate, endDate))
        .build();
  }
}

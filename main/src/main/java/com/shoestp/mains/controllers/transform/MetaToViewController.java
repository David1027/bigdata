package com.shoestp.mains.controllers.transform;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataview.CountryService;
import com.shoestp.mains.service.transform.MetaToViewService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 源数据转化展示数据 - 控制层
 * @author: lingjian
 * @create: 2019/8/6 13:53
 */
@RestController
@RequestMapping("/api/platform")
public class MetaToViewController {

  private static final Logger logger = LogManager.getLogger(MetaToViewController.class);

  @Resource private MetaToViewService metaToViewService;

  /**
   * 源数据转化real数据总表
   *
   * @author: lingjian @Date: 2019/8/8 13:41
   * @param httpRequest 请求参数
   * @param start 开始时间
   * @param end 结束时间
   * @return Object对象
   */
  @GetMapping(value = "/toreal")
  public Object toReal(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(metaToViewService.toReal(start, end)).build();
  }

  /**
   * 源数据转化flow流量表
   *
   * @author: lingjian @Date: 2019/8/8 13:42
   * @param httpRequest 请求参数
   * @param start 开始时间
   * @param end 结束时间
   * @return Object对象
   */
  @GetMapping(value = "/toflow")
  public Object toFlow(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(metaToViewService.toFlow(start, end)).build();
  }

  /**
   * 源数据转化flowpage页面分析表
   *
   * @author: lingjian @Date: 2019/8/8 13:42
   * @param httpRequest 请求参数
   * @param start 开始时间
   * @param end 结束时间
   * @return Object对象
   */
  @GetMapping(value = "/toflowpage")
  public Object toFlowPage(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(metaToViewService.toFlowPage(start, end)).build();
  }

  /**
   * 源数据转化inquiry询盘表
   *
   * @author: lingjian @Date: 2019/8/8 14:17
   * @param httpRequest 请求参数
   * @param start 开始时间
   * @param end 结束时间
   * @return Object对象
   */
  @GetMapping(value = "/toinquiry")
  public Object toInquiry(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(metaToViewService.toInquiry(start, end)).build();
  }

  /**
   * 源数据转化inquiryrank询盘排行表
   *
   * @author: lingjian @Date: 2019/8/8 15:14
   * @param httpRequest 请求参数
   * @param start 开始时间
   * @param end 结束时间
   * @return Object对象
   */
  @GetMapping(value = "/toinquiryrank")
  public Object toInquiryRank(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder()
        .code(1)
        .result(metaToViewService.toInquiryRank(start, end))
        .build();
  }

  @GetMapping(value = "/touser")
  public Object toUser(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(metaToViewService.toUser(start, end)).build();
  }
}

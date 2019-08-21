package com.shoestp.mains.controllers.dataview;

import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataview.RealService;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 实时-控制器
 * @author: lingjian @Date: 2019/5/9 10:21
 */
@RestController
@RequestMapping("/api/platform")
public class RealController {
  private static final Logger logger = LogManager.getLogger(RealController.class);

  @Resource private RealService realService;

  /**
   * 根据时间，关键字获取实时数据分析时段分布
   *
   * @author: lingjian @Date: 2019/5/23 13:56
   * @param date
   * @param indexCode
   * @return
   */
  @PostMapping(value = "/indextrend")
  public Object getIndexTrend(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, String indexCode) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getIndexTrend(date, indexCode))
        .build();
  }

  /**
   * 获取首页累计数据
   *
   * @author: lingjian @Date: 2019/5/23 14:22
   * @param httpRequest
   * @return
   */
  @GetMapping(value = "/indexgrand")
  public Object getIndexGrand(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).msg("Hello").result(realService.getIndexGrand()).build();
  }

  /**
   * 获取首页整体看板概况
   *
   * @author: lingjian @Date: 2019/5/22 14:27
   * @param date
   * @param num
   * @return
   */
  @GetMapping(value = "/indexoverview")
  public Object getIndexOverview(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder().code(1).result(realService.getIndexOverview(date, num)).build();
  }

  /**
   * 获取首页整体看板时段分布
   *
   * @param date
   * @param num
   * @param indexCode
   * @return
   */
  @GetMapping(value = "/indexoverviewtime")
  public Object getIndexOverviewTime(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num, String indexCode) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getIndexOverviewTime(date, num, indexCode))
        .build();
  }

  /**
   * 获取实时概况
   *
   * @author: lingjian @Date: 2019/5/9 15:45
   * @param httpRequest
   * @return
   */
  @GetMapping(value = "/realoverview")
  public Object getRealOverview(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getRealOverview())
        .build();
  }

  /**
   * 获取实时趋势
   *
   * @author: lingjian @Date: 2019/5/9 15:51
   * @param date
   * @return
   */
  @PostMapping(value = "/realtrend")
  public Object getRealTrend(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getRealTrend(date))
        .build();
  }

  /**
   * 获取流量来源列表
   *
   * @author: lingjian @Date: 2019/8/20 10:17
   * @param httpRequest 请求参数
   * @return Object对象
   */
  @GetMapping(value = "/sourcetype")
  public Object getSourceType(HttpServletRequest httpRequest) {
    logger.debug(httpRequest);
    return MessageResult.builder().code(1).msg("Hello").result(realService.getSourceType()).build();
  }

  /**
   * 获取实时访客列表
   *
   * @author: lingjian @Date: 2019/8/20 13:53
   * @param httpRequest 请求参数
   * @param page 开始条数
   * @param limit 显示条数
   * @param visitType 访客类型
   * @param sourceType 流量来源类型
   * @param urlPage 被访问页面
   * @param country 访客位置
   * @return Object对象
   */
  @GetMapping(value = "/realvisitor")
  public Object getRealVisitor(
      HttpServletRequest httpRequest,
      Integer page,
      Integer limit,
      Integer visitType,
      SourceTypeEnum sourceType,
      String urlPage,
      Integer country) {
    logger.debug(visitType + "---" + sourceType + "---" + urlPage + "---" + country);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getRealVisitor(page, limit, visitType, sourceType, urlPage, country))
        .build();
  }

  /**
   * 获取首页常访问页面列表
   *
   * @author: lingjian @Date: 2019/8/20 17:10
   * @param httpRequest 请求参数
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return Object对象
   */
  @GetMapping(value = "/realvisitpage")
  public Object getRealVisitPage(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      Integer page,
      Integer limit) {
    logger.debug(httpRequest);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getRealVisitPage(startDate, endDate, page, limit))
        .build();
  }

  /**
   * 获取首页搜索关键词排行
   *
   * @author: lingjian @Date: 2019/8/21 10:30
   * @param httpRequest 请求参数
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param page 开始条目
   * @param limit 结束条目
   * @return Object对象
   */
  @PostMapping(value = "/homesearch")
  public Object getHomeSearch(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      Integer page,
      Integer limit) {
    logger.debug(httpRequest);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getHomeSearch(startDate, endDate, page, limit))
        .build();
  }

  /**
   * 获取页面分析页面访问排行列表
   *
   * @author: lingjian @Date: 2019/8/20 17:18
   * @param httpRequest 请求参数
   * @param date 时间
   * @param num 天数类型
   * @param page 开始条数
   * @param limit 结束条数
   * @return Object对象
   */
  @GetMapping(value = "/realvisitpagenum")
  public Object getRealVisitPage(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
      Integer num,
      Integer page,
      Integer limit) {
    logger.debug(httpRequest);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getRealVisitPage(date, num, page, limit))
        .build();
  }
}

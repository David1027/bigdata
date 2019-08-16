package com.shoestp.mains.controllers.dataview;

import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataview.FlowService;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
  @PostMapping(value = "/realsource")
  public Object getRealSource(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder()
        .code(1)
        .result(flowService.getRealSource(startDate, endDate))
        .build();
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
    logger.debug(startDate + "==" + endDate);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowDevice(startDate, endDate))
        .build();
  }

  @PostMapping(value = "/flowdevicebynum")
  public Object getFlowDevice(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder().code(1).result(flowService.getFlowDevice(date, num)).build();
  }

  /**
   * 根据时间获取流量来源
   *
   * @author: lingjian @Date: 2019/5/14 14:11
   * @param date
   * @param num
   * @return
   */
  @PostMapping(value = "/flowsourcetype")
  public Object getFlowSourceType(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder().code(1).result(flowService.getFlowSourceType(date, num)).build();
  }

  /**
   * 根据时间获取流量概况(小时)
   *
   * @author: lingjian @Date: 2019/5/14 15:05
   * @param date
   * @return
   */
  @PostMapping(value = "/flowsourcetypetimebyhour")
  public Object getFlowSourceTypeTimeByHour(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowSourceTypeTimeByHour(date))
        .build();
  }

  /**
   * 根据时间获取流量概况(天)
   *
   * @author: lingjian @Date: 2019/5/15 11:45
   * @param date
   * @return
   */
  @PostMapping(value = "/flowsourcetypetimebyday")
  public Object getFlowSourceTypeTimeByDay(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, int num) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowSourceTypeTimeByDay(num, date))
        .build();
  }

  /**
   * 根据时间获取来源渠道
   *
   * @author: lingjian @Date: 2019/5/14 14:25
   * @param date
   * @param num
   * @return
   */
  @PostMapping(value = "/flowsourcepage")
  public Object getFlowSourcePage(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder().code(1).result(flowService.getFlowSourcePage(date, num)).build();
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/17 16:22
   * @param date
   * @param sourceType
   * @param sourcePage
   * @return
   */
  @PostMapping(value = "/flowsourcepagebyhour")
  public Object getFlowSourcePageByHour(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
      SourceTypeEnum sourceType,
      String sourcePage) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowSourcePageByHour(date, sourceType, sourcePage))
        .build();
  }

  /**
   * 根据流量来源，来源渠道名称，时间，获取来源渠道时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/17 16:29
   * @param date
   * @param sourceType
   * @param sourcePage
   * @param num
   * @return
   */
  @PostMapping(value = "/flowsourcepagebyday")
  public Object getFlowSourcePageByDay(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
      SourceTypeEnum sourceType,
      String sourcePage,
      int num) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowSourcePageByDay(num, date, sourceType, sourcePage))
        .build();
  }

  /**
   * 获取所有的页面类型
   *
   * @author: lingjian @Date: 2019/8/14 11:23
   * @param httpRequest 请求参数
   * @return Object对象
   */
  @GetMapping(value = "/flowpagetype")
  public Object getFlowPageType(HttpServletRequest httpRequest) {
    logger.debug(httpRequest);
    return MessageResult.builder().code(1).result(flowService.getFlowPageType()).build();
  }

  /**
   * 根据时间获取页面分析
   *
   * @author: lingjian @Date: 2019/5/14 16:26
   * @param date
   * @param num
   * @return
   */
  @PostMapping(value = "/flowpageanalysis")
  public Object getFlowPageAnalysis(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowPageAnalysis(date, num))
        .build();
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(小时)
   *
   * @author: lingjian @Date: 2019/5/15 15:13
   * @param date
   * @param access
   * @return
   */
  @PostMapping(value = "/flowpageanalysisbyhour")
  public Object getFlowPageAnalysisByHour(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, AccessTypeEnum access) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowPageAnalysisByHour(date, access))
        .build();
  }

  /**
   * 根据时间和页面分类，获取页面分析时段分析(天)
   *
   * @author: lingjian @Date: 2019/5/15 15:30
   * @param date
   * @param access
   * @return
   */
  @PostMapping(value = "/flowpageanalysisbyday")
  public Object getFlowPageAnalysisByDay(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, AccessTypeEnum access, Integer num) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .result(flowService.getFlowPageAnalysisByDay(num, date, access))
        .build();
  }

  /**
   * 根据时间获取流量概况参数（跳失率，平均浏览量，平均停留时长）
   *
   * @author: lingjian @Date: 2019/5/14 16:46
   * @param date
   * @return
   */
  @PostMapping(value = "/flowpage")
  public Object getFlowPage(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    logger.debug(date);
    return MessageResult.builder().code(1).result(flowService.getFlowPage(date, num)).build();
  }

  /**
   * 根据时间获取一个三十天中每一天的流量概况参数
   *
   * @author: lingjian @Date: 2019/5/15 14:26
   * @param date
   * @return
   */
  @PostMapping(value = "/flowpagebymonyh")
  public Object getFlowPageByMonth(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder().code(1).result(flowService.getFlowPageByMonth(date)).build();
  }
}

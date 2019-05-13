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
  @GetMapping(value = "/flowsource")
  public Object getFlowSource(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).msg("Hello").result(flowService.getFlowSource()).build();
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
}

package com.shoestp.mains.controllers.DataView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.DataView.FlowService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
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
   * @author: lingjian
   * @Date: 2019/5/10 16:45
   * @param httpRequest
   * @return
   */
  @GetMapping(value = "/flowsource")
  public Object getFlowSource(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).msg("Hello").result(flowService.getFlowSource()).build();
  }
}

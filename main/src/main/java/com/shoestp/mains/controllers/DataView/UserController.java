package com.shoestp.mains.controllers.DataView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.DataView.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户-控制器
 * @author: lingjian @Date: 2019/5/9 10:34
 */
@RestController
@RequestMapping("/api/platform")
public class UserController {
  private static final Logger logger = LogManager.getLogger(UserController.class);

  @Resource private UserService userService;

  @GetMapping(value = "/useroverview")
  public Object getUserOverview(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    userService.getUserOverview();
    return MessageResult.builder().code(1).msg("Hello").build();
  }
}

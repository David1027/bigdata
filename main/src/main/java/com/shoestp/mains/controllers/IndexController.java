package com.shoestp.mains.controllers;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataView.UserService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
public class IndexController {
  private static final Logger logger = LogManager.getLogger(IndexController.class);

  @Resource private UserService userService;

  @GetMapping(value = {"/", ""})
  public Object index(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    //    userService.test();
    return MessageResult.builder().code(1).msg("Hello").build();
  }
}

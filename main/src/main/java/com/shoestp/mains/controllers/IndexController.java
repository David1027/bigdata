package com.shoestp.mains.controllers;

import com.shoestp.mains.pojo.MessageResult;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
  private static final Logger logger = LogManager.getLogger(IndexController.class);

  @GetMapping(value = {"/", ""})
  public Object index(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).msg("Hello").build();
  }
}

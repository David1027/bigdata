package org.start2do.mains.controllers;

import com.sun.deploy.net.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RestController;
import org.start2do.mains.views.MessageResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {
  private static final Logger logger = LogManager.getLogger(IndexController.class);

  @GetMapping(value = {"/", ""})
  public Object index(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).msg("Hello").build();
  }
}

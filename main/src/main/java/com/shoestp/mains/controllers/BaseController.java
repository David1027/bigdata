package com.shoestp.mains.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 14:12 */
public class BaseController {

  private static final Logger logger = LogManager.getLogger(BaseController.class);

  protected String getIpByHeader(HttpServletRequest httpRequest) {
    Enumeration<String> enumeration = httpRequest.getHeaders("X-Forwarded-For");
    String ip = null;
    if (enumeration.hasMoreElements()) {
      ip = getFirstIp(enumeration.nextElement());
      logger.debug("X-Forwarded-For Header Info {}", ip);
      return ip;
    }
    logger.debug("Not Found Header Info:{}", "X-Forwarded-For");
    enumeration = httpRequest.getHeaders("X-Real-IP");
    if (enumeration.hasMoreElements()) {
      ip = getFirstIp(enumeration.nextElement());
      logger.debug("X-Real-IP Header Info ", ip);
      return ip;
    }
    logger.debug("Not Found Header Info:{}", "X-Real-IP");
    return httpRequest.getRemoteHost();
  }

  private String getFirstIp(String ip) {
    if (ip == null) {
      return ip;
    }
    return ip.split(",")[0];
  }

  protected String getUserAgentByHeader(HttpServletRequest httpRequest) {
    Enumeration<String> enumeration = httpRequest.getHeaders("user-agent");
    if (enumeration.hasMoreElements()) {
      return enumeration.nextElement();
    }
    return null;
  }
}

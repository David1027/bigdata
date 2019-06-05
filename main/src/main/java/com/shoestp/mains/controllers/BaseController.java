package com.shoestp.mains.controllers;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 14:12 */
public class BaseController {

  private static final Logger logger = LogManager.getLogger(BaseController.class);

  protected String getIpByHeader(HttpServletRequest httpRequest) {
    Enumeration<String> enumeration = httpRequest.getHeaders("X-Forwarded-For");
    String ip = null;
    Enumeration<String> stringEnumeration = httpRequest.getHeaderNames();
    // ;
    //      return ip;
    //    }hile (stringEnumeration.hasMoreElements()) {
    ////      String next = stringEnumeration.nextElement();
    ////      logger.debug("Header Info:{}  {}", next, httpRequest.getHeader(next));
    ////    }
    ////    if (enumeration.hasMoreElements()) {
    ////      ip = enumeration.nextElement();
    ////      logger.debug("X-Forwarded-For Header Info {}", ip);
    ////      return ip;
    ////    }
    //    logger.debug("Not Found Header Info:{}", "X-Forwarded-For");
    enumeration = httpRequest.getHeaders("X-Real-IP");
    if (enumeration.hasMoreElements()) {
      ip = enumeration.nextElement();
      logger.debug("X-Real-IP Header Info ", ip);
      return ip;
    }
    logger.debug("Not Found Header Info:{}", "X-Real-IP");
    return httpRequest.getRemoteHost();
  }

  protected String getUserAgentByHeader(HttpServletRequest httpRequest) {
    Enumeration<String> enumeration = httpRequest.getHeaders("user-agent");
    if (enumeration.hasMoreElements()) {
      return enumeration.nextElement();
    }
    return null;
  }
}

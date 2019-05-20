package com.shoestp.mains.controllers;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 14:12 */
public class BaseController {
  protected String getIpByHeader(HttpServletRequest httpRequest) {
    Enumeration<String> enumeration = httpRequest.getHeaders("X-Real-IP");
    if (enumeration.hasMoreElements()) {
      return enumeration.nextElement();
    }
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

package com.shoestp.mains.config.exceptionhandles;

import com.shoestp.mains.controllers.BaseController;
import com.shoestp.mains.pojo.MessageResult;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/22 Time: 14:18 */
@RestControllerAdvice
public class ShiroErr extends BaseController {
  private static final Logger logger = LogManager.getLogger(ShiroErr.class);
  /** * 捕捉shiro的异常 */
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(ShiroException.class)
  public MessageResult handle401(HttpServletRequest request, ShiroException e) {
    e.printStackTrace();
    logger.error(
        "Host:[{}]  X-Real-IP:[{}] .Visit url[{}]. Error Message: {}",
        request.getRemoteHost(),
        request.getHeader("X-Real-IP"),
        request.getRequestURI(),
        e.getMessage());
    return MessageResult.builder().code(-1).msg("未认证").build();
  }

  /** * 捕捉UnauthorizedException */
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  public MessageResult handle401() {
    return MessageResult.builder().code(-1).msg("未认证").build();
  }

  /** * 捕捉其他所有异常 */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public MessageResult globalException(HttpServletRequest request, Throwable ex) {
    logger.error("Visit Url:{}", request.getRequestURI());
    logger.error(
        "Request Info:{}    User-Agent:{}", getIpByHeader(request), getUserAgentByHeader(request));
    logger.error("Exception Info:{}", ex.getMessage());
    return MessageResult.builder().code(-1).msg("其他异常").build();
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }
}

package com.shoestp.mains.config.exceptionHandles;

import com.shoestp.mains.pojo.MessageResult;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

// @ControllerAdvice(basePackageClasses = AcmeController.class)
@ControllerAdvice
public class ExceptionHandleConfig {

  //  @ExceptionHandleConfigler(YourException.class)
  @ResponseBody
  MessageResult handleControllerException(HttpServletRequest request, Throwable ex) {
    HttpStatus status = getStatus(request);
    return MessageResult.builder().code(1).build();
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }
}

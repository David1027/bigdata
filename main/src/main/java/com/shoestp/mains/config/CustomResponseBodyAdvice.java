package com.shoestp.mains.config;

import com.shoestp.mains.pojo.MessageResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * The type Custom response body advice. 返回类自动包装MessageResult
 *
 * @author lijie
 * @date 2019 /08/06
 * @since
 */
@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice {
  @Override
  public boolean supports(MethodParameter methodParameter, Class aClass) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
      Object o,
      MethodParameter methodParameter,
      MediaType mediaType,
      Class aClass,
      ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse) {
    if (o instanceof MessageResult) {
      return o;
    }
    return MessageResult.builder().code(1).result(o).build();
  }
}

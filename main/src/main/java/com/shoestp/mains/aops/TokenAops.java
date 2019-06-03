package com.shoestp.mains.aops;

import com.shoestp.mains.aops.pojo.TokenCheck;
import com.shoestp.mains.pojo.MessageResult;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.start2do.utils.tokenutils.TokenUtils;
import org.start2do.utils.tokenutils.pojo.CheckTokenPojo;

@Aspect
public class TokenAops {
  @Pointcut("execution(* com.shoestp.mains.controllers.*.*())")
  public void tokencheck() {}

  @Before(value = " @annotation(tokencheck))")
  public Object cut(ProceedingJoinPoint joinPoint, TokenCheck tokencheck) throws Throwable {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String token = request.getHeader("Token");
    if (token == null || token.length() < 1) {
      return MessageResult.builder().code(-1).build();
    }
    if (!TokenUtils.checkToken(
        CheckTokenPojo.builder()
            .UA(request.getHeader("user-agent"))
            .IMME(request.getParameter("IMME"))
            .build(),
        token)) {
      return MessageResult.builder().code(-1).msg("").build();
    }
    return joinPoint.proceed();
  }
}

package org.start2do.mains.apos;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.start2do.mains.apos.pojo.TokenCheck;
import org.start2do.mains.utils.tokenUtils.TokenUtils;
import org.start2do.mains.utils.tokenUtils.pojo.CheckTokenPojo;
import org.start2do.mains.views.MessageResult;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class TokenAops {
  @Pointcut("execution(* org.start2do.mains.controllers.*.*()) &")
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
        token)) return MessageResult.builder().code(-1).build();
    return joinPoint.proceed();
  }
}

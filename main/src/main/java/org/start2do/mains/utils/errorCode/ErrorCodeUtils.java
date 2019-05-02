package org.start2do.mains.utils.errorCode;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.start2do.mains.utils.errorCode.langs.BaseLangs;

import javax.annotation.Resource;

@Component
public class ErrorCodeUtils {
  private BaseLangs baseLangs;

  @Resource private Environment environment;

  public String getResultMessage(int code, String lang) {
//      environment.getProperty()
      return null;
  }
}

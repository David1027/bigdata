package com.shoestp.mains.utils.errorcode;

import javax.annotation.Resource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.start2do.utils.classUtils.ClassUtils;

@Component
public class ErrorCodeUtils {
  private BaseLangs baseLangs;

  @Resource private Environment environment;

  public String getResultMessage(int code) {
    for (Class<?> packageClass :
        ClassUtils.getPackageClass(environment.getProperty("application.errorcode-path"), null)) {
      if (BaseLangs.class.isAssignableFrom(packageClass)) {}
    }
    return null;
  }
}

package com.shoestp.mains.utils.errorCode;

import com.shoestp.mains.utils.errorCode.langs.BaseLangs;
import javax.annotation.Resource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeUtils {
  private BaseLangs baseLangs;

  @Resource private Environment environment;

  public String getResultMessage(int code, String lang) {
//      environment.getProperty()
      return null;
  }
}

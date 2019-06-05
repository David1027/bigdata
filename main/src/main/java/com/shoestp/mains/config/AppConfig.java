package com.shoestp.mains.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/6/5 Time: 14:50 */
@Component
@ConfigurationProperties(prefix = "appconfig")
@Data
public class AppConfig {
  private List<String> domain;
}

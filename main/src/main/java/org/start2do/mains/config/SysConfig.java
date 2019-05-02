package org.start2do.mains.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class SysConfig {

  @Bean
  public HttpMessageConverter httpMessageConverter() {
    return new MappingJackson2HttpMessageConverter();
  }


}

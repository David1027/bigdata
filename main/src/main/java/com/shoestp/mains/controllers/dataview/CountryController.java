package com.shoestp.mains.controllers.dataview;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataview.CountryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 国家-控制器
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@RestController
@RequestMapping("/api/platform")
public class CountryController {
  private static final Logger logger = LogManager.getLogger(CountryController.class);

  @Resource private CountryService countryService;

  /**
   * 获取国家地区访客数
   *
   * @author: lingjian @Date: 2019/5/13 11:15
   * @param httpRequest 请求参数
   * @return Object
   */
  @GetMapping(value = "/countryarea")
  public Object getCountryArea(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(countryService.getCountryArea()).build();
  }
}

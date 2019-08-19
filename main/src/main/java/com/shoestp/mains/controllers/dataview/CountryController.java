package com.shoestp.mains.controllers.dataview;

import java.util.Date;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataview.CountryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
   * @param date 时间
   * @param num 天数类型
   * @return Object对象
   */
  @PostMapping(value = "/countryarea")
  public Object getCountry(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
      Integer num) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(countryService.getCountry(date, num)).build();
  }
}

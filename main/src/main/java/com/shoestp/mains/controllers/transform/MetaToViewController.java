package com.shoestp.mains.controllers.transform;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataview.CountryService;
import com.shoestp.mains.service.transform.MetaToViewService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 源数据转化展示数据 - 控制层
 * @author: lingjian
 * @create: 2019/8/6 13:53
 */
@RestController
@RequestMapping("/api/platform")
public class MetaToViewController {

  private static final Logger logger = LogManager.getLogger(MetaToViewController.class);

  @Resource private MetaToViewService metaToViewService;

  @GetMapping(value = "/tocountry")
  public Object toCountry(
      HttpServletRequest httpRequest,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder().code(1).result(metaToViewService.toCountry(start, end)).build();
  }
}

package com.shoestp.mains.controllers.DataView;

import javax.annotation.Resource;

import com.shoestp.mains.service.DataView.CountryService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 国家-控制器
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@RestController
@RequestMapping("/api/platform")
public class CountryController {
  private static final Logger logger = LogManager.getLogger(CountryController.class);

  @Resource private CountryService countryService;
}

package com.shoestp.mains.controllers.sellerdataview;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.dataview.CountryController;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.sellerdataview.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 商家后台用户表控制器
 * @author: lingjian
 * @create: 2019/5/24 11:30
 */
@RestController
@RequestMapping("/api/seller")
public class UserController {

  private static final Logger logger = LogManager.getLogger(CountryController.class);

  @Resource private UserService userService;


  @PostMapping(value = "/realvisitor")
  public Object getRealVisitor(
          @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, String country, String province,Integer supplierid) {
        logger.debug(date);
        return MessageResult.builder()
                .code(1)
                .msg("Hello")
                .result(userService.getRealVisitor(date, country,province,supplierid))
                .build();
    }
}

package com.shoestp.mains.controllers.DataView;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.DataView.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 用户-控制器
 * @author: lingjian @Date: 2019/5/9 10:34
 */
@RestController
@RequestMapping("/api/platform")
public class UserController {
  private static final Logger logger = LogManager.getLogger(UserController.class);

  @Resource private UserService userService;

  /**
   * 获取用户概况
   *
   * @author: lingjian @Date: 2019/5/13 14:48
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/useroverview")
  public Object getUserOverview(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(userService.getUserOverview(startDate, endDate))
        .build();
  }

//  @GetMapping(value = "/usertime")
//  public Object getUserTime(HttpServletRequest httpRequest) {
//    logger.debug(httpRequest.getCookies());
//    return MessageResult.builder()
//            .code(1)
//            .msg("Hello")
//            .result(userService.getUserTime())
//            .build();
//  }
}

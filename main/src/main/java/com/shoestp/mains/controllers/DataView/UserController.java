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
    logger.debug(startDate+"======="+endDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(userService.getUserOverview(startDate, endDate))
        .build();
  }

  /**
   * 根据时间获取用户概况中的时段分布(小时)
   *
   * @author: lingjian @Date: 2019/5/13 15:53
   * @param date
   * @return
   */
  @PostMapping(value = "/usertimebyhour")
  public Object getUserTimeByHour(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder().code(1).msg("Hello").result(userService.getUserTimeByHour(date)).build();
  }

  /**
   * 根据时间获取用户概况中的时段分布(天)
   * @author: lingjian
   * @Date: 2019/5/16 10:12
   * @param date
   * @return
   */
  @PostMapping(value = "/usertimebyday")
  public Object getUserTimeByDay(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date,Integer day) {
    logger.debug(date);
    return MessageResult.builder().code(1).msg("Hello").result(userService.getUserTimeByDay(date,day)).build();
  }

  /**
   * 根据时间获取用户性别数量
   *
   * @author: lingjian @Date: 2019/5/13 16:12
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/usersex")
  public Object getUserSex(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(userService.getUserSex(startDate, endDate))
        .build();
  }

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2019/5/13 16:34
   * @param startDate
   * @param endDate
   * @return
   */
  @PostMapping(value = "/userarea")
  public Object getUserArea(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    logger.debug(startDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(userService.getUserArea(startDate, endDate))
        .build();
  }
}

package com.shoestp.mains.controllers.DataView;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.entitys.DataView.DataViewReal;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.DataView.RealService;
import com.shoestp.mains.views.DataView.DataViewRealView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 实时-控制器
 * @author: lingjian @Date: 2019/5/9 10:21
 */
@RestController
@RequestMapping("/api/platform")
public class RealController {
  private static final Logger logger = LogManager.getLogger(RealController.class);

  @Resource private RealService realService;

  /**
   * 获取实时概况
   *
   * @author: lingjian @Date: 2019/5/9 15:45
   * @param httpRequest
   * @return
   */
  @GetMapping(value = "/realoverview")
  public Object getRealOverview(HttpServletRequest httpRequest) {
    logger.debug(httpRequest.getCookies());
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(realService.getRealOverview())
        .build();
  }

  /**
   * 获取实时趋势
   *
   * @author: lingjian @Date: 2019/5/9 15:51
   * @param date
   * @return
   */
  @PostMapping(value = "/realtrend")
  public Object getRealTrend(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    logger.debug(date);
    return MessageResult.builder().code(1).msg("Hello").result(realService.getRealTrend(date)).build();
  }
}

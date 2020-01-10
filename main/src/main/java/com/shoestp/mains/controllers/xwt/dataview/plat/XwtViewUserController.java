package com.shoestp.mains.controllers.xwt.dataview.plat;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.plat.dto.user.XwtUserDTO;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.dataview.plat.XwtViewUserService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户-控制器
 * @author: lingjian
 * @create: 2020/1/3 15:46
 */
@RestController
@RequestMapping("/xwt/plat/user/")
public class XwtViewUserController {

  @Resource private XwtViewUserService service;
  @Resource private XwtUserDTO dto;

  /**
   * 获取用户概况
   *
   * @author: lingjian @Date: 2020/1/3 15:48
   * @param date 时间
   * @return XwtUserVO 用户前端展示类
   */
  @PostMapping(value = "user_over_view")
  public Object getUserOverview(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(dto.toVo(service.getUserOverview(date, num)))
        .build();
  }

  /**
   * 根据时间获取用户概况中的时段分布(小时)
   *
   * @author: lingjian @Date: 2020/1/3 16:03
   * @param date 时间
   * @return Map<String, Map>
   */
  @PostMapping(value = "user_time_by_hour")
  public Object getUserTimeByHour(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(service.getUserTimeByHour(date))
        .build();
  }

  /**
   * 根据时间获取用户概况中的时段分布(天)
   *
   * @author: lingjian @Date: 2020/1/3 16:21
   * @param date 时间
   * @return Map<String, Map>
   */
  @PostMapping(value = "user_time_by_day")
  public Object getUserTimeByDay(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(service.getUserTimeByDay(date, num))
        .build();
  }

  /**
   * 根据时间获取用户地域分布
   *
   * @author: lingjian @Date: 2020/1/3 16:25
   * @param date 时间
   * @param num 天数
   * @return List<XwtUserAreaVO> 用户地域集合对象
   */
  @PostMapping(value = "user_area")
  public Object getUserArea(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(dto.toVoList(service.getUserArea(date, num)))
        .build();
  }
}

package com.shoestp.mains.controllers.xwt.transform;

import java.util.Date;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.transform.XwtTransformService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 源数据转化展示数据控制层
 * @author: lingjian
 * @create: 2019/12/31 15:32
 */
@RestController
@SuppressWarnings("ALL")
@RequestMapping("/xwt/transform/")
public class XwtTransformController {

  @Autowired
  private XwtTransformService service;

  /**
   * 源数据转化real表数据 - 实时表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewReal 实时表对象
   */
  @GetMapping(value = "/to_real")
  public Object toReal(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    return MessageResult.builder().code(1).result(service.toReal(start, end)).build();
  }

  /**
   * 源数据转化flow表数据 - 流量表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlow> flow流量表集合对象
   */
  @GetMapping(value = "/to_flow")
  public Object toFlow(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    return MessageResult.builder().code(1).result(service.toFlow(start, end)).build();
  }

  /**
   * 源数据转化flowpage表数据 - 页面分析表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlowPage> flowpage页面分析表集合对象
   */
  @GetMapping(value = "/to_flow_page")
  public Object toFlowPage(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    return MessageResult.builder().code(1).result(service.toFlowPage(start, end)).build();
  }

  /**
   * 源数据转化user表数据 - 用户表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewUser user用户表对象
   */
  @GetMapping(value = "/to_user")
  public Object toUser(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    return MessageResult.builder().code(1).result(service.toUser(start, end)).build();
  }

  /**
   * 源数据转化userarea表数据 - 用户地域表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewUserArea> userarea用户地域表集合对象
   */
  @GetMapping(value = "/to_user_area")
  public Object toUserArea(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    return MessageResult.builder().code(1).result(service.toUserArea(start, end)).build();
  }

  /**
   * 源数据转化country表数据 - 国家表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewCountry> country国家表集合对象
   */
  @GetMapping(value = "/to_country")
  public Object toCountry(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date end) {
    return MessageResult.builder().code(1).result(service.toCountry(start, end)).build();
  }
}

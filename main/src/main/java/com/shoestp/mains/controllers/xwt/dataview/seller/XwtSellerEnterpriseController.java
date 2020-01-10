package com.shoestp.mains.controllers.xwt.dataview.seller;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.seller.dto.XwtSellerEnterPriseDTO;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.dataview.seller.XwtSellerEnterpriseService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 鞋网通商家后台店铺控制层
 * @author: lingjian
 * @create: 2020/1/9 14:24
 */
@RestController
@RequestMapping("/xwt/seller/enterprise/")
public class XwtSellerEnterpriseController {

  @Resource private XwtSellerEnterpriseService service;
  @Resource private XwtSellerEnterPriseDTO dto;

  /**
   * 根据时间和店铺id获取店铺的数据
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param enterpriseId 店铺id
   * @return XwtSellerEnterPriseVO 店铺VO
   */
  @PostMapping("get_basic_data")
  public Object getBasicData(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
      Integer enterpriseId) {
    return MessageResult.builder()
        .code(1)
        .result(dto.toVo(service.getBasicData(start, end, enterpriseId)))
        .build();
  }

  /**
   * 根据时间和店铺id获取店铺数据走势(小时)
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param enterpriseId 店铺id
   * @return Map<String, Map>
   */
  @PostMapping("get_basic_data_by_hour")
  public Object getBasicDataByHour(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
      Integer enterpriseId) {
    return MessageResult.builder()
        .code(1)
        .result(service.getBasicDataByHour(start, end, enterpriseId))
        .build();
  }

  /**
   * 根据时间和店铺id获取店铺数据走势(天)
   *
   * @param start 开始时间
   * @param end 结束天数
   * @param enterpriseId 店铺id
   * @return Map<String, Map>
   */
  @PostMapping("get_basic_data_by_day")
  public Object getBasicDataByDay(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
      Integer enterpriseId) {
    return MessageResult.builder()
        .code(1)
        .result(service.getBasicDataByDay(start, end, enterpriseId))
        .build();
  }

  /**
   * 根据时间，国家分组获取店铺访客数-用户画像
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param enterpriseId 店铺id
   * @return
   */
  @PostMapping("get_basic_data_by_country")
  public Object getBasicDataByCountry(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
      Integer enterpriseId) {
    return MessageResult.builder()
        .code(1)
        .result(dto.toVoList(service.getBasicDataByCountry(start, end, enterpriseId)))
        .build();
  }
}

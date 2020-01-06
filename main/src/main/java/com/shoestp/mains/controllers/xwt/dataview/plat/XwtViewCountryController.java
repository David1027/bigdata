package com.shoestp.mains.controllers.xwt.dataview.plat;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.plat.dto.country.XwtCountryDTO;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.dataview.XwtViewCountryService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 国家-控制器
 * @author: lingjian
 * @create: 2020/1/3 9:10
 */
@RestController
@RequestMapping("/xwt/plat/country/")
public class XwtViewCountryController {

  @Resource private XwtViewCountryService service;
  @Resource private XwtCountryDTO dto;

  /**
   * 获取国家地区访客数
   *
   * @param date 时间
   * @param num 天数类型
   * @return List<XwtCountryVO> 国家前端展示类集合对象
   */
  @PostMapping(value = "country")
  public Object getCountry(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Integer num) {
    return MessageResult.builder()
        .code(1)
        .result(dto.toVoList(service.getCountry(date, num)))
        .build();
  }
}

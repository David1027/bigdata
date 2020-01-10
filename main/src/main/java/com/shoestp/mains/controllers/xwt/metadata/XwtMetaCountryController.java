package com.shoestp.mains.controllers.xwt.metadata;

import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.controllers.xwt.metadata.dto.XwtMetaCountryDTO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.metadata.XwtMetaAccessLogService;
import com.shoestp.mains.service.xwt.metadata.XwtMetaCountryService;
import com.shoestp.mains.utils.xwt.FieldUtils;
import com.shoestp.mains.utils.xwt.HttpRequestUtils;
import com.shoestp.mains.utils.xwt.StringFormatUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 国家控制层
 * @author: lingjian
 * @create: 2020/1/7 9:23
 */
@CrossOrigin
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/xwt/meta/date/country/")
public class XwtMetaCountryController {

  @Autowired private XwtMetaCountryService service;
  @Autowired private XwtMetaCountryDTO dto;

  /**
   * 获取国家选择框列表
   *
   * @author: lingjian @Date: 2020/1/7 9:28
   * @return
   */
  @GetMapping("get_country")
  public Object getCountry() {
    return MessageResult.builder().code(1).result(dto.toVoList(service.getCountry())).build();
  }
}

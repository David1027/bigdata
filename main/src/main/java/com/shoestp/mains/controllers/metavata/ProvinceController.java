package com.shoestp.mains.controllers.metavata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metadata.ProvinceService;

@RestController
@RequestMapping("/api/platform")
public class ProvinceController {

  @Autowired private ProvinceService provinceService;

  @GetMapping(value = "/getProvince")
  public Object getProvince() {
    return MessageResult.builder().code(1).msg("Hello").result(provinceService.getAll()).build();
  }
}

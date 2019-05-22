package com.shoestp.mains.controllers.metaData;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metaData.CountryService;

@RestController
@RequestMapping("/api/platform")
public class PltCountryController {

  @Resource(name = "pltCountryService")
  private CountryService countryService;

  @RequestMapping("/getCountry")
  public Object getCountry() {
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(countryService.getCountryList())
        .build();
  }
}

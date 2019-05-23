package com.shoestp.mains.controllers.metavata;

import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metadata.CountryService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform")
public class PltCountryController {

  @Resource(name = "pltCountryService")
  private CountryService countryService;

  @RequestMapping("/getCountry")
  public Object getCountry() {
    return MessageResult.builder().code(1).result(countryService.getCountryList()).build();
  }
}

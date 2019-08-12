package com.shoestp.mains.controllers.metavata;

import com.shoestp.mains.service.metadata.LocationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/platform")
public class PltCountryController {

  @Resource private LocationService locationService;

  @RequestMapping("/getCountry")
  public Object getCountry() {
    return locationService.getCountryList();
  }
}

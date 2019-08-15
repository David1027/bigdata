package com.shoestp.mains.service.metadata;

import base.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

public class LocationServiceTest extends BaseTest {

  @Resource private LocationService locationService;

  @Test
  void getCountryList() {
    Assert.assertNotNull(locationService.getCountryList());
  }

  @Test
  void getCountryByIp() {}

  @Test
  void getCountry() {
    Assert.assertEquals(locationService.getCountryByIp("111.206.198.47").getId(), new Integer(7));
  }

  @Test
  void getProvince() {}

  @Test
  void getAddress() {}
}

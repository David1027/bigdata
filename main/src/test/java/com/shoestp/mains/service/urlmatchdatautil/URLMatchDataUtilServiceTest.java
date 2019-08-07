package com.shoestp.mains.service.urlmatchdatautil;

import base.BaseTest;
import com.shoestp.mains.dao.urlmatchdatautil.URLMatchDataDao;
import com.shoestp.mains.entitys.urlmatchdatautil.URLMatchDataEntity;
import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

public class URLMatchDataUtilServiceTest extends BaseTest {

  @Resource private URLMatchDataDao matchDataDao;
  @Resource private URLMatchDataUtilService urlMatchDataUtilService;

  @BeforeEach
  void setUp() {
    URLMatchDataEntity entity = new URLMatchDataEntity();
    entity.setName("迪胜");
    entity.setRegex("http?://www.shoestp.com/activity/html/ds*");
    entity.setType(URLDataTypeEnum.LANDINGPAGE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("GOOGLE");
    entity.setRegex("http*//www.google.*");
    entity.setType(URLDataTypeEnum.SEARCHENGINE);
    matchDataDao.save(entity);
  }

  @Test
  void getLandingPageSupplierName() {
    String name =
        urlMatchDataUtilService.getLandingPageSupplierName(
            "https://www.shoestp.com/activity/html/ds");
    Assert.assertEquals(name, "迪胜");
  }

  @Test
  void getSourceType() {
    SourceTypeEnum type =
        urlMatchDataUtilService.getSourceType("http://www.google.com/?awdawdawdawdawudawdawaw");
    Assert.assertEquals(type, SourceTypeEnum.GOOGLE);
  }

  @Test
  void getSourceType2() {
    SourceTypeEnum type =
        urlMatchDataUtilService.getSourceType("https://www.google.com/?awdawdawdawdawudawdawaw");
    Assert.assertEquals(type, SourceTypeEnum.GOOGLE);
  }

  @Test
  void getSourceType3() {
    SourceTypeEnum type =
        urlMatchDataUtilService.getSourceType("httpwwws://www.google.com/?awdawdawdawdawudawdawaw");
    Assert.assertEquals(type, SourceTypeEnum.GOOGLE);
  }
}
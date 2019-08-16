package com.shoestp.mains.service.urlmatchdatautil;

import base.BaseTest;
import com.shoestp.mains.dao.urlmatchdatautil.URLMatchDataDao;
import com.shoestp.mains.entitys.urlmatchdatautil.URLMatchDataEntity;
import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * The type Url match data util service test.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /08/08
 * @since
 */
public class URLMatchDataUtilServiceTest extends BaseTest {

  /** The Match data dao. */
  @Resource private URLMatchDataDao matchDataDao;

  /** The Url match data util service. */
  @Resource private URLMatchDataUtilService urlMatchDataUtilService;

  /**
   * Sets up.
   *
   * @author lijie
   * @date 2019 /08/08
   * @since
   */
  @BeforeEach
  void setUp() {
    URLMatchDataEntity entity = new URLMatchDataEntity();
    entity.setName("迪胜");
    entity.setRegex("/activity/html/ds*");
    entity.setType(URLDataTypeEnum.LANDINGPAGE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("美廉美");
    entity.setRegex("/activity/html/mlm*");
    entity.setType(URLDataTypeEnum.LANDINGPAGE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("GOOGLE");
    entity.setRegex("http*//www.google.*");
    entity.setType(URLDataTypeEnum.SEARCHENGINE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("DETAIL");
    entity.setRegex("/*_p*.html");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("INDEX");
    entity.setRegex("/");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("INDEX");
    entity.setRegex("/home/usr_UsrPurchase");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("LIST");
    entity.setRegex("/home/pdt_PdtProduct?cated=*");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("INQUIRY");
    entity.setRegex("/home/usr_UsrSupplier_goContactSupplier*");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setPriority(10);
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("INTERVIEW");
    entity.setRegex("");
    entity.setType(URLDataTypeEnum.SEARCHENGINE);
    matchDataDao.save(entity);
  }

  /**
   * Gets landing page supplier name.
   *
   * @author lijie
   * @date 2019 /08/08
   * @since
   */
  @Test
  void getLandingPageSupplierName() {
    String name = urlMatchDataUtilService.getLandingPageSupplierName("/activity/html/ds");
    Assert.assertEquals(name, "迪胜");
    name = urlMatchDataUtilService.getLandingPageSupplierName("/activity/html/mlm");
    Assert.assertEquals(name, "美廉美");
  }

  /**
   * Gets source type.
   *
   * @author lijie
   * @date 2019 /08/08
   * @since
   */
  @Test
  void getSourceType() {
    SourceTypeEnum type =
        urlMatchDataUtilService.getSourceType("http://www.google.com/?awdawdawdawdawudawdawaw");
    Assert.assertEquals(type, SourceTypeEnum.GOOGLE);
    type = urlMatchDataUtilService.getSourceType("https://www.google.com/?awdawdawdawdawudawdawaw");
    Assert.assertEquals(type, SourceTypeEnum.GOOGLE);
    type = urlMatchDataUtilService.getSourceType("");
    Assert.assertEquals(type, SourceTypeEnum.INTERVIEW);
  }

  /**
   * Gets page type.
   *
   * @author lijie
   * @date 2019 /08/08
   * @since
   */
  @Test
  void getPageType() {
    AccessTypeEnum type =
        urlMatchDataUtilService.getPageType("/black-pu-piglet-peggy-sports-shoes_p9277.html");
    Assert.assertEquals(type, AccessTypeEnum.DETAIL);
    type = urlMatchDataUtilService.getPageType("/");
    Assert.assertEquals(type, AccessTypeEnum.INDEX);
    type = urlMatchDataUtilService.getPageType("/home/usr_UsrPurchase");
    Assert.assertEquals(type, AccessTypeEnum.INDEX);
    type = urlMatchDataUtilService.getPageType("/home/pdt_PdtProduct?cated=380");
    Assert.assertEquals(type, AccessTypeEnum.LIST);
    type =
        urlMatchDataUtilService.getPageType(
            "/home/usr_UsrSupplier_goContactSupplier?supplierPkey=298&backUrl=https://www.shoestp.com/women-shoes-ankle-boots-lady-boots_p9030.html");
    Assert.assertEquals(type, AccessTypeEnum.INQUIRY);
  }
}

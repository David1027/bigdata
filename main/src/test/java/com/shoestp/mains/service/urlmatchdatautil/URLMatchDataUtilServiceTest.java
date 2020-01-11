package com.shoestp.mains.service.urlmatchdatautil;

import javax.annotation.Resource;

import com.shoestp.mains.dao.urlmatchdatautil.URLMatchDataDao;
import com.shoestp.mains.entitys.urlmatchdatautil.URLMatchDataEntity;
import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import base.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    entity.setDescription("着陆页-迪胜");
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("美廉美");
    entity.setRegex("/activity/html/mlm*");
    entity.setType(URLDataTypeEnum.LANDINGPAGE);
    entity.setDescription("着陆页-mlm");

    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("GOOGLE");
    entity.setRegex("http*//www.google.*");
    entity.setType(URLDataTypeEnum.SEARCHENGINE);
    entity.setDescription("访问类型-Google搜索引擎");
    matchDataDao.save(entity);
    /** */
    entity = new URLMatchDataEntity();
    entity.setName("LIST");
    entity.setRegex("/home/pdt_PdtProduct?cated=*");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setDescription("页面-产品列表页");
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("INQUIRY");
    entity.setRegex("/home/usr_UsrSupplier_goContactSupplier*");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setPriority(10);
    entity.setDescription("页面-供应商询盘");
    matchDataDao.save(entity);
    entity = new URLMatchDataEntity();
    entity.setName("INTERVIEW");
    entity.setRegex("");
    entity.setType(URLDataTypeEnum.SEARCHENGINE);
    entity.setDescription("访问类型-自助访问");
    matchDataDao.save(entity);
    /** 填充数据 */
    /** 页面类型 */
    entity = new URLMatchDataEntity();
    entity.setName("SELLER_ADMIN");
    entity.setRegex("/newseller*");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setDescription("页面类型-商家后台");
    matchDataDao.save(entity);
    /** 产品详情 */
    entity = new URLMatchDataEntity();
    entity.setName("DETAIL");
    entity.setRegex("/*_p*.html");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setDescription("页面类型-产品详情页");
    matchDataDao.save(entity);
    /** 首页 */
    entity = new URLMatchDataEntity();
    entity.setName("INDEX");
    entity.setRegex("/");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setDescription("页面类型-首页");
    matchDataDao.save(entity);
    /** 商家用户中心 */
    entity = new URLMatchDataEntity();
    entity.setName("USER_REG");
    entity.setRegex("/home/usr_UsrMain_completeReg");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setDescription("页面类型-注册完成页");
    matchDataDao.save(entity);
    /** 用户消息中心 */
    entity = new URLMatchDataEntity();
    entity.setName("USER_MESSAGE_CENTER");
    entity.setRegex("/home/usr_UsrMessages_center");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setDescription("页面类型-用户消息中心");
    matchDataDao.save(entity);
    /** 平台 */
    entity = new URLMatchDataEntity();
    entity.setName("PLATFORM_ADMIN");
    entity.setRegex("/platform/*");
    entity.setType(URLDataTypeEnum.PAGETYPE);
    entity.setDescription("页面类型-平台");
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

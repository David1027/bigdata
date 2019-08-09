package com.shoestp.mains.service.urlmatchdatautil.impl;

import com.shoestp.mains.dao.urlmatchdatautil.URLMatchDataDao;
import com.shoestp.mains.entitys.urlmatchdatautil.URLMatchDataEntity;
import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.pojo.PageSourcePojo;
import com.shoestp.mains.service.urlmatchdatautil.URLMatchDataUtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.start2do.utils.MyStringUtils;

import javax.annotation.Resource;

/**
 * The type Url match data util service.
 *
 * @author lijie
 * @date 2019 /08/07
 * @since
 */
@Service
public class URLMatchDataUtilServiceImpl implements URLMatchDataUtilService {
  private static final Logger logger = LogManager.getLogger(URLMatchDataUtilServiceImpl.class);
  /** The Url match data dao. */
  @Resource private URLMatchDataDao urlMatchDataDao;
  /**
   * Gets land name. 传入Url获取 该着陆页商家名称
   *
   * @param uri the url
   * @return the land name
   * @author lijie
   * @date 2019 /08/07
   * @since *
   */
  @Override
  public String getLandingPageSupplierName(String uri) {
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.LANDINGPAGE)) {
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), uri)) {
        return urlMatchDataEntity.getName();
      }
    }
    return "";
  }

  @Override
  public PageSourcePojo getUrlType(String url) {
    PageSourcePojo pageSourcePojo = new PageSourcePojo();
    for (URLMatchDataEntity urlMatchDataEntity : urlMatchDataDao.findAll()) {
      switch (urlMatchDataEntity.getType()) {
        case LANDINGPAGE:
          pageSourcePojo.setSourcePage(getLandingPageSupplierName(url));
          break;
        case SEARCHENGINE:
        default:
          pageSourcePojo.setSourceType(getSourceType(url));
      }
    }
    return pageSourcePojo;
  }

  /**
   * Gets source type. 传入url,获取该url来自于那个搜索引擎
   *
   * @param url the url
   * @return the source type
   * @author lijie
   * @date 2019 /08/07
   * @since *
   */
  @Override
  public SourceTypeEnum getSourceType(String url) {
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.SEARCHENGINE)) {
      logger.debug("Name:{},Regex:{}", urlMatchDataEntity.getName(), urlMatchDataEntity.getRegex());
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), url)) {
        return SourceTypeEnum.valueOf(urlMatchDataEntity.getName());
      }
    }
    return SourceTypeEnum.OTHER;
  }
  /**
   * @title getPageType
   * @description 获取页面类型
   * @author Lijie HelloBox@outlook.com
   * @param: uri
   * @return: com.shoestp.mains.enums.flow.AccessTypeEnum
   */
  @Override
  public AccessTypeEnum getPageType(String uri) {
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.PAGETYPE)) {
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), uri)) {
        return AccessTypeEnum.valueOf(urlMatchDataEntity.getName());
      }
    }
    return AccessTypeEnum.OTHER;
  }
}

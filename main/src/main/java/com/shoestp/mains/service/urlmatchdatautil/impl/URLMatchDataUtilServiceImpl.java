package com.shoestp.mains.service.urlmatchdatautil.impl;

import com.shoestp.mains.dao.urlmatchdatautil.URLMatchDataDao;
import com.shoestp.mains.entitys.urlmatchdatautil.URLMatchDataEntity;
import com.shoestp.mains.entitys.urlmatchdatautil.enums.URLDataTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.enums.xwt.OProductEnum;
import com.shoestp.mains.pojo.PageSourcePojo;
import com.shoestp.mains.service.urlmatchdatautil.URLMatchDataUtilService;
import com.shoestp.mains.utils.xwt.EnumUtil;
import com.shoestp.mains.views.dataview.utils.KeyValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.start2do.utils.MyStringUtils;

import javax.annotation.Resource;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Url match data util service.
 *
 * @author lijie
 * @date 2019 /08/07
 * @since
 */
@Service
@CacheDefaults(cacheName = "15_Minutes")
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
  @CacheResult
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
  @CacheResult
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
  @CacheResult
  public SourceTypeEnum getSourceType(String url) {
    List<URLMatchDataEntity> list =
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.SEARCHENGINE);
    for (URLMatchDataEntity urlMatchDataEntity : list) {
      logger.debug("Name:{},Regex:{}", urlMatchDataEntity.getName(), urlMatchDataEntity.getRegex());
      if (url == null) {
        continue;
      }
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
  @CacheResult
  public AccessTypeEnum getPageType(String uri) {
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.PAGETYPE)) {
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), uri)) {
        return AccessTypeEnum.valueOf(urlMatchDataEntity.getName());
      }
    }
    return AccessTypeEnum.OTHER;
  }

  /**
   * 根据uri获取页面类型
   *
   * @author: lingjian
   * @create: 2020/1/2 16:25
   * @param uri 请求的uri
   * @return OAccessTypeEnum 鞋网通页面类型
   */
  @Override
  public OAccessTypeEnum getAccessType(String uri) {
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.PAGETYPE)) {
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), uri)) {
        if (EnumUtil.isInclude(urlMatchDataEntity.getName())) {
          return OAccessTypeEnum.OTHER;
        }
        return OAccessTypeEnum.valueOf(urlMatchDataEntity.getName());
      }
    }
    return OAccessTypeEnum.OTHER;
  }

  /**
   * Gets supplier pkey by url. 根据 Url 获取卖家的 Id,用于着陆页转换
   *
   * @param uri the uri
   * @author lijie
   * @date 2019 /09/16
   * @since *
   * @return the supplier pkey by url
   */
  @Override
  @CacheResult
  public Integer getSupplierPkeyByUrl(String uri) {
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.CONVERSION_URL_SUPPLIER_ID)) {
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), uri)) {
        return Integer.valueOf(urlMatchDataEntity.getName());
      }
    }
    return 0;
  }

  /**
   * 根据uri返回店铺的id或产品的id
   *
   * @author: lingjian @Date: 2020/1/8 13:54
   * @param uri 请求的uri
   * @return Map<String,Integer> <店铺名称-店铺id>或<产品名称-产品的id>
   */
  @Override
  public Map<OProductEnum, Integer> getProductIdByUrl(String uri) {
    Map<OProductEnum, Integer> map = new HashMap<>(16);
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.PAGETYPE)) {
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), uri)) {
        String name = urlMatchDataEntity.getName();
        if (EnumUtil.isInclude(name)) {
          OProductEnum product = OProductEnum.valueOf(urlMatchDataEntity.getName());
          int id;
          if (product.equals(OProductEnum.SHOP_PRODUCT)
              || product.equals(OProductEnum.MATERIAL_PRODUCT)) {
            id = Integer.parseInt(uri.substring(uri.indexOf("-") + 1, uri.indexOf(".")));
          } else {
            if (uri.contains("&")) {
              id = Integer.parseInt(uri.substring(uri.indexOf("se=") + 1, uri.indexOf("&")));
            } else {
              String substring = uri.substring(uri.indexOf("=") + 1);
              id = Integer.parseInt(substring);
            }
          }
          map.put(product, id);
          return map;
        }
        return null;
      }
    }
    return null;
  }

  /**
   * 根据uri返回搜索词
   *
   * @param uri url
   * @return String 搜索词
   */
  @Override
  public String getSearchTermByUrl(String uri) {
    for (URLMatchDataEntity urlMatchDataEntity :
        urlMatchDataDao.findByTypeOrderByPriorityDesc(URLDataTypeEnum.SEARCH)) {
      if (MyStringUtils.isMatch3(urlMatchDataEntity.getRegex(), uri)) {
        String substring = uri.substring(uri.indexOf("=") + 1);
        return substring;
      }
    }
    return null;
  }
}

package com.shoestp.mains.service.urlmatchdatautil;

import java.util.Map;

import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.enums.xwt.OProductEnum;
import com.shoestp.mains.pojo.PageSourcePojo;

/**
 * The interface Url match data util service.
 *
 * @author lijie
 * @date 2019 /08/07
 * @since
 */
public interface URLMatchDataUtilService {
  /**
   * Gets land name. 传入Url获取 该着陆页商家名称
   *
   * @author lijie
   * @date 2019 /08/07
   * @since *
   * @param uri the uri
   * @return the land name
   */
  String getLandingPageSupplierName(String uri);

  /**
   * Gets source type. 传入url,获取该url来自于那个搜索引擎
   *
   * @author lijie
   * @date 2019 /08/07
   * @since *
   * @param url the url
   * @return the source type
   */
  SourceTypeEnum getSourceType(String url);

  /**
   * Gets url type. 获取url的类型 统一返回 SourceTypeEnum 及页面来源
   *
   * @author lijie
   * @date 2019 /08/07
   * @since *
   * @param uri the uri
   * @return the url type
   */
  PageSourcePojo getUrlType(String uri);

  /**
   * Gets page type.
   *
   * @author lijie
   * @date 2019 /08/08
   * @since *
   * @param uri the uri
   * @return the page type
   */
  AccessTypeEnum getPageType(String uri);

  /**
   * 根据uri获取页面类型
   *
   * @author: lingjian @Date: 2020/1/8 13:51
   * @param uri 请求的uri
   * @return OAccessTypeEnum 鞋网通页面类型
   */
  OAccessTypeEnum getAccessType(String uri);

  /**
   * Gets supplier pkey by url. 根据 Url 获取卖家的 Id,用于着陆页转换
   *
   * @author lijie
   * @date 2019 /09/16
   * @since *
   * @param uri the uri
   * @return the supplier pkey by url
   */
  Integer getSupplierPkeyByUrl(String uri);

  /**
   * 根据uri返回店铺的id或产品的id
   *
   * @author: lingjian @Date: 2020/1/8 13:51
   * @param uri 请求的uri
   * @return Map<OProductEnum,Integer> <店铺名称-店铺id>或<产品名称-产品的id>
   */
  Map<OProductEnum, Integer> getProductIdByUrl(String uri);

  /**
   * 根据uri返回搜索词
   *
   * @param uri url
   * @return String 搜索词
   */
  String getSearchTermByUrl(String uri);
}

package com.shoestp.mains.service.urlmatchdatautil;

import com.shoestp.mains.enums.flow.SourceTypeEnum;
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
   * @param url the url
   * @return the land name
   */
  String getLandingPageSupplierName(String url);

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
   * Gets url type. 获取url的类型
   *
   * @author lijie
   * @date 2019 /08/07
   * @since *
   * @param url the url
   * @return the url type
   */
  PageSourcePojo getUrlType(String url);
}

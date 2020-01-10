package com.shoestp.mains.service.xwt.metadata;

import java.util.List;

import com.shoestp.mains.entitys.xwt.metadata.XwtMetaCountry;

/**
 * 国家表表服务层接口
 *
 * @author lingjian
 * @since 2019-12-31 09:52:45
 */
public interface XwtMetaCountryService {

  /**
   * 根据国家名称获取国家表id
   *
   * @param name 国家名称
   * @return Integer 国家表id
   */
  Integer getCountryId(String name);

  /**
   * 获取国家选择框列表
   *
   * @author: lingjian @Date: 2020/1/7 9:28
   * @return List<XwtMetaCountry> 国家表对象
   */
  List<XwtMetaCountry> getCountry();
}

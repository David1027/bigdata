package com.shoestp.mains.service.xwt.metadata;

/**
 * (XwtMetaProvince)表服务层接口
 *
 * @author lingjian
 * @since 2019-12-31 09:54:00
 */
public interface XwtMetaProvinceService {

  /**
   * 根据省份名称返回省表id
   *
   * @param name 省份名称
   * @return Integer 省表id
   */
  Integer getProvinceId(String name);
}

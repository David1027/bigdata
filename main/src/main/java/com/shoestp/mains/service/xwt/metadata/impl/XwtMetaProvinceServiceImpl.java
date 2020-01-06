package com.shoestp.mains.service.xwt.metadata.impl;

import com.shoestp.mains.dao.xwt.metadata.dao.XwtMetaProvinceDAO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaProvince;
import com.shoestp.mains.service.xwt.metadata.XwtMetaProvinceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (XwtMetaProvince)表服务层接口实现类
 *
 * @author lingjian
 * @since 2019-12-31 09:54:00
 */
@Service
@SuppressWarnings("ALL")
public class XwtMetaProvinceServiceImpl implements XwtMetaProvinceService {

  @Autowired private XwtMetaProvinceDAO dao;

  /**
   * 根据省份名称返回省表id
   *
   * @param name 省份名称
   * @return Integer 省表id
   */
  @Override
  public Integer getProvinceId(String name) {
    return dao.findByNameLike(name).map(XwtMetaProvince::getId).orElse(1);
  }
}

package com.shoestp.mains.service.xwt.metadata.impl;

import java.util.List;

import com.shoestp.mains.dao.xwt.metadata.dao.XwtMetaCountryDAO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaCountry;
import com.shoestp.mains.service.xwt.metadata.XwtMetaCountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 国家表服务层接口实现类
 *
 * @author lingjian
 * @since 2019-12-31 09:52:46
 */
@Service
@SuppressWarnings("ALL")
public class XwtMetaCountryServiceImpl implements XwtMetaCountryService {

  @Autowired private XwtMetaCountryDAO dao;

  /**
   * 根据国家名称获取国家表id
   *
   * @param name 国家名称
   * @return Integer 国家表id
   */
  @Override
  public Integer getCountryId(String name) {
    return dao.findByNameLike(name).map(XwtMetaCountry::getId).orElse(6);
  }

  /**
   * 获取国家选择框列表
   *
   * @author: lingjian @Date: 2020/1/7 9:28
   * @return List<XwtMetaCountry>
   */
  @Override
  public List<XwtMetaCountry> getCountry() {
    return dao.findAll();
  }
}

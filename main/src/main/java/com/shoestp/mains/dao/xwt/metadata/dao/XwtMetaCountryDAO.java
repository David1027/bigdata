package com.shoestp.mains.dao.xwt.metadata.dao;

import java.util.Optional;

import com.shoestp.mains.dao.xwt.metadata.repository.XwtMetaCountryRepository;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaCountry;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (XwtViewCountry)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 09:48:39
 */
public interface XwtMetaCountryDAO extends XwtMetaCountryRepository, JpaRepository<XwtMetaCountry, Integer> {

  /**
   * 根据国家名称返回国家表对象
   *
   * @param name 国家名称
   * @return Optional<XwtViewCountry> 国家表对象
   */
  Optional<XwtMetaCountry> findByNameLike(String name);
}

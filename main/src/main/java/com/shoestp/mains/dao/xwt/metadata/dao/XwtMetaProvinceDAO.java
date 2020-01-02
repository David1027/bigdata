package com.shoestp.mains.dao.xwt.metadata.dao;

import java.util.Optional;

import com.shoestp.mains.dao.xwt.metadata.repository.XwtMetaProvinceRepository;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaProvince;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (XwtMetaProvince)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 09:48:39
 */
public interface XwtMetaProvinceDAO extends XwtMetaProvinceRepository, JpaRepository<XwtMetaProvince, Integer> {

  /**
   * 根据省份名称获取省份表对象
   *
   * @param name 省份名称
   * @return Optional<XwtMetaProvince> 省份表对象
   */
  Optional<XwtMetaProvince> findByNameLike(String name);
}

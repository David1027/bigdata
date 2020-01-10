package com.shoestp.mains.dao.xwt.dataview.plat.dao;

import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewCountryRepository;
import com.shoestp.mains.entitys.xwt.dataview.plat.country.XwtViewCountry;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (Country)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewCountryDAO
    extends XwtViewCountryRepository, JpaRepository<XwtViewCountry, Integer> {}

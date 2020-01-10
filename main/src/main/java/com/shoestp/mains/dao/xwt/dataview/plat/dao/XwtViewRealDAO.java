package com.shoestp.mains.dao.xwt.dataview.plat.dao;

import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewRealRepository;
import com.shoestp.mains.entitys.xwt.dataview.plat.real.XwtViewReal;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (Real)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewRealDAO
    extends XwtViewRealRepository, JpaRepository<XwtViewReal, Integer> {}

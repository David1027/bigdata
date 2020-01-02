package com.shoestp.mains.dao.xwt.dataview.dao;

import com.shoestp.mains.dao.xwt.dataview.repository.XwtViewRealRepository;
import com.shoestp.mains.entitys.xwt.dataview.real.XwtViewReal;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (Real)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewRealDAO extends XwtViewRealRepository, JpaRepository<XwtViewReal, Integer> {}

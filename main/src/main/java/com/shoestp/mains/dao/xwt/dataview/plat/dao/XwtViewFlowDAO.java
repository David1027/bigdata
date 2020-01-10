package com.shoestp.mains.dao.xwt.dataview.plat.dao;


import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewFlowRepository;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlow;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (Flow)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewFlowDAO extends XwtViewFlowRepository, JpaRepository<XwtViewFlow, Integer> {}

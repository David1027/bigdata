package com.shoestp.mains.dao.xwt.dataview.dao;

import com.shoestp.mains.dao.xwt.dataview.repository.XwtViewFlowPageRepository;
import com.shoestp.mains.entitys.xwt.dataview.flow.XwtViewFlowPage;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (FlowPage)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewFlowPageDAO
    extends XwtViewFlowPageRepository, JpaRepository<XwtViewFlowPage, Integer> {}

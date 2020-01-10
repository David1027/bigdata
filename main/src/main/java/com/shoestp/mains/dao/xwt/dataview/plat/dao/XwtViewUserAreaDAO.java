package com.shoestp.mains.dao.xwt.dataview.plat.dao;

import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewUserAreaRepository;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUserArea;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (XwtDataViewUserArea)表数据访问层接口
 *
 * @author lingjian
 * @since 2020-01-02 14:01:10
 */
public interface XwtViewUserAreaDAO
    extends XwtViewUserAreaRepository, JpaRepository<XwtViewUserArea, Integer> {}

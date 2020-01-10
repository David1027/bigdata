package com.shoestp.mains.dao.xwt.dataview.plat.dao;

import com.shoestp.mains.dao.xwt.dataview.plat.repository.XwtViewUserRepository;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUser;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (User)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewUserDAO extends XwtViewUserRepository, JpaRepository<XwtViewUser, Integer> {}

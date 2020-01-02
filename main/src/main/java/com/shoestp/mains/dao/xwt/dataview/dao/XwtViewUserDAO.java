package com.shoestp.mains.dao.xwt.dataview.dao;

import com.shoestp.mains.dao.xwt.dataview.repository.XwtViewUserRepository;
import com.shoestp.mains.entitys.xwt.dataview.user.XwtViewUser;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (User)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-31 15:22:23
 */
public interface XwtViewUserDAO extends XwtViewUserRepository, JpaRepository<XwtViewUser, Integer> {}

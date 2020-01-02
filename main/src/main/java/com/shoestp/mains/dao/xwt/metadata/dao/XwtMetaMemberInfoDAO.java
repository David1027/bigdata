package com.shoestp.mains.dao.xwt.metadata.dao;

import java.util.Optional;

import com.shoestp.mains.dao.xwt.metadata.repository.XwtMetaMemberInfoRepository;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaMemberInfo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (XwtMetaMemberInfo)表数据访问层接口
 *
 * @author lingjian
 * @since 2019-12-30 15:00:13
 */
public interface XwtMetaMemberInfoDAO extends XwtMetaMemberInfoRepository, JpaRepository<XwtMetaMemberInfo, Integer> {

  /**
   * 根据访客id获取用户信息表记录
   *
   * @param uvId 访客id
   * @return Optional<XwtMetaMemberInfo> 用户信息对象
   */
  Optional<XwtMetaMemberInfo> findByUvId(String uvId);
}

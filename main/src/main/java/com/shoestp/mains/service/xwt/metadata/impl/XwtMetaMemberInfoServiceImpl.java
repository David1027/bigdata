package com.shoestp.mains.service.xwt.metadata.impl;

import java.util.Date;

import com.shoestp.mains.dao.xwt.metadata.dao.XwtMetaMemberInfoDAO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaMemberInfo;
import com.shoestp.mains.service.xwt.metadata.XwtMetaMemberInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 用户信息服务层接口实现类
 * @author: lingjian
 * @create: 2019/12/30 15:03
 */
@Service
@SuppressWarnings("ALL")
public class XwtMetaMemberInfoServiceImpl implements XwtMetaMemberInfoService {

  @Autowired
  private XwtMetaMemberInfoDAO dao;

  /**
   * 保存用户信息对象
   *
   * @param xwtMetaMemberInfo 用户信息对象
   * @return Integer 用户信息表id
   */
  @Override
  public Integer save(XwtMetaMemberInfo xwtMetaMemberInfo) {
    // 设置创建时间
    xwtMetaMemberInfo.setCreateTime(new Date());
    // 保存对象并返回id
    return dao.save(xwtMetaMemberInfo).getId();
  }

  /**
   * 根据访客id获取用户信息表的id
   *
   * @param uvId 访客id
   * @return Integer 用户信息表id
   */
  @Override
  public Integer getUserInfoIdByUvId(String uvId) {
    return dao.findByUvId(uvId).map(XwtMetaMemberInfo::getId).orElse(0);
  }
}

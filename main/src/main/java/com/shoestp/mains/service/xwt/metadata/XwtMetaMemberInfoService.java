package com.shoestp.mains.service.xwt.metadata;

import com.shoestp.mains.entitys.xwt.metadata.XwtMetaMemberInfo;

/**
 * @description: 用户信息服务层接口
 * @author: lingjian
 * @create: 2019/12/30 15:03
 */
public interface XwtMetaMemberInfoService {

  /**
   * 保存用户信息
   *
   * @param xwtMetaMemberInfo 用户信息对象
   * @return Integer 用户信息表id
   */
  Integer save(XwtMetaMemberInfo xwtMetaMemberInfo);

  /**
   * 根据访客id获取用户信息表的id
   *
   * @param uvId 访客id
   * @return Integer 用户信息表id
   */
  XwtMetaMemberInfo getUserInfoIdByUvId(String uvId);
}

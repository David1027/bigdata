package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.controllers.analytics.view.pojo.UserInfoPojo;
import com.shoestp.mains.dao.metadata.UserInfoDao;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.service.metadata.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
  @Resource private UserInfoDao userInfoDao;

  @Override
  public void save(UserInfo u) {}

  @Override
  public UserInfo save(UserInfoPojo pojo) {
    UserInfo info = null;
    Optional<UserInfo> result;
    /** 先判断是否登录用户 */
    if (pojo == null || pojo.getUserId() == null) {
      /** 如果是非登录用户,那么查询用户签名,以前存在是否有记录 */
      result = userInfoDao.findBySign(pojo.getUserName());
      if (result.isPresent()) {
        info = result.get();
        info.setLastVisitTime(new Date());
        return info;
      }
      /** 不存在的情况下,保存记录 */
      info = new UserInfo();
      info.setSign(pojo.getUserName());
      info.setType(RegisterTypeEnum.VISITOR);
      info.setLastVisitTime(new Date());
      return userInfoDao.save(info);
    } else {
      result = userInfoDao.findByUserId(pojo.getUserId());
      if (result.isPresent()) {
        info = result.get();
        info.setLastVisitTime(new Date());
        return info;
      }
      info = new UserInfo();
//      info.setUserId(pojo.getUserId());
      info.setName(pojo.getUserName());
      info.setLastVisitTime(new Date());
      return userInfoDao.save(info);
    }
  }
}

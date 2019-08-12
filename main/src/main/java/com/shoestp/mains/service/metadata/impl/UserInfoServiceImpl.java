package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.controllers.analytics.view.pojo.UserInfoPojo;
import com.shoestp.mains.dao.metadata.UserInfoDao;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.entitys.metadata.enums.SexEnum;
import com.shoestp.mains.entitys.metadata.enums.UserStatus;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.service.metadata.UserInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
  private static final Logger logger = LogManager.getLogger(UserInfoServiceImpl.class);
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
      info.setCreateTime(new Date());
      return userInfoDao.save(info);
    } else {
      /** 查询存在的用户 */
      result = userInfoDao.findByUserId(pojo.getUserId());
      if (result.isPresent()) {
        info = result.get();
        info.setLastVisitTime(new Date());
        return info;
      }
      /** 一般情况下不存在 注册用户不存在的情况 */
      logger.error("请检查注册推送,或者该ID数据{},或者执行同步数据操作", pojo.getUserId());
      info = new UserInfo();
      info.setName(pojo.getUserName());
      info.setLastVisitTime(new Date());
      info.setCreateTime(new Date());
      return userInfoDao.save(info);
    }
  }

  @Override
  public UserInfo getUser(String sign) {
    Optional<UserInfo> result = userInfoDao.findBySign(sign);
    if (result.isPresent()) {
      return result.get();
    }
    UserInfo info = new UserInfo();
    info.setSign(sign);
    info.setType(RegisterTypeEnum.VISITOR);
    info.setLastVisitTime(new Date());
    info.setCreateTime(new Date());
    return userInfoDao.save(info);
  }

  @Override
  public void save(GRPC_SendDataProto.UserInfo info) {
    com.shoestp.mains.entitys.metadata.UserInfo userInfo;
    Optional<com.shoestp.mains.entitys.metadata.UserInfo> result =
        userInfoDao.findBySign(info.getSign());
    if (result.isPresent()) {
      userInfo = result.get();
    } else {
      userInfo = new com.shoestp.mains.entitys.metadata.UserInfo();
    }
    switch (info.getSex()) {
      case 1:
        userInfo.setSex(SexEnum.MAN);
        break;
      case 2:
        userInfo.setSex(SexEnum.WOMAN);
        break;
      case 0:
      default:
        userInfo.setSex(SexEnum.UNKNOWN);
    }
    /** 判断用户类型 */
    switch (info.getType()) {
      case 0:
        userInfo.setType(RegisterTypeEnum.PURCHASE);
        break;
      case 1:
        userInfo.setType(RegisterTypeEnum.SUPPLIER);
        break;
    }
    switch (info.getStatus()) {
      case 1:
        userInfo.setStatus(UserStatus.VERIFY);
        break;
      default:
        userInfo.setStatus(UserStatus.UNVERIFY);
    }
    userInfo.setName(info.getName());
    userInfo.setCreateTime(new Date(info.getCreateDate()));
    userInfo.setLastVisitTime(new Date());
    userInfoDao.save(userInfo);
  }

  @Override
  public UserInfo getUserInfo(Integer userId, String sign) {
    Optional<com.shoestp.mains.entitys.metadata.UserInfo> result;
    if (userId > 0) {
      result = userInfoDao.findByUserId(userId);
      if (result.isPresent()) {
        return result.get();
      }
    }
    return getUser(sign);
  }

  /**
   * Sync user info 同步用户信息
   *
   * @param info the info
   * @author lijie
   * @date 2019 /08/09
   * @since .
   */
  @Override
  public void syncUserInfo(GRPC_SendDataProto.UserInfo info) {
    if (!userInfoDao.findById(info.getUserId()).isPresent()) {
      save(info);
    }
  }

  /**
   * Update 更新用户
   *
   * @param userInfo the user info
   * @author lijie
   * @date 2019 /08/09
   * @since .
   */
  @Override
  public void update(UserInfo userInfo) {
    userInfoDao.saveAndFlush(userInfo);
  }
}

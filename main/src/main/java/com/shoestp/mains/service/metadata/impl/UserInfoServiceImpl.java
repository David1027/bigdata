package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.controllers.analytics.view.pojo.UserInfoPojo;
import com.shoestp.mains.dao.metadata.UserInfoDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.Province;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.entitys.metadata.enums.SexEnum;
import com.shoestp.mains.entitys.metadata.enums.UserStatus;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.service.metadata.LocationService;
import com.shoestp.mains.service.metadata.UserInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * The type User info service.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /08/28
 * @since
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
  /** The constant logger. */
  private static final Logger logger = LogManager.getLogger(UserInfoServiceImpl.class);

  /** The User info dao. */
  @Resource private UserInfoDao userInfoDao;

  /** The Location service. */
  @Resource private LocationService locationService;

  @Override
  public void save(UserInfo u) {}

  @Override
  public UserInfo save(UserInfoPojo pojo) {
    return save(pojo, null, null);
  }

  @Override
  public UserInfo getUser(String sign) {
    if (sign == null || sign.length() < 1) {
      return null;
    }
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
    Optional<com.shoestp.mains.entitys.metadata.UserInfo> result;
    /** 如果有签名的情况下根据签名获取数据 */
    if (info.getSign().length() > 1) {
      result = userInfoDao.findBySign(info.getSign());
    } else {
      if (info.getUserId() < 1) {
        return;
      }
      result = userInfoDao.findByUserId(info.getUserId());
    }

    if (result.isPresent()) {
      userInfo = result.get();
    } else {
      userInfo = new com.shoestp.mains.entitys.metadata.UserInfo();
    }
    /** 获取用户类型 */
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
    /** 设置用户状态 */
    switch (info.getStatus()) {
      case 1:
        userInfo.setStatus(UserStatus.VERIFY);
        break;
      default:
        userInfo.setStatus(UserStatus.UNVERIFY);
    }
    /** 设置国家地址 */
    userInfo.setCountry(locationService.getCountryByShortName(info.getCountry()));
    userInfo.setUserId(info.getUserId());
    userInfo.setName(info.getName());
    if (userInfo.getCreateTime() == null) {
      userInfo.setCreateTime(new Date(info.getCreateDate()));
    }
    if (userInfo.getLastVisitTime() == null) {
      userInfo.setLastVisitTime(new Date());
    }
    userInfoDao.saveAndFlush(userInfo);
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
   * <p>同步用户资料,具体逻辑如下: 当用户不存在,添加用户, 当用户存在,更新用户资料.
   *
   * @param info the info
   * @author lijie
   * @date 2019 /08/09
   * @since .
   */
  @Override
  public void syncUserInfo(GRPC_SendDataProto.UserInfo info) {
    save(info);
  }

  /**
   * Update 更新用户
   *
   * @author lijie
   * @date 2019 /08/09
   * @since . info.
   * @param userInfo the user info
   * @return the user info
   */
  @Override
  public UserInfo update(UserInfo userInfo) {
    return userInfoDao.saveAndFlush(userInfo);
  }

  /**
   * Save
   *
   * @param pojo the user info
   * @param country the location
   * @param province the province
   * @return the user info
   * @author lijie
   * @date 2019 /08/28
   * @since user info.
   */
  @Override
  public UserInfo save(UserInfoPojo pojo, PltCountry country, Province province) {
    UserInfo info = null;
    Optional<UserInfo> result;
    /** 先判断是否登录用户 */
    if (pojo == null || pojo.getUserId() == null) {
      /** 如果是非登录用户,那么查询用户签名,以前存在是否有记录 */
      result = userInfoDao.findBySign(pojo.getUserName());
      if (result.isPresent()) {
        info = result.get();
        info.setLastVisitTime(new Date());
        info.setCountry(country);
        info.setProvince(province);
        return info;
      }
      /** 不存在的情况下,保存记录 */
      info = new UserInfo();
      info.setSign(pojo.getUserName());
      info.setType(RegisterTypeEnum.VISITOR);
      info.setLastVisitTime(new Date());
      info.setCreateTime(new Date());
      info.setCountry(country);
      info.setProvince(province);
      return userInfoDao.save(info);
    } else {
      /** 查询存在的用户 */
      result = userInfoDao.findByUserId(pojo.getUserId());
      if (result.isPresent()) {
        info = result.get();
        info.setLastVisitTime(new Date());
        info.setCountry(country);
        info.setProvince(province);
        return update(info);
      }
      /** 一般情况下不存在 注册用户不存在的情况 */
      logger.error("请检查注册推送,或者该ID数据{},或者执行同步数据操作", pojo.getUserId());
      info = new UserInfo();
      info.setName(pojo.getUserName());
      info.setLastVisitTime(new Date());
      info.setCreateTime(new Date());
      info.setCountry(country);
      info.setProvince(province);
      return userInfoDao.save(info);
    }
  }
}

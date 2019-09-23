package com.shoestp.mains.service.metadata;

import com.shoestp.mains.controllers.analytics.view.pojo.UserInfoPojo;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.Province;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;

/**
 * The interface User info service.
 *
 * @author lijie
 * @date 2019 /08/09
 * @since
 */
public interface UserInfoService {
  /**
   * Save 保存用户
   *
   * @author lijie
   * @date 2019 /08/09
   * @since .
   * @param u the u
   */
  void save(UserInfo u);

  /**
   * Save 保存用户
   *
   * @author lijie
   * @date 2019 /08/09
   * @since user info.
   * @param userInfo the user info
   * @return the user info
   */
  UserInfo save(UserInfoPojo userInfo);

  /**
   * Gets user. 根据sign获取用户
   *
   * @author lijie
   * @date 2019 /08/09
   * @since *
   * @param sign the sign
   * @return the user
   */
  UserInfo getUser(String sign);

  /**
   * Save
   *
   * @author lijie
   * @date 2019 /08/09
   * @since .
   * @param info the info
   */
  void save(GRPC_SendDataProto.UserInfo info);
  /**
   * Get user info 根据用户的Id或者Sign获取签名
   *
   * @author lijie
   * @date 2019 /08/09
   * @since user info.
   * @param userId the user id
   * @param sign the sign
   * @return the user info
   */
  UserInfo getUserInfo(Integer userId, String sign);

  /**
   * Sync user info 同步用户信息
   *
   * @author lijie
   * @date 2019 /08/09
   * @since .
   * @param info the info
   */
  void syncUserInfo(GRPC_SendDataProto.UserInfo info);

  /**
   * Update 更新用户
   *
   * @author lijie
   * @date 2019 /08/09
   * @since . info.
   * @param userInfo the user info
   * @return the user info
   */
  UserInfo update(UserInfo userInfo);

  /**
   * Save
   *
   * @author lijie
   * @date 2019 /08/28
   * @since user info.
   * @param userInfo the user info
   * @param location the location
   * @param province the province
   * @return the user info
   */
  UserInfo save(UserInfoPojo userInfo, PltCountry location, Province province);

  /**
   * Remove duplicate user
   *
   * @author lijie
   * @date 2019 /09/20
   * @since .
   */
  void removeDuplicateUser();
}

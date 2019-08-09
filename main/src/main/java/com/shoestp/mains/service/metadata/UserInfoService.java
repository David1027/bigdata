package com.shoestp.mains.service.metadata;

import com.shoestp.mains.controllers.analytics.view.pojo.UserInfoPojo;
import com.shoestp.mains.entitys.metadata.UserInfo;

public interface UserInfoService {
  void save(UserInfo u);

  UserInfo save(UserInfoPojo userInfo);
}

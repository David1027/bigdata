package com.shoestp.mains.service.imp;

import com.shoestp.mains.service.UserService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/8 Time: 9:54 */
@Service
public class UserServiceImp implements UserService {

  @Resource private com.shoestp.mains.dao.UserDao userDao;

  @Override
  public void test() {
    userDao.test();
  }
}

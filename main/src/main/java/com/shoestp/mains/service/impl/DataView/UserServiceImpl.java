package com.shoestp.mains.service.impl.DataView;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.UserDao;
import com.shoestp.mains.service.DataView.UserService;

import org.springframework.stereotype.Service;

/**
 * @description: 用户-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:22
 */
@Service
public class UserServiceImpl implements UserService {

  @Resource private UserDao userDao;
}

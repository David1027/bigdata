package com.shoestp.mains.service.sellerdataview.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.sellerdataview.user.UserDao;
import com.shoestp.mains.service.sellerdataview.UserService;

import org.springframework.stereotype.Service;

/**
 * @description: 商家后台用户-服务层实现类
 * @author: lingjian
 * @create: 2019/5/24 11:48
 */
@Service
public class UserServiceImpl implements UserService {

  @Resource private UserDao userDao;

  @Override
  public List getRealVisitor(Date date, String country, String province, Integer supplierid) {
    return userDao.findUser(date,country,province,supplierid);
  }
}

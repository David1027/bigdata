package com.shoestp.mains.dao.DataView;

import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.DataViewUser;
import com.shoestp.mains.repositorys.DataView.UserRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 用户-数据层
 * @author: lingjian @Date: 2019/5/9 10:29
 */
@Repository
public class UserDao extends BaseDao<DataViewUser> {

  @Resource private UserRepository userRepository;



  @Override
  public DataViewUser find(DataViewUser dataViewUser) {
    return null;
  }

  @Override
  public DataViewUser findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewUser dataViewUser) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewUser> list) {
    return 0;
  }

  @Override
  public int remove(DataViewUser dataViewUser) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

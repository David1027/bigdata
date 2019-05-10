package com.shoestp.mains.dao;


import com.shoestp.mains.entitys.QUser;
import com.shoestp.mains.entitys.User;
import com.shoestp.mains.repositorys.UserRepository;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/8 Time: 9:26 */
@Repository
public class UserDao extends BaseDao<User> {

  @Resource private UserRepository repository;

  public void test() {
    System.out.println(repository.findById(1));
    System.out.println(getQuery().select(QUser.user1).where(QUser.user1.id.eq(1)).fetchAll());
  }

  @Override
  public User find(User user) {
    return null;
  }

  @Override
  public User findById(Integer id) {
    return null;
  }

  @Override
  public int update(User user) {
    return 0;
  }

  @Override
  public int updateByList(List<User> list) {
    return 0;
  }

  @Override
  public int remove(User user) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

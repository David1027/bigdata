package com.shoestp.mains.dao.transform;

import java.util.Date;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QUserInfo;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;

import org.springframework.stereotype.Repository;

/**
 * @description: 用户注册 - 数据访问层
 * @author: lingjian @Date: 2019/8/6 17:05
 */
@Repository
public class NewUserInfoDao extends BaseDao<UserInfo> {

  /**
   * 根据注册类型，时间获取注册量
   *
   * @author: lingjian @Date: 2019/8/8 15:48
   * @param registerTypeEnum 注册类型
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countRegister(RegisterTypeEnum registerTypeEnum, Date start, Date end) {
    QUserInfo qUserInfo = QUserInfo.userInfo;
    JPAQuery<UserInfo> query =
        getQuery()
            .select(qUserInfo)
            .where(qUserInfo.createTime.between(start, end))
            .from(qUserInfo);
    if (registerTypeEnum != null) {
      query.where(qUserInfo.type.eq(registerTypeEnum));
    } else {
      query.where(qUserInfo.type.ne(RegisterTypeEnum.VISITOR));
    }
    return (int) query.fetchCount();
  }

  public List<UserInfo> getRegisterList(Date start, Date end) {
    QUserInfo qUserInfo = QUserInfo.userInfo;
    return getQuery()
        .select(qUserInfo)
        .where(qUserInfo.createTime.between(start, end))
        .from(qUserInfo)
        .fetchResults()
        .getResults();
  }

  @Override
  public UserInfo find(UserInfo userInfo) {
    return null;
  }

  @Override
  public UserInfo findById(Integer id) {
    return null;
  }

  @Override
  public int update(UserInfo userInfo) {
    return 0;
  }

  @Override
  public int updateByList(List<UserInfo> list) {
    return 0;
  }

  @Override
  public int remove(UserInfo userInfo) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

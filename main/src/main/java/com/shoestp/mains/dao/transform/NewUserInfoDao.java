package com.shoestp.mains.dao.transform;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QUserInfo;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.entitys.metadata.enums.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
      query
          .where(qUserInfo.type.eq(registerTypeEnum))
          .where(qUserInfo.status.eq(UserStatus.VERIFY));
    } else {
      query.where(qUserInfo.type.ne(RegisterTypeEnum.VISITOR));
    }
    return (int) query.fetchCount();
  }

  /**
   * 根据时间获取注册表记录
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<UserInfo> 注册表集合对象
   */
  public List<UserInfo> getRegisterList(Date start, Date end) {
    QUserInfo qUserInfo = QUserInfo.userInfo;
    return getQuery()
        .select(qUserInfo)
        .where(qUserInfo.createTime.between(start, end))
        .from(qUserInfo)
        .fetchResults()
        .getResults();
  }
}

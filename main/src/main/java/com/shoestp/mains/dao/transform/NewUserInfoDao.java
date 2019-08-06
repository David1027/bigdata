package com.shoestp.mains.dao.transform;

import java.util.Date;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.*;
import com.shoestp.mains.entitys.metadata.enums.EquipmentPlatform;

import org.springframework.stereotype.Repository;

/**
 * @description: 用户注册 - 数据访问层
 * @author: lingjian @Date: 2019/8/6 17:05
 */
@Repository
public class NewUserInfoDao extends BaseDao<UserInfo> {

  /**
   * 根据设备来源,国家，时间获取访客数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countRegister(Date start, Date end) {
    QUserInfo qUserInfo = QUserInfo.userInfo;
    return (int)
        getQuery()
            .select(qUserInfo)
            .where(qUserInfo.createTime.between(start, end))
            .from(qUserInfo)
            .fetchCount();
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

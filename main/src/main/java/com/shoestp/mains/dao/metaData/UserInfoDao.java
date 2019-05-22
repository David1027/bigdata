package com.shoestp.mains.dao.metaData;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shoestp.mains.entitys.MetaData.UserInfo;
import com.shoestp.mains.enums.user.SexEnum;

public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {

  @Query(
      value =
          "SELECT country,count(*) as count  FROM `user_info` where create_time > ?1 AND create_time <= ?2 GROUP BY country ",
      nativeQuery = true)
  public List<Object> getCountryAndCount(Date startTime, Date endTime);

  public long countByCountryLikeCreateTimeLessThan(String country, Date createTime);

  @Query(
      value =
          "SELECT count(*) FROM `user_info` WHERE type = ?1 and create_time < ?3"
              + "UNION "
              + "SELECT count(*) FROM `user_info` WHERE type = ?2 and create_time < ?3 ",
      nativeQuery = true)
  public List<Object> getCount(String purchase, String supplier, Date endTime);

  public long countBySexAndCreateTimeLessThan(SexEnum sex, Date createTime);
}

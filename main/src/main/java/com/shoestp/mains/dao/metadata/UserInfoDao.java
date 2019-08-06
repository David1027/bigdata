package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.entitys.metadata.enums.SexEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {

  @Query(
      value =
          "SELECT country,count(*) as count  FROM `meta_data_user_info` where create_time >= ?1 AND create_time <= ?2 GROUP BY country ",
      nativeQuery = true)
  List<Object> getCountryAndCount(Date startTime, Date endTime);

  long countByCountryLikeAndCreateTimeLessThan(String country, Date createTime);

  long countByTypeAndCreateTimeLessThan(RegisterTypeEnum type, Date endTime);

  long countBySexAndCreateTimeLessThan(SexEnum sex, Date createTime);

  Optional<UserInfo> findByUserId(Integer userId);

  Optional<UserInfo> findBySign(String userName);
}

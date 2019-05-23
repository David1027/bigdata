package com.shoestp.mains.dao.metaData;

import com.shoestp.mains.entitys.metaData.UserInfo;
import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {

  @Query(
      value =
          "SELECT country,count(*) as count  FROM `user_info` where create_time > ?1 AND create_time <= ?2 GROUP BY country ",
      nativeQuery = true)
  public List<Object> getCountryAndCount(Date startTime, Date endTime);

  public long countByCountryLikeAndCreateTimeLessThan(String country, Date createTime);

  public long countByTypeAndCreateTimeLessThan(RegisterTypeEnum type, Date endTime);

  public long countBySexAndCreateTimeLessThan(SexEnum sex, Date createTime);
}

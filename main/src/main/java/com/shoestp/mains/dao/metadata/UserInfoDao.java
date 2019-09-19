package com.shoestp.mains.dao.metadata;

import com.shoestp.mains.entitys.metadata.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The interface User info dao.
 *
 * @author lijie
 * @date 2019 /09/19
 * @since
 */
public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {

  /**
   * Gets country and count.
   *
   * @author lijie
   * @date 2019 /09/19
   * @since *
   * @param startTime the start time
   * @param endTime the end time
   * @return the country and count
   */
  @Query(
      value =
          "SELECT country,count(*) as count  FROM `meta_data_user_info` where create_time >= ?1 AND create_time <= ?2 GROUP BY country ",
      nativeQuery = true)
  List<Object> getCountryAndCount(Date startTime, Date endTime);

  /**
   * Find by user id
   *
   * @author lijie
   * @date 2019 /09/19
   * @since optional.
   * @param userId the user id
   * @return the optional
   */
  Optional<UserInfo> findByUserId(Integer userId);

  /**
   * Find by sign
   *
   * @author lijie
   * @date 2019 /09/19
   * @since optional.
   * @param userName the user name
   * @return the optional
   */
  Optional<UserInfo> findBySign(String userName);

  /**
   * Find by nmae
   *
   * @author lijie
   * @date 2019 /09/19
   * @since optional.
   * @return the optional
   */
  Optional<UserInfo> findByName(String name);
}

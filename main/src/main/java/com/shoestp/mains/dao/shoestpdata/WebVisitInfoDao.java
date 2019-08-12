package com.shoestp.mains.dao.shoestpdata;

import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

interface WebVisitInfoDao extends JpaRepository<WebVisitInfo, Integer> {

  // 查询首页的点击人数
  // 查询商品详情页的点击人数
  // 查询搜索页结果也的点击人数
  // 查询svs页的点击人数
  // 查询其他页的点击人数

  @Query(
      value =
          "SELECT url,title,location,img,usr_main_supplier,count(*) FROM `meta_data_web_visit_info` "
              + "WHERE create_time >= ?1 AND create_time <= ?2 and url REGEXP '^(.*_p)([1234567890]{1,}).html' GROUP BY location,title ",
      nativeQuery = true)
  List<Object> getPdtPageViews(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT url,title,location,img,usr_main_supplier,count(*) FROM `meta_data_web_visit_info` "
              + "WHERE create_time >= ?1 AND create_time <= ?2 and url REGEXP '^(.*_p)([1234567890]{1,}).html' GROUP BY location,title,ip ",
      nativeQuery = true)
  List<Object> getPdtVisitorCount(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT user_id,visit_name,usr_main_supplier,count(*) FROM `meta_data_web_visit_info` "
              + "WHERE user_id <> -1 and usr_main_supplier is not null and create_time >= ?1 AND create_time <= ?2 GROUP BY user_id",
      nativeQuery = true)
  List<Object> getLoginUserInfo(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT ip,visit_name,usr_main_supplier,count(*) FROM `meta_data_web_visit_info` "
              + "WHERE user_id = -1 AND usr_main_supplier is not null and create_time >= ?1 AND create_time <= ?2 GROUP BY ip",
      nativeQuery = true)
  List<Object> getNotLoginUserInfo(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT location,usr_main_supplier FROM `meta_data_web_visit_info` "
              + "WHERE user_id <> -1 and usr_main_supplier is not null and create_time >= ?1 AND create_time <= ?2 GROUP BY  location,usr_main_supplier,user_id ",
      nativeQuery = true)
  List<Object> getVisitorCount(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT location,usr_main_supplier FROM `meta_data_web_visit_info` "
              + "WHERE user_id = -1 and usr_main_supplier is not null and create_time >= ?1 AND create_time <= ?2 GROUP BY  location,usr_main_supplier,ip",
      nativeQuery = true)
  List<Object> getNotLoginVisitorCount(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT location,usr_main_supplier,count(*) FROM `meta_data_web_visit_info` "
              + "WHERE user_id <> -1 and usr_main_supplier is not null and create_time >= ?1 AND create_time <= ?2 GROUP BY location,usr_main_supplier",
      nativeQuery = true)
  List<Object> getPageViewsCount(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT location,usr_main_supplier,count(*) FROM `meta_data_web_visit_info` "
              + "WHERE user_id = -1 and usr_main_supplier is not null and create_time >= ?1 AND create_time <= ?2 GROUP BY location,usr_main_supplier",
      nativeQuery = true)
  List<Object> getNotLoginPageViewsCount(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT id FROM `meta_data_web_visit_info` WHERE create_time >= ?1 AND create_time <= ?2 GROUP BY ip",
      nativeQuery = true)
  List<Object> getVisitorCountByDate(Date startTime, Date endTime);
}

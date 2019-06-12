package com.shoestp.mains.repositorys.metadata;

import com.shoestp.mains.entitys.metadata.GoogleBrowseInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoogleBrowseInfoRepository extends JpaRepository<GoogleBrowseInfo, Integer> {

  Optional<GoogleBrowseInfo> findTopByOrderByCreateTimeDesc();

  @Query(
      value =
          "SELECT * FROM `meta_data_google_browse_info` where access_time >= ?1 AND access_time <= ?2",
      nativeQuery = true)
  List<GoogleBrowseInfo> queryByStartTimeAndEndTime(String startTime, String endTime);

  @Query(
      value =
          "SELECT page_path AS pagePath, sum( page_views ) AS totalPageViews, SUM( sessions ) AS totalSession, sum(time_on_page) AS totalTime FROM meta_data_google_browse_info GROUP BY pagePath ORDER BY totalPageViews DESC LIMIT :limit",
      nativeQuery = true)
  List<Object> queryPageRanking(@Param("limit") Integer limit);

  @Query(
      value =
          "SELECT page_path AS pagePath, sum( page_views ) AS totalPageViews, SUM( sessions ) AS totalSession "
              + "FROM meta_data_google_browse_info where access_time>=?1 and access_time<?2 GROUP BY pagePath ORDER BY totalPageViews DESC  LIMIT ?3",
      nativeQuery = true)
  List<Object> queryPageRanking(String startTime, String endTime, Integer limit);

  @Query(
      value =
          "SELECT sum(page_views),sum(visitor) FROM `meta_data_google_browse_info` WHERE page_path like ('%_p:pkey.html') and access_time >= :startTime AND access_time <= :endTime",
      nativeQuery = true)
  List<Object> getPdtVisitCountAndPageViews(
      @Param("pkey") Integer pkey,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime);

  @Query(
      value =
          "SELECT sum(page_views),sum(visitor) FROM meta_data_google_browse_info "
              + "WHERE page_path like ('%/home/usr_UsrSupplier_gtSupIndex?pkey=:pkey%') "
              + "or  page_path like ('%/home/usr_UsrSupplier_gtSupPro?pkey=:pkey%') "
              + "or  page_path like ('%/home/usr_UsrSupplier_gtSupInfo?pkey=:pkey%') "
              + "and access_time >= :startTime AND access_time <= :endTime",
      nativeQuery = true)
  List<Object> getSupVisitCountAndPageViews(
      @Param("pkey") Integer pkey,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime);

  @Query(
      value =
          "SELECT country as co,sum(page_views) as pageViews,sum(sessions) as sessions,"
              + " ( SELECT SUM( sessions )  FROM `meta_data_google_browse_info`  WHERE access_time >= ?2  AND access_time <= ?3  AND  sys_type = ?1  and country = co )  "
              + " FROM `meta_data_google_browse_info` "
              + "where access_time >= ?2 AND access_time <= ?3 GROUP BY country ",
      nativeQuery = true)
  List<Object> getPageViewsAndSessionsGrupByCountrty(int param, String startTime, String endTime);
}

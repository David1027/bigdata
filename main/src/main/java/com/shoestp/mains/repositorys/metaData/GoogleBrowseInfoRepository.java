package com.shoestp.mains.repositorys.metaData;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;

public interface GoogleBrowseInfoRepository extends JpaRepository<GoogleBrowseInfo, Integer> {

  Optional<GoogleBrowseInfo> findTopByOrderByCreateTimeDesc();

  @Query(
      value = "SELECT * FROM `google_browse_info` where access_time > ?1 AND access_time <= ?2",
      nativeQuery = true)
  List<GoogleBrowseInfo> queryByStartTimeAndEndTime(String startTime, String endTime);

  @Query(
      value =
          "SELECT\tpage_path AS pagePath,\tsum( page_views ) AS totalPageViews,\tSUM( sessions ) AS totalSession,\tsum(time_on_page) AS totalTime FROM\tgoogle_browse_info GROUP BY pagePath ORDER BY\ttotalPageViews DESC \tLIMIT :limit",
      nativeQuery = true)
  List<Object> queryPageRanking(@Param("limit") Integer limit);
}

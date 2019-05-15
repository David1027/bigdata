package com.shoestp.mains.dao.MetaData;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;

public interface GoogleBrowseInfoDao extends JpaRepository<GoogleBrowseInfo, Integer> {

  public Optional<GoogleBrowseInfo> findTopByOrderByCreateTimeDesc();

  @Query(
      value = "SELECT * FROM `google_browse_info` where access_time > ?1 AND access_time <= ?2",
      nativeQuery = true)
  public List<GoogleBrowseInfo> queryByStartTimeAndEndTime(String startTime, String endTime);
}

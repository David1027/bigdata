package com.shoestp.mains.dao.shoestpData;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shoestp.mains.entitys.MetaData.SearchWordInfo;

public interface SearchDao extends JpaRepository<SearchWordInfo, Integer> {

  @Query(
      value =
          " SELECT keyword,count(*) FROM `search_word_info` where create_time > ?1 and create_time < ?2 GROUP BY keyword limit ?3,?4",
      nativeQuery = true)
  public List<Object> getRanking(Date startTime, Date endTime, int start, int limit);
}

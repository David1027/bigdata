package com.shoestp.mains.dao.shoestpdata;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shoestp.mains.entitys.metadata.SearchWordInfo;

public interface SearchDao extends JpaRepository<SearchWordInfo, Integer> {

  public Map<String, Object> getRanking(Date startTime, Date endTime, int start, int limit);

  @Query(
      value =
          "SELECT keyword,count(*) FROM `meta_data_search_word_info` where country = ?1 GROUP BY keyword",
      nativeQuery = true)
  public List<Object> getRankingByCountry(String country);

  public List<SearchWordInfo> findByCreateTimeBetween(Date startDate, Date endDate);
}

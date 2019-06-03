package com.shoestp.mains.dao.shoestpdata;

import com.shoestp.mains.entitys.metadata.SearchWordInfo;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SearchDao extends JpaRepository<SearchWordInfo, Integer> {

  public Map<String, Object> getRanking(Date startTime, Date endTime, int start, int limit);

  @Query(value = "SELECT keyword,count(*) FROM SearchWordInfo where country = ?1 GROUP BY keyword")
  List<Object> getRankingByCountry(String country);

  List<SearchWordInfo> findByCreateTimeBetween(Date startDate, Date endDate);
}

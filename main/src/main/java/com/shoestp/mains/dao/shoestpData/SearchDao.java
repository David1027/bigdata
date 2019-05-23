package com.shoestp.mains.dao.shoestpData;

import java.util.Date;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.SearchWordInfo;

public interface SearchDao extends JpaRepository<SearchWordInfo, Integer> {

  public Map<String, Object> getRanking(Date startTime, Date endTime, int start, int limit);
}

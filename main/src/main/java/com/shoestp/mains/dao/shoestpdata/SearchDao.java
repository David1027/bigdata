package com.shoestp.mains.dao.shoestpdata;

import com.shoestp.mains.entitys.metadata.SearchWordInfo;
import java.util.Date;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchDao extends JpaRepository<SearchWordInfo, Integer> {

  public Map<String, Object> getRanking(Date startTime, Date endTime, int start, int limit);
}

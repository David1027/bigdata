package com.shoestp.mains.dao.shoestpData.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoestp.mains.entitys.metaData.QSearchWordInfo;
import com.shoestp.mains.views.dataView.utils.KeyValue;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchDaoImpl {

  @Autowired private EntityManager em;

  private JPAQueryFactory queryFactory;

  @PostConstruct
  public void init() {
    queryFactory = new JPAQueryFactory(em);
  }

  public Map<String, Object> getRanking(Date startTime, Date endTime, int start, int limit) {
    QSearchWordInfo search = QSearchWordInfo.searchWordInfo;
    JPAQuery<KeyValue> selectFrom =
        (JPAQuery<KeyValue>)
            queryFactory
                .select(
                    Projections.bean(
                        KeyValue.class, search.keyword.as("key"), search.count().as("value")))
                .from(search);
    selectFrom.where(search.createTime.between(startTime, endTime));
    selectFrom.groupBy(search.keyword);
    long count = selectFrom.fetchCount();
    List<KeyValue> list = selectFrom.offset(start).limit(limit).fetch();
    Map<String, Object> map = new HashMap<>();
    map.put("count", count);
    map.put("list", list);
    return map;
  }
}

package com.shoestp.mains.dao.shoestpData.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoestp.mains.entitys.MetaData.QWebVisitInfo;
import com.shoestp.mains.views.DataView.metaData.queryView;

public class WebVisitInfoDaoImpl {

  @Autowired private EntityManager em;

  private JPAQueryFactory queryFactory;

  @PostConstruct
  public void init() {
    queryFactory = new JPAQueryFactory(em);
  }

  public Long getClikeNum(Date startDate, Date endDate, String... str) {
    QWebVisitInfo qweb = QWebVisitInfo.webVisitInfo;
    JPAQuery<queryView> selectFrom =
        (JPAQuery<queryView>)
            queryFactory
                .select(
                    Projections.bean(
                        new queryView<Long>().getClass(), qweb.ip.count().as("queryField")))
                .from(qweb);
    int i = 1;
    BooleanExpression expression = null;
    for (String item : str) {
      String k = item;
      if (i == 1) {
        expression = qweb.url.eq(k);
      } else {
        expression.or(qweb.url.eq(k));
      }
      i++;
    }
    selectFrom.where(expression);
    selectFrom.where(qweb.createTime.between(startDate, endDate));
    selectFrom.groupBy(qweb.ip);
    List<queryView> fetchCount = selectFrom.fetch();
    Long l = 0l;
    for (queryView t : fetchCount) {
      l += (Long) t.getQueryField();
    }
    return l;
  }
}

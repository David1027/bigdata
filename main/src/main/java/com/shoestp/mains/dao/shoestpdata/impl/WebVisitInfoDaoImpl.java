package com.shoestp.mains.dao.shoestpdata.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoestp.mains.entitys.metadata.QWebVisitInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.views.dataview.metadata.queryView;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

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
    Long l = 0L;
    for (queryView t : fetchCount) {
      l += (Long) t.getQueryField();
    }
    return l;
  }

  public Map<String, Object> queryList(
      int type, int source, String page, String country, int start, int limit) {
    QWebVisitInfo qweb = QWebVisitInfo.webVisitInfo;
    JPAQuery<WebVisitInfo> selectFrom = queryFactory.selectFrom(qweb);
    Map<String, Object> map = new HashMap<>();
    if (type == 1) {
      // 登录用户
      selectFrom.where(qweb.userId.gt(-1));
    }
    if (source == 1) {
      // google
      selectFrom.where(qweb.referer.like("%google.com%"));
    } else if (source == 2) {
      // baidu
      selectFrom.where(qweb.referer.like("%baidu.com%"));
    } else if (source == 3) {
      // 自主访问
      selectFrom.where(qweb.referer.eq(""));
    } else if (source == 4) {
      // 社交访问
      selectFrom.where(qweb.referer.ne(""));
      selectFrom.where(qweb.referer.notLike("%google.com%"));
      selectFrom.where(qweb.referer.notLike("%baidu.com%"));
    }
    if (page != null && !"".equals(page)) {
      // 筛选页面
      selectFrom.where(qweb.url.like("%" + page + "%"));
    }
    if (country != null && !"".equals(country)) {
      // 筛选国家
      selectFrom.where(qweb.location.like("%" + country + "%"));
    }
    map.put("count", selectFrom.fetchCount());
    map.put("list", selectFrom.offset(start).limit(limit).fetch());
    return map;
  }
}

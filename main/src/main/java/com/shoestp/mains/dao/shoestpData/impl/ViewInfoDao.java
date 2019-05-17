package com.shoestp.mains.dao.shoestpData.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.shoestpData.WebVisitInfoRepository;
import com.shoestp.mains.entitys.MetaData.QWebVisitInfo;
import com.shoestp.mains.entitys.MetaData.WebVisitInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ViewInfoDao extends BaseDao {

  @Resource private WebVisitInfoRepository webVisitInfoRepository;


  public int getClikeNum(String... str) {
    QWebVisitInfo qweb = QWebVisitInfo.webVisitInfo;
    JPAQuery<WebVisitInfo> selectFrom = getQueryFactory().selectFrom(qweb);
    int i = 1;
    BooleanExpression expression = null;
    for (String item : str) {
      String k = "%" + item + "%";
      if (i == 1) {
        expression = qweb.url.like(k);
      } else {
        expression.or(qweb.url.like(k));
      }
      i++;
    }
    selectFrom.where(expression);
    selectFrom.groupBy(qweb.ip);
    Long fetchCount = selectFrom.fetchCount();
    return fetchCount.intValue();
  }

    @Override
    public Object find(Object o) {
        return null;
    }

    @Override
    public Object findById(Integer id) {
        return null;
    }

    @Override
    public int update(Object o) {
        return 0;
    }

    @Override
    public int updateByList(List list) {
        return 0;
    }

    @Override
    public int remove(Object o) {
        return 0;
    }

    @Override
    public int removeByIds(Integer... id) {
        return 0;
    }
}

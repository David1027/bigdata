package com.shoestp.mains.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/8 Time: 9:30 */
public abstract class BaseDao<T> {
  @PersistenceContext protected EntityManager entityManager;

  protected JPAQuery<?> getQuery() {
    return new JPAQuery<Void>(entityManager);
  }

  public JPAQueryFactory getQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}

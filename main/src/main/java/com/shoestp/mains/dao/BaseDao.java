package com.shoestp.mains.dao;

import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/8 Time: 9:30 */
public abstract class BaseDao<T> {
  @PersistenceContext protected EntityManager entityManager;

  protected JPAQuery<?> getQuery() {
    return new JPAQuery<Void>(entityManager);
  }

  public abstract T find(T t);

  public abstract T findById(Integer id);

  public abstract int update(T t);

  public abstract int updateByList(List<T> list);

  public abstract int remove(T t);

  public abstract int removeByIds(Integer... id);
}

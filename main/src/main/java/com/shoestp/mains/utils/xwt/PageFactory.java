package com.shoestp.mains.utils.xwt;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.StringUtils;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/** page工厂 Create by xesja on 2019/9/10 10:33 */
public class PageFactory extends ComparablePath {

  public static final int DEFAULT_LIMIT = 15;
  public static final int DEFAULT_START = 0;
  private static final String PARAM_START = "start";
  private static final String PARAM_LIMIT = "limit";
  private static final String PARAM_SORT = "sort";
  private static final String PARAM_SORT_SIGN = "_";
  private static final String ASC = "asc";
  private static final String DESC = "desc";

  protected PageFactory(Class type, Path parent, String property) {
    super(type, parent, property);
  }

  /** 每页多少条数据 */
  private static int getLimit(HttpServletRequest request) {
    return request.getParameter(PARAM_LIMIT) == null
        ? DEFAULT_LIMIT
        : Integer.valueOf(request.getParameter(PARAM_LIMIT));
  }

  /**
   * 获取起始记录数
   *
   * @param request
   * @return
   */
  private static int getStart(HttpServletRequest request) {
    return request.getParameter(PARAM_START) == null
        ? DEFAULT_START
        : Integer.valueOf(request.getParameter(PARAM_START));
  }

  /**
   * 获取排序字段及规则 如 count_desc_createdTime_asc sort:creaedTime_desc Created by liyichao on 2019/09/20
   * 10:23
   */
  private static Order[] getSort(HttpServletRequest request) {
    String sortParam = request.getParameter(PARAM_SORT);
    if (!StringUtils.isNullOrEmpty(sortParam)) {
      String[] sortElement = sortParam.split(PARAM_SORT_SIGN);
      if (sortElement.length % 2 != 0) {
        throw new RuntimeException("排序参数错误,请重新输入");
      }
      Order[] sorts = new Order[sortElement.length / 2];
      for (int i = 0; i < sortElement.length; i = i + 2) {
        String sortName = sortElement[i];
        String sortRule = sortElement[i + 1];
        Order order = null;
        if (sortRule.toLowerCase().equals(ASC)) {
          order = Order.asc(sortName);
        }
        if (sortRule.toLowerCase().equals(DESC)) {
          order = Order.desc(sortName);
        }
        sorts[i / 2] = order;
      }
      return sorts;
    }
    return null;
  }

  public static Sort defaultSort() {
    HttpServletRequest request = HttpContext.getRequest();
    Order[] orders = getSort(request);
    return orders == null ? Sort.unsorted() : Sort.by(orders);
  }

  /**
   * 获取携带分页信息和排序信息的PageRequest Create by xesja on 2019/9/10 11:21 Update by liyichao on 2019/09/20
   * 10:20
   */
  public static Pageable defaultPage() {
    HttpServletRequest request = HttpContext.getRequest();
    if (request != null) {
      // 每页多少条数据
      int limit = getLimit(request);
      // 开始位置
      int start = getStart(request);
      Order[] orders = getSort(request);
      if (orders == null) {
        return PageRequest.of(start / limit, limit);
      } else {
        return PageRequest.of(start / limit, limit, Sort.by(getSort(request)));
      }
    } else {
      return null;
    }
  }

  /**
   * * @描述 QueryDsl包装query对象分页排序信息 @Param query JPAQuery对象 @Param path
   * 排序字段所在QueryDsl实体类对象,QEntity @Param pageable 分页排序信息 @Return
   * com.querydsl.jpa.impl.JPAQuery<T> @作者 liyichao @修改人 liyichao @修改时间 2019-09-20 11:08:22
   */
  public static <T> JPAQuery<T> queryDslPage(
      JPAQuery<T> query, EntityPathBase<?> path, Pageable pageable) {
    if (pageable == null) {
      return query;
    }
    query = query.offset(pageable.getOffset()).limit(pageable.getPageSize());
    Iterator<Order> os = pageable.getSort().iterator();
    while (os.hasNext()) {
      query = query.orderBy(toOrderSpecifier(os.next(), path));
    }
    return query;
  }

  /**
   * * @描述 将JPA的Order对象转为QueryDsl对象 @Param order JPA的Order对象 @Param ep
   * Order的QueryDsl实体类对象,QEntity @Return com.querydsl.core.types.OrderSpecifier<?> @作者 liyichao @修改人
   * liyichao @修改时间 2019-09-20 11:10:01
   */
  public static OrderSpecifier<?> toOrderSpecifier(Order order, EntityPath<?> ep) {
    ComparablePath sortPropertyExpression =
        new PageFactory(Comparable.class, ep, order.getProperty());

    com.querydsl.core.types.Order o =
        order.isAscending()
            ? com.querydsl.core.types.Order.ASC
            : com.querydsl.core.types.Order.DESC;
    return new OrderSpecifier<>(o, sortPropertyExpression);
  }
}

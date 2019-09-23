package com.shoestp.mains.dao.dataview.inquirynew.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.dao.dataview.inquirynew.InquiryNewCustomRepository;
import com.shoestp.mains.entitys.dataview.inquirynew.DataViewInquiryNew;
import com.shoestp.mains.entitys.dataview.inquirynew.QDataViewInquiryNew;
import com.shoestp.mains.entitys.dataview.inquirynew.enums.InquiryTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InquiryNewCustomRepositoryImpl extends BaseDao implements InquiryNewCustomRepository {
  /**
   * Find all by name and type
   *
   * @param name the name
   * @param typeEnum the type enum
   * @param dateTime the date time
   * @param pageable the pageable
   * @return the page
   * @author lijie
   * @date 2019 /09/12
   * @since page.
   */
  @Override
  public Page<DataViewInquiryNew> findAllByNameAndTypeAndStatisticalTimeEquals(
      String name, InquiryTypeEnum typeEnum, LocalDateTime dateTime, Pageable pageable) {
    QDataViewInquiryNew data = QDataViewInquiryNew.dataViewInquiryNew;
    JPAQuery<DataViewInquiryNew> query = getQueryFactory().selectFrom(data);
    query.orderBy(data.inquiryCount.desc(), data.uv.desc(), data.pv.desc());
    if (name != null) {
      query.where(data.name.like("%" + name + "%"));
    }
    if (pageable != null) {
      query.offset(pageable.getOffset());
      query.limit(pageable.getPageSize());
    }
    query.where(data.type.eq(typeEnum));
    if (dateTime != null) {
      query.where(data.statisticalTime.eq(dateTime));
    }
    Page page = new PageImpl(query.fetch(), pageable, query.fetchCount());
    return page;
  }

  @Override
  public Page<DataViewInquiryNew> findAllByNameAndTypeGroupByNameAndImage(
      String name, InquiryTypeEnum typeEnum, Pageable pageable) {
    QDataViewInquiryNew data = QDataViewInquiryNew.dataViewInquiryNew;
    JPAQuery query =
        getQueryFactory()
            .selectFrom(data)
            .select(
                data.inquiryCount.sum().as("inquiryCount"),
                data.uv.sum().as("uv"),
                data.pv.sum().as("pv"),
                data.name,
                data.image)
            .groupBy(data.name)
            .orderBy(data.inquiryCount.desc(), data.uv.desc(), data.pv.desc())
            .where(data.type.eq(typeEnum));
    if (name != null) {
      query.where(data.name.like("%" + name + "%"));
    }
    Page page;
    if (pageable != null) {
      query.offset(pageable.getOffset());
      query.limit(pageable.getPageSize());
    }
    page =
        new PageImpl(
            (List)
                query.fetch().stream()
                    .map(
                        o -> {
                          DataViewInquiryNew dataViewInquiryNew = new DataViewInquiryNew();
                          Tuple tuple = (Tuple) o;
                          dataViewInquiryNew.setInquiryCount(tuple.get(0, Long.class));
                          dataViewInquiryNew.setPv(tuple.get(1, Long.class));
                          dataViewInquiryNew.setUv(tuple.get(2, Long.class));
                          dataViewInquiryNew.setName(tuple.get(data.name));
                          dataViewInquiryNew.setImage(tuple.get(data.image));
                          return dataViewInquiryNew;
                        })
                    .collect(Collectors.toList()),
            pageable,
            query.fetchCount());
    return page;
  }
}

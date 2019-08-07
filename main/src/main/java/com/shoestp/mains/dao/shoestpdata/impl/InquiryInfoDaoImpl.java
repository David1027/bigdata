package com.shoestp.mains.dao.shoestpdata.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.entitys.metadata.QInquiryInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.views.dataview.metadata.Data;

public class InquiryInfoDaoImpl {
  @Autowired private EntityManager em;

  private JPAQueryFactory queryFactory;

  @PostConstruct
  public void init() {
    queryFactory = new JPAQueryFactory(em);
  }

  public int queryInquiryCount(
      Date startDate, Date endDate, SourceTypeEnum souType, String sou, DeviceTypeEnum dev) {
    QInquiryInfo info = QInquiryInfo.inquiryInfo;
    JPAQuery<InquiryInfo> selectFrom = queryFactory.selectFrom(info);
    if (souType.equals(SourceTypeEnum.INTERVIEW)) {
      selectFrom.where(info.referer.eq("").or(info.referer.like("%shoestp.com%")));
    } else if (souType.equals(SourceTypeEnum.GOOGLE)) {
      selectFrom.where(info.referer.like("%google.com%"));
    } else if (souType.equals(SourceTypeEnum.BAIDU)) {
      selectFrom.where(info.referer.like("%baidu.com%"));
    } else {
      selectFrom.where(info.referer.ne(""));
      selectFrom.where(info.referer.notLike("%shoestp.com%"));
      selectFrom.where(info.referer.notLike("%google.com%"));
      selectFrom.where(info.referer.notLike("%baidu.com%"));
    }
    selectFrom.where(info.deviceType.eq(dev));
    if ("自然搜索".equals(sou)) {
      /*selectFrom.where(
      info.type
          .eq(InquiryTypeEnum.SUPPLIER)
          .or(info.type.eq(InquiryTypeEnum.COMMODITY))
          .or(info.type.eq(InquiryTypeEnum.SEARCHTERM)));*/
      return 0;
    } else {
      for (InquiryTypeEnum item : InquiryTypeEnum.values()) {
        if (sou.equals(item.getName())) {
          selectFrom.where(info.type.eq(item));
          break;
        }
      }
    }
    selectFrom.where(info.createTime.between(startDate, endDate));
    Long fetchCount = selectFrom.fetchCount();
    return fetchCount.intValue();
  }

  public List<Data> getInquiryKeyword(InquiryTypeEnum type, Date startTime, Date endTime) {
    QInquiryInfo info = QInquiryInfo.inquiryInfo;
    JPAQuery<Data> selectFrom =
        queryFactory
            .select(
                Projections.bean(
                    Data.class, info.keyword.as("key"), info.usrMainPurchase.as("number")))
            .from(info);
    selectFrom.where(info.type.eq(type)).where(info.createTime.between(startTime, endTime));
    List<Data> fetch = selectFrom.fetch();
    return fetch;
  }
}

package com.shoestp.mains.dao.shoestpData.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoestp.mains.entitys.metaData.InquiryInfo;
import com.shoestp.mains.entitys.metaData.QInquiryInfo;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class InquiryInfoDaoImpl {
  @Autowired private EntityManager em;

  private JPAQueryFactory queryFactory;

  @PostConstruct
  public void init() {
    queryFactory = new JPAQueryFactory(em);
  }

  public int queryInquiryCount(Date startDate, Date endDate, SourceTypeEnum souType, String sou) {
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
    if (sou.equals("自然搜索")) {
      /*selectFrom.where(
      info.type
          .eq(InquiryTypeEnum.SUPPLIER)
          .or(info.type.eq(InquiryTypeEnum.COMMODITY))
          .or(info.type.eq(InquiryTypeEnum.SEARCHTERM)));*/
    } else {
      for (InquiryTypeEnum item : InquiryTypeEnum.values()) {
        if (sou.equals(item.getName())) {
          selectFrom.where(info.type.eq(item));
          break;
        }
      }
      // selectFrom.where(info.type.ne(InquiryTypeEnum.LANDINGPAGE));
    }
    Long fetchCount = selectFrom.fetchCount();
    return fetchCount.intValue();
  }
}

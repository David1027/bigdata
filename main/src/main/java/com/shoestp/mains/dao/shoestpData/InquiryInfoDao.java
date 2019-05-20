package com.shoestp.mains.dao.shoestpData;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoestp.mains.entitys.MetaData.InquiryInfo;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

public interface InquiryInfoDao extends JpaRepository<InquiryInfo, Integer> {

  public int queryInquiryCount(Date startDate, Date endDate, SourceTypeEnum souType, String sou);
}

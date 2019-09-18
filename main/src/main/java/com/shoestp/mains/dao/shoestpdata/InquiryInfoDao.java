package com.shoestp.mains.dao.shoestpdata;

import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository(value = "com.shoestp.mains.dao.shoestpdata.InquiryInfoDao")
public interface InquiryInfoDao extends JpaRepository<InquiryInfo, Integer> {
  Optional<InquiryInfo> findByInquiryId(Integer id);

  Long countByPkeyAndTypeAndCreateTimeBetween(
          Integer pkey, InquiryTypeEnum type, Date start, Date end);
}

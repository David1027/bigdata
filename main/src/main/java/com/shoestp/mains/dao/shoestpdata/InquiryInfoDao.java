package com.shoestp.mains.dao.shoestpdata;

import com.shoestp.mains.entitys.metadata.InquiryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "com.shoestp.mains.dao.shoestpdata.InquiryInfoDao")
public interface InquiryInfoDao extends JpaRepository<InquiryInfo, Integer> {
  Optional<InquiryInfo> findByInquiryId(Integer id);
}


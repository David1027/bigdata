package com.shoestp.mains.dao.shoestpData;

import com.shoestp.mains.entitys.MetaData.InquiryInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryDao extends JpaRepository<InquiryInfo, Integer> {
}

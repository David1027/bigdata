package com.shoestp.mains.dao.shoestpData;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shoestp.mains.entitys.MetaData.InquiryInfo;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

public interface InquiryInfoDao extends JpaRepository<InquiryInfo, Integer> {

  public int queryInquiryCount(Date startDate, Date endDate, SourceTypeEnum souType, String sou);

  public long countByCreateTimeBetween(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT count(*) FROM inquiry_info where create_time > ?1 AND create_time <= ?2 GROUP BY  ip",
      nativeQuery = true)
  public List getPeopleNum(Date startTime, Date endTime);

  public List<InquiryInfo> findByCreateTimeBetween(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,count(*) as count FROM `inquiry_info` "
              + "where type <> 'SEARCHTERM' and type <> 'RFQ' and create_time > ?1 AND create_time <= ?2 GROUP BY country",
      nativeQuery = true)
  public List<Object> getCountGrupByCountry(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,count(*) as count FROM `inquiry_info` "
              + "where type = 'RFQ' and create_time > ?1 AND create_time <= ?2 GROUP BY country",
      nativeQuery = true)
  public List<Object> getRfqCountGrupByCountry(Date startTime, Date endTime);
}

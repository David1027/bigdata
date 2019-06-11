package com.shoestp.mains.dao.shoestpdata;

import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.views.dataview.metadata.Data;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InquiryInfoDao extends JpaRepository<InquiryInfo, Integer> {

  int queryInquiryCount(
      Date startDate, Date endDate, SourceTypeEnum souType, String sou, DeviceTypeEnum dev);

  long countByTypeNotAndTypeNotAndCreateTimeBetween(
      InquiryTypeEnum type, InquiryTypeEnum type1, Date startTime, Date endTime);

  @Query(
      value =
          "SELECT count(*) FROM meta_data_inquiry_info where type <> 'SEARCHTERM' and type <> 'RFQ' and create_time >= ?1 AND create_time <= ?2 GROUP BY  ip",
      nativeQuery = true)
  List getPeopleNum(Date startTime, Date endTime);

  List<InquiryInfo> findByCreateTimeBetween(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,count(*) as count FROM `meta_data_inquiry_info` "
              + "where type <> 'SEARCHTERM' and type <> 'RFQ' and create_time >= ?1 AND create_time <= ?2 GROUP BY country",
      nativeQuery = true)
  List<Object> getCountGrupByCountry(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,count(*) as count FROM `meta_data_inquiry_info` "
              + "where type = 'RFQ' and create_time >= ?1 AND create_time <= ?2 GROUP BY country",
      nativeQuery = true)
  List<Object> getRfqCountGrupByCountry(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,img, name,pkey,usr_main_purchase,count(*) FROM `meta_data_inquiry_info` "
              + " where type = ?1 and create_time >= ?2 AND create_time <= ?3 GROUP BY country,pkey ",
      nativeQuery = true)
  List<Object> getPdtInquiry(String type, Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,usr_main_supplier FROM meta_data_inquiry_info "
              + "where (type = 'SUPPLIERSUPPLIER' or type = 'COMMODITY') and create_time > ?1 AND create_time <= ?2 GROUP BY  country,usr_main_supplier,usr_main_purchase ",
      nativeQuery = true)
  List<Object> getPeopleNumGroupByCountry(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,usr_main_supplier FROM meta_data_inquiry_info "
              + "where type <> 'SUPPLIERSUPPLIER' and type <> 'COMMODITY' "
              + " and type <> 'SEARCHTERM' and type <> 'RFQ' and create_time > ?1 AND create_time <= ?2 GROUP BY  country,usr_main_supplier,ip ",
      nativeQuery = true)
  List<Object> getNotLoginPeopleNum(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,usr_main_supplier,usr_main_purchase FROM meta_data_inquiry_info "
              + "where (type = 'SUPPLIERSUPPLIER' or type = 'COMMODITY') and create_time > ?1 AND create_time <= ?2 ",
      nativeQuery = true)
  List<Object> getInquiryCount(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,usr_main_supplier,ip FROM meta_data_inquiry_info "
              + "where type <> 'SUPPLIERSUPPLIER' and type <> 'COMMODITY' "
              + " and type <> 'SEARCHTERM' and type <> 'RFQ' and create_time > ?1 AND create_time <= ?2",
      nativeQuery = true)
  List<Object> getNotLoginInquiryCount(Date startTime, Date endTime);

  List<Data> getInquiryKeyword(InquiryTypeEnum type, Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,usr_main_supplier,count(*) FROM meta_data_inquiry_info "
              + "where (type = 'SUPPLIERSUPPLIER' or type = 'COMMODITY') and create_time > ?1 AND create_time <= ?2 GROUP BY  country,usr_main_supplier ",
      nativeQuery = true)
  List<Object> getInquiryCountGroupByCountry(Date startTime, Date endTime);

  @Query(
      value =
          "SELECT country,usr_main_supplier,count(*)  FROM meta_data_inquiry_info "
              + "where type <> 'SUPPLIERSUPPLIER' and type <> 'COMMODITY' "
              + " and type <> 'SEARCHTERM' and type <> 'RFQ' and create_time > ?1 AND create_time <= ?2 GROUP BY  country,usr_main_supplier ",
      nativeQuery = true)
  List<Object> getNotLoginInquiryCountGroupByCountry(Date startTime, Date endTime);
}

package com.shoestp.mains.dao.dataview.inquirynew;

import com.shoestp.mains.entitys.dataview.inquirynew.DataViewInquiryNew;
import com.shoestp.mains.entitys.dataview.inquirynew.enums.InquiryTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * The interface Inquiry repository.
 *
 * @author lijie
 * @date 2019 /09/12
 * @since
 */
@Repository
public interface InquiryNewCustomRepository {
  /**
   * Find all by name and type
   *
   * @author lijie
   * @date 2019 /09/12
   * @since page.
   * @param name the name
   * @param typeEnum the type enum
   * @param dateTime the date time
   * @param pageable the pageable
   * @return the page
   */
  Page<DataViewInquiryNew> findAllByNameAndTypeAndStatisticalTimeEquals(
      String name, InquiryTypeEnum typeEnum, LocalDateTime dateTime, Pageable pageable);

  Page<DataViewInquiryNew> findAllByNameAndTypeGroupByNameAndImage(
      String name, InquiryTypeEnum typeEnum, Pageable pageable);
}

package com.shoestp.mains.dao.shoestpdata;

import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * The interface Inquiry info dao.
 *
 * @author lijie
 * @date 2019 /09/19
 * @since
 */
@Repository(value = "com.shoestp.mains.dao.shoestpdata.InquiryInfoDao")
public interface InquiryInfoDao extends PagingAndSortingRepository<InquiryInfo, Integer> {
  /**
   * Find by inquiry id
   *
   * @author lijie
   * @date 2019 /09/19
   * @since optional.
   * @param id the id
   * @return the optional
   */
  Optional<InquiryInfo> findByInquiryId(Integer id);

  /**
   * Count by pkey and type and create time between
   *
   * @author lijie
   * @date 2019 /09/19
   * @since long.
   * @param pkey the pkey
   * @param type the type
   * @param start the start
   * @param end the end
   * @return the long
   */
  Long countByPkeyAndTypeAndCreateTimeBetween(
      Integer pkey, InquiryTypeEnum type, Date start, Date end);

  /**
   * The constant fin. @author lijie
   *
   * @author lijie
   * @date 2019 /09/19
   * @since page.
   * @param type the type
   * @param start the start
   * @param end the end
   * @param pageRequest the page request
   * @return the page
   */
  Page<InquiryInfo> findAllByTypeAndCreateTimeBetween(
      InquiryTypeEnum type, Date start, Date end, Pageable pageRequest);
}

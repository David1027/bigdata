package com.shoestp.mains.dao.dataview.inquirynew;

import com.shoestp.mains.entitys.dataview.inquirynew.DataViewInquiryNew;
import com.shoestp.mains.entitys.dataview.inquirynew.enums.InquiryTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The interface Inquiry repository.
 *
 * @author lijie
 * @date 2019 /09/12
 * @since
 */
@Repository
public interface InquiryNewRepository
    extends PagingAndSortingRepository<DataViewInquiryNew, Integer> {
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
      String name, InquiryTypeEnum typeEnum, LocalDateTime dateTime, Pageable pageable);;

  Page<DataViewInquiryNew> findAllByTypeAndStatisticalTimeEquals(
      InquiryTypeEnum typeEnum, LocalDateTime dateTime, Pageable pageable);

  /**
   * Find top order by statistical time desc 查询出最后的统计时间
   *
   * @author lijie
   * @date 2019 /09/12
   * @since local date time.
   * @return the local date time
   */
  Optional<DataViewInquiryNew> findTopByOrderByStatisticalTimeDesc();
}

package com.shoestp.mains.repositorys.DataView.inquory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiryRank;

/**
 * @description: 询盘-实现JPA接口
 * @author: lingjian @Date: 2019/5/14 11:18
 */
public interface InquiryRankRepository
    extends PagingAndSortingRepository<DataViewInquiryRank, Integer> {

  @Query(
      value =
          "SELECT inquiry_name,count(*) FROM `data_view_inquiry_rank` where inquiry_type = ?1 and create_time > ?2 and create_time < ?3 GROUP BY pkey limit ?4,?5 ",
      nativeQuery = true)
  public List<Object> getRanking(String type, Date startTime, Date endTime, int start, int limit);
}

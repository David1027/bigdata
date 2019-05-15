package com.shoestp.mains.repositorys.DataView.inquory;

import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiryRank;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 询盘-实现JPA接口
 * @author: lingjian @Date: 2019/5/14 11:18
 */
public interface InquiryRankRepository
    extends PagingAndSortingRepository<DataViewInquiryRank, Integer> {}

package com.shoestp.mains.repositorys.DataView.inquory;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiry;

/**
 * @description: 询盘-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:20
 */
public interface InquiryRepository extends PagingAndSortingRepository<DataViewInquiry, Integer> {}

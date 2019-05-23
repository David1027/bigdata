package com.shoestp.mains.repositorys.dataView.inquory;

import com.shoestp.mains.entitys.dataView.inquiry.DataViewInquiry;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 询盘-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:20
 */
public interface InquiryRepository extends PagingAndSortingRepository<DataViewInquiry, Integer> {}

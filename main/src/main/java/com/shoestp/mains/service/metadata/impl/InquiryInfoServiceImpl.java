package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.shoestpdata.InquiryInfoDao;
import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.service.metadata.InquiryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:28 */
@Service
public class InquiryInfoServiceImpl implements InquiryInfoService {
  @Autowired private InquiryInfoDao inquiryDao;

  @Override
  public InquiryInfo save(InquiryInfo inquiryInfo) {
    return inquiryDao.save(inquiryInfo);
  }
}
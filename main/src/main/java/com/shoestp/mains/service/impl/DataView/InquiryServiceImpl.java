package com.shoestp.mains.service.impl.DataView;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.InquiryDao;
import com.shoestp.mains.service.DataView.InquiryService;

import org.springframework.stereotype.Service;

/**
 * @description: 询盘-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:18
 */
@Service
public class InquiryServiceImpl implements InquiryService {

  @Resource private InquiryDao inquiryDao;
}

package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.shoestpdata.InquiryInfoDao;
import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.service.metadata.InquiryInfoService;
import com.shoestp.mains.service.metadata.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:28 */
@Service
public class InquiryInfoServiceImpl implements InquiryInfoService {
  @Resource(name = "com.shoestp.mains.dao.shoestpdata.InquiryInfoDao")
  private InquiryInfoDao inquiryDao;

  @Resource private UserInfoService userInfoServicel;

  @Override
  public InquiryInfo save(InquiryInfo inquiryInfo) {
    return inquiryDao.save(inquiryInfo);
  }

  @Override
  public void save(GRPC_SendDataProto.Inquiry inquiry) {
    InquiryInfo inquiryInfo = new InquiryInfo();
    switch (inquiry.getType()) {
      case 1:
        inquiryInfo.setType(InquiryTypeEnum.RFQ);
        break;
      case 2:
      case 3:
        inquiryInfo.setType(InquiryTypeEnum.COMMODITY);
        break;
      case 4:
        inquiryInfo.setType(InquiryTypeEnum.SUPPLIER);
        break;
      case 5:
        inquiryInfo.setType(InquiryTypeEnum.PRIVATE);
        break;
      default:
        inquiryInfo.setType(InquiryTypeEnum.LANDING);
    }
    inquiryInfo.setUserInfo(userInfoServicel.getUserInfo(inquiry.getUserId(), inquiry.getSign()));
    inquiryInfo.setUrl(inquiry.getUrl());
    /** Shoestp 询盘表单的ID */
    inquiryInfo.setInquiryId(inquiry.getInquiryId());
    inquiryInfo.setReferer(inquiry.getReferer());
    inquiryInfo.setCreateTime(new Date(inquiry.getCreateDate()));
    inquiryInfo.setName(inquiry.getName());
    inquiryInfo.setPkey(inquiry.getPkey());
    inquiryInfo.setMoney(inquiry.getMoney());
    inquiryInfo.setImg(inquiry.getImg());
    inquiryInfo.setDeviceType(DeviceTypeEnum.PC);
    inquiryDao.save(inquiryInfo);
  }

  @Override
  public void syncUserInfo(GRPC_SendDataProto.Inquiry info) {
    if (!inquiryDao.findByInquiryId(info.getInquiryId()).isPresent()) {
      save(info);
    }
  }
}

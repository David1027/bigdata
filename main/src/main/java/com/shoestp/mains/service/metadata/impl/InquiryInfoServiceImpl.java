package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.dao.shoestpdata.InquiryInfoDao;
import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.service.metadata.InquiryInfoService;
import com.shoestp.mains.service.metadata.UserInfoService;
import org.springframework.stereotype.Service;
import org.start2do.utils.DateTimeUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
    InquiryInfo inquiryInfo;
    Optional<InquiryInfo> result = inquiryDao.findByInquiryId(inquiry.getInquiryId());
    if (result.isPresent()) {
      inquiryInfo = result.get();
    } else {
      inquiryInfo = new InquiryInfo();
    }
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
    inquiryInfo.setSubmit_user(
        userInfoServicel.getUserInfo(inquiry.getSubmitUser(), inquiry.getSign()));
    inquiryInfo.setRecipient_user(userInfoServicel.getUserInfo(inquiry.getRecipientUser(), null));
    inquiryInfo.setUrl(inquiry.getUrl());
    /** Shoestp 询盘表单的ID */
    inquiryInfo.setInquiryId(inquiry.getInquiryId());
    inquiryInfo.setReferer(inquiry.getReferer());
    inquiryInfo.setCreateTime(new Date(inquiry.getCreateDate()));
    inquiryInfo.setName(inquiry.getName());
    inquiryInfo.setPkey(inquiry.getPkey());
    inquiryInfo.setMoney(inquiry.getMoney());
    inquiryInfo.setImg(inquiry.getImg());
    inquiryInfo.setIp(inquiry.getIp());
    inquiryInfo.setDeviceType(DeviceTypeEnum.PC);
    inquiryDao.save(inquiryInfo);
  }

  @Override
  public void syncInquiry(GRPC_SendDataProto.Inquiry info) {
    save(info);
  }

  /**
   * Count by pkey and type 根据产品/商家的 id 和类型统计某个时间段内的询盘总数
   *
   * @param pkey the pkey
   * @param type the type
   * @param start the start
   * @param end the end
   * @return the long
   * @author lijie
   * @date 2019 /09/16
   * @since long.
   */
  @Override
  public Long countByPkeyAndType(
      Integer pkey, InquiryTypeEnum type, LocalDateTime start, LocalDateTime end) {
    return inquiryDao.countByPkeyAndTypeAndCreateTimeBetween(
        pkey, type, DateTimeUtil.toDate(start), DateTimeUtil.toDate(end));
  }
}

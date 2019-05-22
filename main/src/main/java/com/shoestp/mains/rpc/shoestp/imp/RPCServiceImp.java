package com.shoestp.mains.rpc.shoestp.imp;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.start2do.utils.ipUtils.City;

import com.shoestp.mains.dao.metaData.UserInfoDao;
import com.shoestp.mains.entitys.MetaData.InquiryInfo;
import com.shoestp.mains.entitys.MetaData.SearchWordInfo;
import com.shoestp.mains.entitys.MetaData.WebVisitInfo;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.UserInfo;
import com.shoestp.mains.rpc.shoestp.pojo.SendDataUtilGrpc;
import com.shoestp.mains.service.metaData.InquiryInfoService;
import com.shoestp.mains.service.metaData.SearchWordInfoService;
import com.shoestp.mains.service.metaData.WebVisitInfoService;

import io.grpc.stub.StreamObserver;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 15:00 */
@Component
public class RPCServiceImp extends SendDataUtilGrpc.SendDataUtilImplBase {

  private static final Logger logger = LogManager.getLogger(RPCServiceImp.class);
  @Resource private WebVisitInfoService webVisitInfoService;
  @Resource private SearchWordInfoService searchWordInfoService;
  @Resource private InquiryInfoService inquiryInfoService;
  @Autowired private UserInfoDao userInfoDao;

  @Override
  public StreamObserver<GRPC_SendDataProto.SearchInfo> sendSearch(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.SearchInfo>() {
      @Override
      public void onNext(GRPC_SendDataProto.SearchInfo searchInfo) {
        logger.debug("++++++++++++++++++++++++++++++++++++++++++");
        logger.debug(searchInfo);
        SearchWordInfo searchWordInfo = new SearchWordInfo();
        searchWordInfo.setIp(searchInfo.getIp());
        searchWordInfo.setKeyword(searchInfo.getKeyword());
        searchWordInfo.setUserId(searchInfo.getUserId());
        searchWordInfo.setCreateTime(new Date());
        searchWordInfoService.save(searchWordInfo);
      }

      @Override
      public void onError(Throwable throwable) {
        logger.error(throwable);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<GRPC_SendDataProto.ViewInfo> sendViewInfo(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.ViewInfo>() {
      @Override
      public void onNext(GRPC_SendDataProto.ViewInfo viewInfo) {
        logger.debug("++++++++++++++++++++++++++++++++++++++++++");
        logger.debug(viewInfo);
        WebVisitInfo webVisitInfo = new WebVisitInfo();
        webVisitInfo.setUrl(viewInfo.getUrl());
        webVisitInfo.setUserAgent(viewInfo.getUseragent());
        webVisitInfo.setReferer(viewInfo.getReferer());
        webVisitInfo.setIp(viewInfo.getIp());
        webVisitInfo.setUserId(viewInfo.getUserId());
        webVisitInfo.setVisitName(viewInfo.getVisitName());
        String[] str = City.find(viewInfo.getIp());
        webVisitInfo.setLocation(str[0].toString());
        webVisitInfo.setCreateTime(new Date());
        webVisitInfoService.save(webVisitInfo);
      }

      @Override
      public void onError(Throwable throwable) {
        logger.error(throwable);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<GRPC_SendDataProto.Inquiry> sendInquiry(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.Inquiry>() {
      @Override
      public void onNext(GRPC_SendDataProto.Inquiry inquiry) {
        logger.debug(inquiry);
        InquiryInfo inquiryInfo = new InquiryInfo();
        boolean b = true;
        switch (inquiry.getType()) {
          case 1:
            inquiryInfo.setType(InquiryTypeEnum.RFQ);
            b = false;
            break;
          case 2:
            inquiryInfo.setType(InquiryTypeEnum.COMMODITY);
            b = false;
            break;
          case 4:
            inquiryInfo.setType(InquiryTypeEnum.SUPPLIER);
            b = false;
            break;
        }
        if (b) {
          for (InquiryTypeEnum item : InquiryTypeEnum.values()) {
            if (inquiry.getType() == item.getSup()) {
              inquiryInfo.setType(item);
              break;
            }
          }
        }
        inquiryInfo.setId(inquiry.getInquiryId());
        inquiryInfo.setReferer(inquiry.getReferer());
        inquiryInfo.setCreateTime(new Date());
        inquiryInfo.setName(inquiry.getName());
        inquiryInfo.setPkey(inquiry.getPkey());
        inquiryInfo.setMoney(inquiry.getMoney());
        inquiryInfo.setIp(inquiry.getIp());
        inquiryInfo.setCountry(City.find(inquiry.getIp())[0]);
        inquiryInfoService.save(inquiryInfo);
      }

      @Override
      public void onError(Throwable throwable) {
        logger.error(throwable);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<GRPC_SendDataProto.UserInfo> sendUserInfo(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.UserInfo>() {

      @Override
      public void onNext(UserInfo info) {
        com.shoestp.mains.entitys.MetaData.UserInfo userInfo =
            new com.shoestp.mains.entitys.MetaData.UserInfo();
        userInfo.setCountry(info.getCountry());
        switch (info.getSex()) {
          case 0:
            userInfo.setSex(SexEnum.UNKNOWN);
            break;
          case 1:
            userInfo.setSex(SexEnum.MAN);
            break;
          case 2:
            userInfo.setSex(SexEnum.WOMAN);
            break;
          default:
            userInfo.setSex(SexEnum.UNKNOWN);
            break;
        }
        if (info.getType() == 0) {
          userInfo.setType(RegisterTypeEnum.PURCHASE);
        } else {
          userInfo.setType(RegisterTypeEnum.SUPPLIER);
        }
        userInfo.setCreateTime(new Date());
        userInfoDao.save(userInfo);
      }

      @Override
      public void onError(Throwable t) {
        logger.error(t);
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}

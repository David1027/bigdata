package com.shoestp.mains.rpc.shoestp.imp;

import com.shoestp.mains.entitys.MetaData.InquiryInfo;
import com.shoestp.mains.entitys.MetaData.SearchWordInfo;
import com.shoestp.mains.entitys.MetaData.WebVisitInfo;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.rpc.shoestp.pojo.SendDataUtilGrpc;
import com.shoestp.mains.service.metaData.InquiryInfoService;
import com.shoestp.mains.service.metaData.SearchWordInfoService;
import com.shoestp.mains.service.metaData.WebVisitInfoService;
import io.grpc.stub.StreamObserver;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 15:00 */
@Component
public class RPCServiceImp extends SendDataUtilGrpc.SendDataUtilImplBase {

  private static final Logger logger = LogManager.getLogger(RPCServiceImp.class);
  @Resource private WebVisitInfoService webVisitInfoService;

  @Resource private SearchWordInfoService searchWordInfoService;
  @Resource private InquiryInfoService inquiryInfoService;

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
}

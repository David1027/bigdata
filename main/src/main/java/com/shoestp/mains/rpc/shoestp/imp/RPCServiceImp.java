package com.shoestp.mains.rpc.shoestp.imp;

import java.util.Date;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shoestp.mains.dao.shoestpData.InquiryInfoDao;
import com.shoestp.mains.dao.shoestpData.SearchDao;
import com.shoestp.mains.dao.shoestpData.ViewInfoDao;
import com.shoestp.mains.entitys.MetaData.InquiryInfo;
import com.shoestp.mains.entitys.MetaData.SearchWordInfo;
import com.shoestp.mains.entitys.MetaData.WebVisitInfo;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.rpc.shoestp.pojo.SendDataUtilGrpc;

import io.grpc.stub.StreamObserver;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 15:00 */
public class RPCServiceImp extends SendDataUtilGrpc.SendDataUtilImplBase {
  private static final Logger logger = LogManager.getLogger(RPCServiceImp.class);
  @Inject private SearchDao searchDao;
  @Inject private ViewInfoDao viewInfoDao;
  @Inject private InquiryInfoDao inquiryDao;

  @Override
  public StreamObserver<GRPC_SendDataProto.SearchInfo> sendSearch(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.SearchInfo>() {
      @Override
      public void onNext(GRPC_SendDataProto.SearchInfo searchInfo) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(searchInfo);
        SearchWordInfo searchWordInfo = new SearchWordInfo();
        searchWordInfo.setIp(searchInfo.getIp());
        searchWordInfo.setKeyword(searchInfo.getKeyword());
        searchWordInfo.setUserId(searchInfo.getUserId());
        searchWordInfo.setCreateTime(new Date());
        searchDao.save(searchWordInfo);
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
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(viewInfo);
        WebVisitInfo webVisitInfo = new WebVisitInfo();
        webVisitInfo.setUrl(viewInfo.getUrl());
        webVisitInfo.setUserAgent(viewInfo.getUseragent());
        webVisitInfo.setReferer(viewInfo.getReferer());
        webVisitInfo.setIp(viewInfo.getIp());
        webVisitInfo.setUserId(viewInfo.getUserId());
        webVisitInfo.setCreateTime(new Date());
        viewInfoDao.save(webVisitInfo);
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
        System.out.println("》》》》》》》》》》》》》》》》》》》》》》》");
        System.out.println(inquiry);
        InquiryInfo inquiryInfo = new InquiryInfo();
        switch (inquiry.getType()) {
          case 2:
            inquiryInfo.setType(InquiryTypeEnum.COMMODITY);
          case 4:
            inquiryInfo.setType(InquiryTypeEnum.SUPPLIER);
        }
        inquiryInfo.setId(inquiry.getInquiryId());
        inquiryInfo.setReferer(inquiry.getReferer());
        inquiryInfo.setCreateTime(new Date());
        inquiryDao.save(inquiryInfo);
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

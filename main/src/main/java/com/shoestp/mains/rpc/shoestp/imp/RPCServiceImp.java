package com.shoestp.mains.rpc.shoestp.imp;

import com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Favorite;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.UserInfo;
import com.shoestp.mains.rpc.shoestp.pojo.SendDataUtilGrpc;
import com.shoestp.mains.service.metadata.FavoriteService;
import com.shoestp.mains.service.metadata.InquiryInfoService;
import com.shoestp.mains.service.metadata.SearchWordInfoService;
import com.shoestp.mains.service.metadata.UserInfoService;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 15:00 */
@Component
public class RPCServiceImp extends SendDataUtilGrpc.SendDataUtilImplBase {

  private static final Logger logger = LogManager.getLogger(RPCServiceImp.class);
  @Resource private SearchWordInfoService searchWordInfoService;
  @Resource private UserInfoService userInfoService;
  @Resource private InquiryInfoService inquiryInfoService;
  @Resource private FavoriteService favoriteService;

  @Override
  public StreamObserver<GRPC_SendDataProto.SearchInfo> sendSearch(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.SearchInfo>() {
      @Override
      public void onNext(GRPC_SendDataProto.SearchInfo searchInfo) {
        searchWordInfoService.save(searchInfo);
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
      public void onNext(GRPC_SendDataProto.Inquiry info) {
        switch (info.getAction()) {
          case 1:
            inquiryInfoService.syncInquiry(info);
            break;
          case 0:
          default:
            inquiryInfoService.save(info);
        }
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
        switch (info.getAction()) {
          case 1:
            userInfoService.syncUserInfo(info);
            break;
          case 0:
          default:
            userInfoService.save(info);
        }
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

  @Override
  public StreamObserver<GRPC_SendDataProto.Favorite> sendFavorite(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.Favorite>() {

      @Override
      public void onNext(Favorite info) {
        switch (info.getAction()) {
          case 1:
            favoriteService.syncUserInfo(info);
            break;
          case 0:
          default:
            favoriteService.save(info);
        }
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

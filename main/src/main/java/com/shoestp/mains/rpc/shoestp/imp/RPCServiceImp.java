package com.shoestp.mains.rpc.shoestp.imp;

import com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto;
import com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto;
import com.shoestp.mains.rpc.shoestp.pojo.SendDataUtilGrpc;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 15:00 */
public class RPCServiceImp extends SendDataUtilGrpc.SendDataUtilImplBase {
  private static final Logger logger = LogManager.getLogger(RPCServiceImp.class);

  @Override
  public StreamObserver<GRPC_SendDataProto.SearchInfo> sendSearch(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.SearchInfo>() {
      @Override
      public void onNext(GRPC_SendDataProto.SearchInfo searchInfo) {
        logger.debug(searchInfo);
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
        logger.debug(viewInfo);
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

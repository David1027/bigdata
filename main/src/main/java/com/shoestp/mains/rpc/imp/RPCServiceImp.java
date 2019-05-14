package com.shoestp.mains.rpc.imp;

import com.shoestp.mains.grpc.GRPC_ResultProto;
import com.shoestp.mains.grpc.GRPC_SendDataProto;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 15:00 */
public class RPCServiceImp extends com.shoestp.mains.grpc.SendDataUtilGrpc.SendDataUtilImplBase {
  private static final Logger logger = LogManager.getLogger(RPCServiceImp.class);

  @Override
  public StreamObserver<GRPC_SendDataProto.SearchInfo> sendSearch(
      StreamObserver<GRPC_ResultProto.Result> responseObserver) {
    return new StreamObserver<GRPC_SendDataProto.SearchInfo>() {
      @Override
      public void onNext(GRPC_SendDataProto.SearchInfo searchInfo) {
        logger.info(searchInfo.getKeyword());
        responseObserver.onNext(GRPC_ResultProto.Result.newBuilder().setMsg("你好啊").build());
      }

      @Override
      public void onError(Throwable throwable) {}

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
        logger.info(viewInfo);
      }

      @Override
      public void onError(Throwable throwable) {}

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}

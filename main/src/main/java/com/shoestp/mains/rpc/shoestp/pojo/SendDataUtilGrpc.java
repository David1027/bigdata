package com.shoestp.mains.rpc.shoestp.pojo;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: searchInfo.proto")
public final class SendDataUtilGrpc {

  private SendDataUtilGrpc() {}

  public static final String SERVICE_NAME = "SendDataUtil";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo,
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
      getSendSearchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendSearch",
      requestType = com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo.class,
      responseType = com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo,
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
      getSendSearchMethod() {
    io.grpc.MethodDescriptor<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo,
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
        getSendSearchMethod;
    if ((getSendSearchMethod = SendDataUtilGrpc.getSendSearchMethod) == null) {
      synchronized (SendDataUtilGrpc.class) {
        if ((getSendSearchMethod = SendDataUtilGrpc.getSendSearchMethod) == null) {
          SendDataUtilGrpc.getSendSearchMethod =
              getSendSearchMethod =
                  io.grpc.MethodDescriptor
                      .<com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo,
                          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
                      .setFullMethodName(generateFullMethodName("SendDataUtil", "sendSearch"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(new SendDataUtilMethodDescriptorSupplier("sendSearch"))
                      .build();
        }
      }
    }
    return getSendSearchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo,
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
      getSendViewInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendViewInfo",
      requestType = com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo.class,
      responseType = com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo,
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
      getSendViewInfoMethod() {
    io.grpc.MethodDescriptor<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo,
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
        getSendViewInfoMethod;
    if ((getSendViewInfoMethod = SendDataUtilGrpc.getSendViewInfoMethod) == null) {
      synchronized (SendDataUtilGrpc.class) {
        if ((getSendViewInfoMethod = SendDataUtilGrpc.getSendViewInfoMethod) == null) {
          SendDataUtilGrpc.getSendViewInfoMethod =
              getSendViewInfoMethod =
                  io.grpc.MethodDescriptor
                      .<com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo,
                          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
                      .setFullMethodName(generateFullMethodName("SendDataUtil", "sendViewInfo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(new SendDataUtilMethodDescriptorSupplier("sendViewInfo"))
                      .build();
        }
      }
    }
    return getSendViewInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry,
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
      getSendInquiryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendInquiry",
      requestType = com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry.class,
      responseType = com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry,
          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
      getSendInquiryMethod() {
    io.grpc.MethodDescriptor<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry,
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
        getSendInquiryMethod;
    if ((getSendInquiryMethod = SendDataUtilGrpc.getSendInquiryMethod) == null) {
      synchronized (SendDataUtilGrpc.class) {
        if ((getSendInquiryMethod = SendDataUtilGrpc.getSendInquiryMethod) == null) {
          SendDataUtilGrpc.getSendInquiryMethod =
              getSendInquiryMethod =
                  io.grpc.MethodDescriptor
                      .<com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry,
                          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
                      .setFullMethodName(generateFullMethodName("SendDataUtil", "sendInquiry"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(new SendDataUtilMethodDescriptorSupplier("sendInquiry"))
                      .build();
        }
      }
    }
    return getSendInquiryMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static SendDataUtilStub newStub(io.grpc.Channel channel) {
    return new SendDataUtilStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SendDataUtilBlockingStub newBlockingStub(io.grpc.Channel channel) {
    return new SendDataUtilBlockingStub(channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static SendDataUtilFutureStub newFutureStub(io.grpc.Channel channel) {
    return new SendDataUtilFutureStub(channel);
  }

  /** */
  public abstract static class SendDataUtilImplBase implements io.grpc.BindableService {

    /** */
    public io.grpc.stub.StreamObserver<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo>
        sendSearch(
            io.grpc.stub.StreamObserver<com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                responseObserver) {
      return asyncUnimplementedStreamingCall(getSendSearchMethod(), responseObserver);
    }

    /** */
    public io.grpc.stub.StreamObserver<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo>
        sendViewInfo(
            io.grpc.stub.StreamObserver<com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                responseObserver) {
      return asyncUnimplementedStreamingCall(getSendViewInfoMethod(), responseObserver);
    }

    /** */
    public io.grpc.stub.StreamObserver<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry>
        sendInquiry(
            io.grpc.stub.StreamObserver<com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                responseObserver) {
      return asyncUnimplementedStreamingCall(getSendInquiryMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getSendSearchMethod(),
              asyncBidiStreamingCall(
                  new MethodHandlers<
                      com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo,
                      com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>(
                      this, METHODID_SEND_SEARCH)))
          .addMethod(
              getSendViewInfoMethod(),
              asyncBidiStreamingCall(
                  new MethodHandlers<
                      com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo,
                      com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>(
                      this, METHODID_SEND_VIEW_INFO)))
          .addMethod(
              getSendInquiryMethod(),
              asyncBidiStreamingCall(
                  new MethodHandlers<
                      com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry,
                      com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>(
                      this, METHODID_SEND_INQUIRY)))
          .build();
    }
  }

  /** */
  public static final class SendDataUtilStub extends io.grpc.stub.AbstractStub<SendDataUtilStub> {
    private SendDataUtilStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SendDataUtilStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SendDataUtilStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SendDataUtilStub(channel, callOptions);
    }

    /** */
    public io.grpc.stub.StreamObserver<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.SearchInfo>
        sendSearch(
            io.grpc.stub.StreamObserver<com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSendSearchMethod(), getCallOptions()), responseObserver);
    }

    /** */
    public io.grpc.stub.StreamObserver<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.ViewInfo>
        sendViewInfo(
            io.grpc.stub.StreamObserver<com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSendViewInfoMethod(), getCallOptions()), responseObserver);
    }

    /** */
    public io.grpc.stub.StreamObserver<
            com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.Inquiry>
        sendInquiry(
            io.grpc.stub.StreamObserver<com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>
                responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSendInquiryMethod(), getCallOptions()), responseObserver);
    }
  }

  /** */
  public static final class SendDataUtilBlockingStub
      extends io.grpc.stub.AbstractStub<SendDataUtilBlockingStub> {
    private SendDataUtilBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SendDataUtilBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SendDataUtilBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SendDataUtilBlockingStub(channel, callOptions);
    }
  }

  /** */
  public static final class SendDataUtilFutureStub
      extends io.grpc.stub.AbstractStub<SendDataUtilFutureStub> {
    private SendDataUtilFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SendDataUtilFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SendDataUtilFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SendDataUtilFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SEND_SEARCH = 0;
  private static final int METHODID_SEND_VIEW_INFO = 1;
  private static final int METHODID_SEND_INQUIRY = 2;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SendDataUtilImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SendDataUtilImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_SEARCH:
          return (io.grpc.stub.StreamObserver<Req>)
              serviceImpl.sendSearch(
                  (io.grpc.stub.StreamObserver<
                          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>)
                      responseObserver);
        case METHODID_SEND_VIEW_INFO:
          return (io.grpc.stub.StreamObserver<Req>)
              serviceImpl.sendViewInfo(
                  (io.grpc.stub.StreamObserver<
                          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>)
                      responseObserver);
        case METHODID_SEND_INQUIRY:
          return (io.grpc.stub.StreamObserver<Req>)
              serviceImpl.sendInquiry(
                  (io.grpc.stub.StreamObserver<
                          com.shoestp.mains.rpc.shoestp.pojo.GRPC_ResultProto.Result>)
                      responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private abstract static class SendDataUtilBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SendDataUtilBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.shoestp.mains.rpc.shoestp.pojo.GRPC_SendDataProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SendDataUtil");
    }
  }

  private static final class SendDataUtilFileDescriptorSupplier
      extends SendDataUtilBaseDescriptorSupplier {
    SendDataUtilFileDescriptorSupplier() {}
  }

  private static final class SendDataUtilMethodDescriptorSupplier
      extends SendDataUtilBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SendDataUtilMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SendDataUtilGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new SendDataUtilFileDescriptorSupplier())
                      .addMethod(getSendSearchMethod())
                      .addMethod(getSendViewInfoMethod())
                      .addMethod(getSendInquiryMethod())
                      .build();
        }
      }
    }
    return result;
  }
}

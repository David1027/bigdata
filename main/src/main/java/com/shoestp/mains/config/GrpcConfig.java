//package com.shoestp.mains.config;
//
//import javax.annotation.PostConstruct;
//
//import com.xinlianshiye.clouds.sso.facade.service.MemberServiceGrpc;
//
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @description: grpc-用户信息的配置
// * @author: lingjian
// * @create: 2019/12/30 11:31
// */
//@Configuration
//public class GrpcConfig {
//
//  private ManagedChannel ssoChannel;
//
//  @Value("${sso.grpc.host}")
//  private String host;
//
//  @Value("${sso.grpc.port}")
//  private Integer port;
//
//  @PostConstruct
//  public void init() {
//    this.ssoChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
//  }
//
//  @Bean
//  public MemberServiceGrpc.MemberServiceBlockingStub getMemberServiceBlockingStub() {
//    return MemberServiceGrpc.newBlockingStub(ssoChannel);
//  }
//}

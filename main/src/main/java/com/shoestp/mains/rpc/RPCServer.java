package com.shoestp.mains.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.annotation.Resource;

import com.shoestp.mains.rpc.shoestp.imp.RPCServiceImp;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 14:59 */
@Component
public class RPCServer {
  private Server server;
  private static final Logger logger = LogManager.getLogger(RPCServer.class);

  @Resource private Environment environment;


  public void start(RPCServiceImp service) throws InterruptedException, IOException {
    String host = environment.getProperty("rpc.host", "0.0.0.0");
    Integer port = Integer.parseInt(environment.getProperty("rpc.port", "888"));
    logger.info(String.format("RPC Listen:%s:%d", host, port));
    server =
        NettyServerBuilder.forAddress(new InetSocketAddress(host, port))
            .addService(service)
            .build();
    server.start();
    server.awaitTermination();
  }

  private void stop() {
    if (server != null) {
      logger.info("*** shutting down gRPC server since JVM is shutting down");
      server.shutdown();
    }
  }
}

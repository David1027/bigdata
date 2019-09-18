package com.shoestp.mains.rpc;

import com.shoestp.mains.rpc.shoestp.imp.RPCServiceImp;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/14 Time: 14:59
 *
 * @author lijie@shoestp.cn
 */
@Component
public class RPCServer {
  private Server server;
  private static final Logger logger = LogManager.getLogger(RPCServer.class);

  @Resource private Environment environment;

  @Value("${rpc.host}")
  private String host = "0.0.0.0";

  @Value("${rpc.port}")
  private Integer port = 888;

  public void start(RPCServiceImp service) throws InterruptedException, IOException {
    logger.info(String.format("RPC Listen:%s:%d", host, port));
    server =
        NettyServerBuilder.forAddress(new InetSocketAddress(host, port))
            .workerEventLoopGroup(new NioEventLoopGroup())
            .addService(service)
            .build();
    server.start();
    server.awaitTermination();
  }

  @PreDestroy
  private void stop() {
    if (server != null) {
      logger.info("*** shutting down gRPC server since JVM is shutting down");
      server.shutdown();
    }
  }
}

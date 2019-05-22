package com.shoestp.mains;

import com.shoestp.mains.rpc.RPCServer;
import com.shoestp.mains.rpc.shoestp.imp.RPCServiceImp;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@EnableCaching
@SpringBootApplication
public class Runs {
  /**
   * @title 启动函数
   * @author Lijie HelloBox@outlook.com
   * @params
   * @returns
   * @updateTime 2019-04-29 20:23
   * @throws
   * @description 请在启动参数添加 --spring.profiles.active=dev
   */
  public static void main(String[] args) {
    ConfigurableApplicationContext configurableApplicationContext =
        SpringApplication.run(Runs.class, args);
    RPCServer rpcServer = configurableApplicationContext.getBean(RPCServer.class);
    RPCServiceImp rpcServiceImp = configurableApplicationContext.getBean(RPCServiceImp.class);
    try {
      rpcServer.start(rpcServiceImp);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

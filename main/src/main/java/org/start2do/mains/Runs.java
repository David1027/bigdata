package org.start2do.mains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    System.out.println("args");
    for (String arg : args) {
      System.out.println(arg);
    }
    SpringApplication.run(Runs.class, args);
  }
}

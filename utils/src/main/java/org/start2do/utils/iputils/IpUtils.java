package org.start2do.utils.iputils;

import org.start2do.utils.io.ClassPathResource;

import java.io.IOException;

public class IpUtils {
  private static City db;

  static {
    try {
      db = new City(new ClassPathResource("ip.ipdb").getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 返回数组 数组内顺序依次为 国家 - 省份 - 城市 */
  public static String[] find(String ips) {
    try {
      return db.find(ips, "CN");
    } catch (IPFormatException e) {
      e.printStackTrace();
    } catch (InvalidDatabaseException e) {
      e.printStackTrace();
    }
    return null;
  }
}

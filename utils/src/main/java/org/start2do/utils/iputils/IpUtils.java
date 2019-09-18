package org.start2do.utils.iputils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.start2do.utils.io.ClassPathResource;

import java.io.IOException;

public class IpUtils {
  private static City db;
  private static final Logger logger = LogManager.getLogger(IpUtils.class);

  static {
    try {
      db = new City(new ClassPathResource("ip.ipdb").getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 返回数组 数组内顺序依次为 国家 - 省份 - 城市 */
  public static String[] find(String ips) {
    if (ips != null && ips.length() > 0) {
      try {
        return db.find(ips, "CN");
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("ip format error:{}", ips);
      }
    }
    return null;
  }
}

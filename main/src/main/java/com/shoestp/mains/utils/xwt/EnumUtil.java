package com.shoestp.mains.utils.xwt;

import com.shoestp.mains.enums.xwt.OProductEnum;

/**
 * @description: 枚举工具类
 * @author: lingjian
 * @create: 2020/1/8 16:21
 */
public class EnumUtil {
  /**
   * 判断数值是否属于枚举类的值
   *
   * @param value
   * @return
   */
  public static boolean isInclude(String value) {
    boolean include = false;
    for (OProductEnum oProduct : OProductEnum.values()) {
      if (oProduct.toString().equals(value)) {
        include = true;
        break;
      }
    }
    return include;
  }
}

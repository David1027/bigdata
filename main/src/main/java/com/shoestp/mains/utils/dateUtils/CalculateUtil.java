package com.shoestp.mains.utils.dateUtils;

/**
 * @description: 计算工具类
 * @author: lingjian
 * @create: 2019/8/15 9:18
 */
public class CalculateUtil {

  /**
   * 计算两个值相除的值
   *
   * @param num1 值1
   * @param num2 值2
   * @return Double
   */
  public static Double getExcept(int num1, int num2) {
    if (num2 == 0) {
      num2 = 1;
    }
    return num1 / (num2 * 1.0);
  }

  /**
   * 计算两个值相除的值
   *
   * @param num1 值1
   * @param num2 值2
   * @return Double
   */
  public static Double getExcept(long num1, long num2) {
    if (num2 == 0) {
      num2 = 1;
    }
    return num1 / (num2 * 1.0);
  }

  /**
   * 计算两个值差相除的值
   *
   * @param num1 值1
   * @param num2 值2
   * @return Double
   */
  public static Double getDifferenceExcept(double num1, double num2) {
    if (num2 == 0 && num1 != 0) {
      return 1.0;
    }
    if (num2 == 0 && num1 == 0) {
      return 0.0;
    }
    return (num1 - num2) / (num2 * 1.0);
  }
}

package com.shoestp.mains.utils.xwt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 字符串格式转换工具类
 * @author: lingjian
 * @create: 2019/12/26 15:59
 */
public class StringFormatUtils {

  private static final char UNDERLINE = '_';
  private static final Pattern PATTENT = Pattern.compile("_");
  /**
   * 驼峰格式字符串转换为下划线格式字符串
   *
   * @param param 驼峰格式字符串 appVersionFld
   * @return 下划线格式字符串 app_version_fld
   */
  public static String camelToUnderline(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    StringBuilder sb = new StringBuilder(param.length());
    for (int i = 0; i < param.length(); i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append(UNDERLINE);
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
  /**
   * 下划线格式字符串转换为驼峰格式字符串
   *
   * @param param 下划线格式字符串 app_version_fld
   * @return 驼峰格式字符串 appVersionFld
   */
  public static String underlineToCamel(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    StringBuilder sb = new StringBuilder(param.length());
    for (int i = 0; i < param.length(); i++) {
      char c = param.charAt(i);
      if (c == UNDERLINE) {
        if (++i < param.length()) {
          sb.append(Character.toUpperCase(param.charAt(i)));
        }
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }
  /**
   * 下划线格式字符串转换为驼峰格式字符串2
   *
   * @param param 下划线格式字符串 app_version_fld
   * @return 驼峰格式字符串 appVersionFld
   */
  public static String underlineToCamel2(String param) {
    if (param == null || "".equals(param.trim())) {
      return "";
    }
    StringBuilder sb = new StringBuilder(param);
    Matcher mc = PATTENT.matcher(param);
    int i = 0;
    while (mc.find()) {
      int position = mc.end() - (i++);
      sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
    }
    return sb.toString();
  }
}

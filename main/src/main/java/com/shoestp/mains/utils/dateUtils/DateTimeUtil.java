/*
 * 文 件 名:  DateTimeUtil.java
 * 描    述:  日期和时间工具类文件
 */
package com.shoestp.mains.utils.dateUtils;

import com.shoestp.mains.constant.dataview.Contants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期和时间工具类
 *
 * <p>提供日期和时间处理公共方法实现
 */
public final class DateTimeUtil {
  /** 日期格式：yyyy-MM-dd HH:mm:ss */
  public static final String DATE_FARMAT = "yyyy-MM-dd HH:mm:ss";

  /** 日期格式：yyyyMMddHHmmss */
  public static final String DATE_FORMAT_2 = "yyyyMMddHHmmss";

  /** 日期格式：yyyy-MM-dd */
  public static final String DATE_FARMAT_3 = "yyyy-MM-dd";

  /** 日期格式：yyyyMMdd */
  public static final String DATE_FARMAT_4 = "yyyyMMdd";

  /** 日期格式：yyyy-MM */
  public static final String DATE_FARMAT_5 = "yyyy-MM";

  /** 日期格式：yyMMdd */
  public static final String DATE_FARMAT_6 = "yyMMdd";

  /** 日期格式：yyyyMMddHHmmssSSS 毫秒 */
  public static final String DATE_FORMAT_7 = "yyyyMMddHHmmssSSS";

  /** 日期格式：mm:ss */
  public static final String DATE_FARMAT_8 = "HH:mm";

  /** 日期格式：yyyy-MM-dd HH:mm */
  public static final String DATE_FARMAT_9 = "yyyy-MM-dd HH:mm";

  /** 日期格式：MM-dd */
  public static final String DATE_FARMAT_10 = "MM-dd";

  /** 24小时组成的横坐标名称 */
  public static final String HOUR = "hour";

  public static final String EVERY_DAY = "everyday";

  /** 定义获取最近多少天的数量 */
  public static final Integer DAY = 30;

  /** 数据库存储的date类型字段转成JAVA的String类型为2006-06-11 03:35:13.0，需要去掉后面的.0 */
  private static final String DATE_STRING_EXTRA = ".0";

  /** 默认构造方法 不允许实例化 */
  private DateTimeUtil() {}

  /**
   * 日期时间字符串格式转换
   *
   * <p>数据库存储的date类型字段转成JAVA的String类型为2006-06-11 03:35:13.0，需要去掉后面的.0
   *
   * @param dateStr 日期时间字符串
   * @return 去掉.0的日期时间字符串
   * @see [类、类#方法、类#成员]
   */
  public static String formatDateString(String dateStr) {
    if (null != dateStr) {
      // 后面包含.0日期时间字符串才需要处理
      int index = dateStr.lastIndexOf(DATE_STRING_EXTRA);
      if (0 < index) {
        dateStr = dateStr.substring(0, index);
      }
    }

    return dateStr;
  }

  /**
   * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
   *
   * @param currentTime currentTime
   * @return Date
   * @see [类、类#方法、类#成员]
   */
  public static Date formatStringToDate(String currentTime) {
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT);
    try {
      date = df.parse(currentTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
   *
   * @param time currentTime
   * @param dataFarmat
   * @return Date
   * @see [类、类#方法、类#成员]
   */
  public static Date formatStringToDate(String time, String dataFarmat) {
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat(dataFarmat);
    try {
      date = df.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date getPreviousDay(Date date, int interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -interval);
    date = calendar.getTime();
    return date;
  }

  /**
   * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
   *
   * @param currentTime currentTime
   * @return Date
   * @see [类、类#方法、类#成员]
   */
  public static Date formatStringToDate3(String currentTime) {
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT_3);
    try {
      date = df.parse(currentTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 得到本周周一
   *
   * @return yyyy-MM-dd
   */
  public static String getMondayOfThisWeek() {
    SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT_3);
    Calendar c = Calendar.getInstance();
    int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    if (day_of_week == 0) {
      day_of_week = 7;
    }
    c.add(Calendar.DATE, -day_of_week + 1);
    return df.format(c.getTime());
  }

  /**
   * 得到本周周日
   *
   * @return yyyy-MM-dd
   */
  public static String getSundayOfThisWeek() {
    SimpleDateFormat df = new SimpleDateFormat(DATE_FARMAT_3);
    Calendar c = Calendar.getInstance();
    int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    if (day_of_week == 0) {
      day_of_week = 7;
    }
    c.add(Calendar.DATE, -day_of_week + 7);
    return df.format(c.getTime());
  }

  public static void main(String[] args) {
    System.out.println(getUTCDateTimeNow().replaceAll("[^\\d]", ""));
  }
  /**
   * 得到当前格林威治的日期和时间
   *
   * @return String
   */
  public static String getUTCDateTimeNow() {
    // 取时区
    TimeZone zone = TimeZone.getDefault();

    Calendar c = Calendar.getInstance();

    Date date = new Date();

    // 计算时区偏差
    c.setTimeInMillis(date.getTime() - zone.getOffset(date.getTime()));

    // 格式化
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FARMAT);

    String dateString = formatter.format(c.getTime());

    return dateString;
  }

  /** =================================大数据使用中================================= */

  /**
   * 将date型日期转换为想要的字符格式 <一句话功能简述> <功能详细描述>
   *
   * @param date date日期
   * @param dateFormat 日期格式：如yyyy-MM-dd HH:mm:ss
   * @return String
   */
  public static String formatDateToString(Date date, String dateFormat) {
    if (date == null) {
      return "";
    }
    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
    return format.format(date);
  }

  /**
   * 获取某天0点时间
   *
   * @param date 某天
   * @return
   */
  public static Date getTimesOfDay(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * 获得某天23:59:59点时间
   *
   * @return
   */
  public static Date getTimesnight(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.MILLISECOND, 999);
    return cal.getTime();
  }

  /**
   * 获得某一天某点时间
   *
   * @param date 某天
   * @param hour 某点
   * @return
   */
  public static Date getTimesOfDay(Date date, int hour) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * 获得当天0点时间
   *
   * @return
   */
  public static Date getTimesmorning() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * 获得当天23:59:59点时间
   *
   * @return
   */
  public static Date getTimesnight() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.MILLISECOND, 999);
    return cal.getTime();
  }

  /**
   * 获得昨天0点时间
   *
   * @return
   */
  public static Date getYesterdaymorning() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getTimesmorning());
    cal.add(Calendar.DAY_OF_MONTH, -1);
    return cal.getTime();
  }

  /**
   * 获得昨天23:59:59点时间
   *
   * @return
   */
  public static Date getYesterdaynight() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getTimesnight());
    cal.add(Calendar.DAY_OF_MONTH, -1);
    return cal.getTime();
  }

  /**
   * 获得上周同一天0点时间
   *
   * @return
   */
  public static Date getWeekmorning() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getTimesmorning());
    cal.add(Calendar.DAY_OF_MONTH, -7);
    return cal.getTime();
  }

  /**
   * 获得上周同一天23:59:59点时间
   *
   * @return
   */
  public static Date getWeeknight() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getTimesnight());
    cal.add(Calendar.DAY_OF_MONTH, -7);
    return cal.getTime();
  }

  /**
   * 获得当天近num天时间
   *
   * @param num
   * @return
   */
  public static Date getDayFromNum(Date date, int num) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getTimesOfDay(date));
    cal.add(Calendar.DATE, -num);
    return cal.getTime();
  }

  /**
   * 获取24小时每num个小时的横坐标
   *
   * @param num
   * @return
   */
  public static Map<String, String[]> getHourAbscissa(int num) {
    String[] arr = new String[24 / num];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = i * num + num + "";
    }
    Map<String, String[]> arrMap = new HashMap<>();
    arrMap.put(EVERY_DAY, arr);
    return arrMap;
  }

  /**
   * 获取日期分布的横坐标-num天
   *
   * @param num
   * @param date
   * @return
   */
  public static Map<String, String[]> getDayAbscissa(int num, Date date) {
    String[] arr = new String[num];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = formatDateToString(getDayFromNum(date, num - i - 1), DATE_FARMAT_10);
    }
    Map<String, String[]> arrMap = new HashMap<>();
    arrMap.put(EVERY_DAY, arr);
    return arrMap;
  }

  /**
   * 根据时间获取最近30天的日期集合
   *
   * @param date
   * @return
   */
  public static List getDay(Date date) {
    List list = new ArrayList();
    for (int i = 0; i < DAY; i++) {
      list.add(formatDateToString(getDayFromNum(date, DAY - i - 1), DATE_FARMAT_10));
    }
    return list;
  }

  /**
   * 根据时间获取是哪一年的第几个周
   *
   * @param date
   * @return
   */
  public static List getWeek(Date date) {
    List list = new ArrayList();
    Calendar calendar = Calendar.getInstance();
    for (int i = 0; i < Contants.TWELVE; i++) {
      calendar.setTime(getDayFromNum(date, (Contants.TWELVE - i) * 7 - 7));
      String s = calendar.get(Calendar.YEAR) + " 第" + calendar.get(Calendar.WEEK_OF_YEAR) + "周";
      list.add(s);
    }

    return list;
  }

  /**
   * 根据时间获取是哪一年的那个月
   *
   * @param date
   * @return
   */
  public static List getMonth(Date date) {
    List list = new ArrayList();
    for (int i = 0; i < Contants.TWELVE; i++) {
      list.add(
          formatDateToString(getDayFromNum(date, (Contants.TWELVE - i) * 30 - 30), DATE_FARMAT_5));
    }
    return list;
  }

  /** =================================大数据使用中================================= */

  // 获得当天近7天时间
  public static Date getWeekFromNow() {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000 * 7);
    return cal.getTime();
  }

  // 获得本周一0点时间
  public static Date getTimesWeekmorning() {
    Calendar cal = Calendar.getInstance();
    cal.set(
        cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal.getTime();
  }

  // 获得本周日24点时间
  public static Date getTimesWeeknight() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getTimesWeekmorning());
    cal.add(Calendar.DAY_OF_WEEK, 7);
    return cal.getTime();
  }

  // 获得本月第一天0点时间
  public static Date getTimesMonthmorning() {
    return getTimesMonthmorning(new Date());
  }

  // 获得本月最后一天24点时间
  public static Date getTimesMonthnight() {
    return getTimesMonthnight(new Date());
  }

  /**
   * 获得传入时间的月份第一天0点时间
   *
   * @return
   */
  public static Date getTimesMonthmorning(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(
        cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    return cal.getTime();
  }

  /**
   * 获得传入时间的月份最后一天24点时间
   *
   * @return
   */
  public static Date getTimesMonthnight(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(
        cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    cal.set(Calendar.HOUR_OF_DAY, 24);
    return cal.getTime();
  }

  public static Date getLastMonthStartMorning() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getTimesMonthmorning());
    cal.add(Calendar.MONTH, -1);
    return cal.getTime();
  }

  public static Date getCurrentQuarterStartTime() {
    Calendar c = Calendar.getInstance();
    int currentMonth = c.get(Calendar.MONTH) + 1;
    SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    Date now = null;
    try {
      if (currentMonth >= 1 && currentMonth <= 3) {
        c.set(Calendar.MONTH, 0);
      } else if (currentMonth >= 4 && currentMonth <= 6) {
        c.set(Calendar.MONTH, 3);
      } else if (currentMonth >= 7 && currentMonth <= 9) {
        c.set(Calendar.MONTH, 4);
      } else if (currentMonth >= 10 && currentMonth <= 12) {
        c.set(Calendar.MONTH, 9);
      }
      c.set(Calendar.DATE, 1);
      now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return now;
  }

  /**
   * 当前季度的结束时间，即2012-03-31 23:59:59
   *
   * @return
   */
  public static Date getCurrentQuarterEndTime() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getCurrentQuarterStartTime());
    cal.add(Calendar.MONTH, 3);
    return cal.getTime();
  }

  /**
   * 获取本年开始时间
   *
   * @return
   */
  public static Date getCurrentYearStartTime() {
    Calendar cal = Calendar.getInstance();
    cal.set(
        cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
    return cal.getTime();
  }

  /**
   * 获取本年结束时间
   *
   * @return
   */
  public static Date getCurrentYearEndTime() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getCurrentYearStartTime());
    cal.add(Calendar.YEAR, 1);
    return cal.getTime();
  }

  /**
   * 获取去年结束时间
   *
   * @return
   */
  public static Date getLastYearStartTime() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getCurrentYearStartTime());
    cal.add(Calendar.YEAR, -1);
    return cal.getTime();
  }

  /**
   * 获取两个时间的时间差（小时）
   *
   * @param time1
   * @param time2
   * @return
   */
  public static Long timeDiffForHour(Date time1, Date time2) {
    long diff = time2.getTime() - time1.getTime();

    long nh = 1000 * 60 * 60; // 一小时的毫秒数

    long diffHour = diff / nh; // 计算差多少小时

    return diffHour;
  }

  /**
   * 获取两个时间的时间差（天）
   *
   * @param time1
   * @param time2
   * @return
   */
  public static Long timeDiffForDay(Date time1, Date time2) {
    long diff = time2.getTime() - time1.getTime();

    long nh = 1000 * 60 * 60 * 24; // 一小时的毫秒数

    long diffDay = diff / nh; // 计算差多少小时

    if (diff % nh > 0) {
      diffDay += 1;
    }

    return diffDay;
  }

  /**
   * 获取两个时间的时间差（分钟）
   *
   * @param time1
   * @param time2
   * @return
   */
  public static Long timeDiffForMin(Date time1, Date time2) {
    long diff = time2.getTime() - time1.getTime();

    long nh = 1000 * 60; // 一分钟的毫秒数

    long diffHour = diff / nh; // 计算差多少分钟

    return diffHour;
  }

  /**
   * 分钟前
   *
   * @param date
   * @param interval
   * @return
   */
  public static Date getPreviousMin(Date date, int interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, -interval);
    date = calendar.getTime();
    return date;
  }

  public static String timeDifferent(Date starttime, Date endtime) {
    String str ="".intern();
    long dif = (endtime.getTime() - starttime.getTime()) / 1000;
    long year = 365 * 24 * 60 * 60;
    long month = 30 * 24 * 60 * 60;
    long day = 24 * 60 * 60;
    long hour = 60 * 60;
    long minute = 60;
    if (dif > year) {
      str = Math.round((double) dif / year) + "年前";
    } else if (dif > month) {
      str = Math.round((double) dif / month) + "月前";
    } else if (dif > day) {
      str = Math.round((double) dif / day) + "天前";
    } else if (dif > hour) {
      str = Math.round((double) dif / hour) + "小时前";
    } else if (dif > minute) {
      str = Math.round((double) dif / minute) + "分钟前";
    } else if (dif < 60 && dif > 10) {
      str = dif + "秒前";
    } else {
      str = "刚刚";
    }
    return str;
  }

  /**
   * -时间加减
   *
   * @param date
   * @param type 年月日类型
   * @param num 数量
   * @return
   */
  public static Date countDate(Date date, int type, int num) {
    Calendar rightNow = Calendar.getInstance();
    rightNow.setTime(date);
    rightNow.add(type, num);
    return rightNow.getTime();
  }
}

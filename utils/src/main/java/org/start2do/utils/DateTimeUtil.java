/*
 * 文 件 名:  DateTimeUtil.java
 * 描    述:  日期和时间工具类文件
 */
package org.start2do.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Date;

/**
 * 日期和时间工具类 重构用Java 8 重写
 *
 * <p>提供日期和时间处理公共方法实现
 *
 * @author lijie
 * @date 2019 /08/04
 * @since
 */
public final class DateTimeUtil {
  /** 日期格式：yyyy-MM-dd HH:mm:ss */
  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  /** 日期格式：yyyyMMddHHmmss */
  public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

  /** 日期格式：yyyy-MM-dd */
  public static final String YYYY_MM_DD = "yyyy-MM-dd";

  /** 日期格式：yyyyMMdd */
  public static final String YYYYMMDD = "yyyyMMdd";

  /** 日期格式：yyyy-MM */
  public static final String YYYY_MM = "yyyy-MM";

  /** 日期格式：yyMMdd */
  public static final String YYMMDD = "yyMMdd";

  /** 日期格式：yyyyMMddHHmmssSSS 毫秒 */
  public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

  /** 日期格式：mm:ss */
  public static final String HH_MM = "HH:mm";

  /** 日期格式：yyyy-MM-dd HH:mm */
  public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

  /** 日期格式：MM-dd */
  public static final String MM_DD = "MM-dd";

  /** 24小时组成的横坐标名称 */
  public static final String HOUR = "hour";

  /** 数据库存储的date类型字段转成JAVA的String类型为2006-06-11 03:35:13.0，需要去掉后面的.0 */
  private static final String DATE_STRING_EXTRA = ".0";

  /** 默认构造方法 不允许实例化 */
  private DateTimeUtil() {}

  /**
   * 日期时间字符串格式转换
   *
   * <p>数据库存储的date类型字段转成JAVA的String类型为2006-06-11 03:35:13.0，需要去掉后面的.0
   *
   * @author lijie
   * @date 2019 /08/04
   * @since string.
   * @param dateStr 日期时间字符串
   * @return 去掉.0的日期时间字符串 string
   * @see [类、类#方法、类#成员]
   */
  public static String formatDateString(String dateStr) {
    if (null != dateStr) {
      // 后面包含.0日期时间字符串才需要处理
      if (dateStr.endsWith(DATE_STRING_EXTRA)) {
        return dateStr.substring(0, dateStr.length() - DATE_STRING_EXTRA.length());
      }
    }
    return dateStr;
  }

  /**
   * 将String型日期转换为想要的LocalDateTime型 <一句话功能简述> <功能详细描述>
   *
   * @author lijie
   * @date 2019 /08/04
   * @since date. date time.
   * @param currentTime currentTime
   * @return LocalDateTime date
   * @see [类、类#方法、类#成员]
   */
  public static LocalDateTime formatStringToDate(String currentTime) {
    return LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
  }

  /**
   * 将String型日期转换为想要的date型 <一句话功能简述> <功能详细描述>
   *
   * @author lijie
   * @date 2019 /08/04
   * @since date. date time.
   * @param time currentTime
   * @param dataFarmat the data farmat
   * @return LocalDateTime LocalDateTime
   * @see [类、类#方法、类#成员]
   */
  public static LocalDateTime formatStringToDate(String time, String dataFarmat) {
    return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(dataFarmat));
  }

  /**
   * Gets previous day. 指定时间的上XX天
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @param date the date 时间
   * @param interval the interval 距离该时间的Xx天
   * @return the previous day
   */
  public static LocalDateTime getPreviousDay(LocalDateTime date, int interval) {
    return date.minus(interval, ChronoUnit.DAYS);
  }

  /**
   * 得到当前格林威治的日期和时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return String utc date time now
   */
  public static String getUTCDateTimeNow() {
    SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    return formatter.format(LocalDateTime.now());
  }

  /**
   * 获取某天0点时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @param dateTime the date time
   * @return times of day
   */
  public static LocalDateTime getTimesOfDay(LocalDateTime dateTime) {
    return dateTime.withNano(0).withMinute(0).withHour(0).withSecond(0);
  }

  /**
   * 获得某天23:59:59点时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @param date the date
   * @return timesnight timesnight
   */
  public static LocalDateTime getTimesnight(LocalDateTime date) {
    return date.withNano(999999999).withMinute(59).withHour(23).withSecond(59);
  }

  /**
   * 获得昨天0点时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return yesterdaymorning yesterdaymorning
   */
  public static LocalDateTime getYesterDayStart() {
    return getTodayStart().minus(1, ChronoUnit.DAYS);
  }

  /**
   * 获得昨天23:59:59点时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return yesterdaynight yesterdaynight
   */
  public static LocalDateTime getYesterdaynight() {
    return getTodayEnd().minus(1, ChronoUnit.DAYS);
  }

  /**
   * 获得上周同一天0点时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return weekmorning weekmorning
   */
  public static LocalDateTime getLastWeekStart() {
    return LocalDateTime.now()
        .withNano(0)
        .withMinute(0)
        .withHour(0)
        .withSecond(0)
        .minus(1, ChronoUnit.WEEKS);
  }

  /**
   * 获得上周同一天23:59:59点时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return weeknight weeknight
   */
  public static LocalDateTime getLastWeekEnd() {
    return LocalDateTime.now()
        .withNano(999999999)
        .withMinute(59)
        .withHour(23)
        .withSecond(59)
        .minus(1, ChronoUnit.WEEKS);
  }

  /**
   * 获取当前时间是今年的第几周
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @param dayOfWeek the day of week 设置星期几为一周的第一天,默认星期一
   * @return week week
   */
  public static Integer getWeekOfYear(DayOfWeek dayOfWeek) {
    if (dayOfWeek == null) {
      dayOfWeek = DayOfWeek.MONDAY;
    }
    WeekFields weekFields = WeekFields.of(dayOfWeek, 1);
    return LocalDateTime.now().get(weekFields.weekOfYear());
  }

  /**
   * 获取当前时间为一年中第几个月
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return month month
   */
  public static Integer getMonthOfYear() {
    return LocalDateTime.now().getMonthValue();
  }

  /**
   * Gets today start. 获取今天0点
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return the today start
   */
  public static LocalDateTime getTodayStart() {
    return LocalDateTime.now().withHour(0).withMinute(0).withNano(0).withSecond(0);
  }
  /**
   * Gets today end. 获取今天23 59 59
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return the today end
   */
  public static LocalDateTime getTodayEnd() {
    return LocalDateTime.now().withNano(999999999).withMinute(59).withHour(23).withSecond(59);
  }
  /**
   * Gets times monthmorning. 获得本月第一天0点时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return the times monthmorning
   */
  public static LocalDateTime getMonthStart() {
    return getTodayStart().withDayOfMonth(1);
  }

  /**
   * Gets times monthnight. 获得本月最后一天24点时间 因为最后一天24点已经是第二天的0点了
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return the times monthnight
   */
  public static LocalDateTime getMonthEnd() {
    return getMonthStart().plus(1, ChronoUnit.MONTHS);
  }

  /**
   * Gets current quarter start time.
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return the current quarter start time
   */
  public static LocalDateTime getCurrentQuarterStartTime() {
    Integer month = getMonthOfYear();
    LocalDateTime result = getMonthStart();
    if (month < 4) {
      return result.withMonth(0);
    } else if (month < 7) {
      return result.withMonth(3);
    } else if (month < 10) {
      return result.withMonth(6);
    } else {
      return result.withMonth(9);
    }
  }

  /**
   * 当前季度的结束时间，即2012-03-31 23:59:59
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return current quarter end time
   */
  public static LocalDateTime getCurrentQuarterEndTime() {
    return getCurrentQuarterStartTime().plus(3, ChronoUnit.MONTHS);
  }

  /**
   * 获取本年开始时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return current year start time
   */
  public static LocalDateTime getCurrentYearStartTime() {
    return getTodayStart().withDayOfYear(1);
  }

  /**
   * 获取本年结束时间
   *
   * @author lijie
   * @date 2019 /08/04
   * @since *
   * @return current year end time
   */
  public static LocalDateTime getCurrentYearEndTime() {
    return getCurrentYearStartTime().plus(1, ChronoUnit.YEARS);
  }

  /**
   * Time different 计算时间差异
   *
   * @author lijie
   * @date 2019 /08/04
   * @since string.
   * @param start the start
   * @param end the end
   * @return the string
   */
  public static Duration timeDifferent(LocalDateTime start, LocalDateTime end) {
    return Duration.between(start, end);
  }
  /**
   * Time different 计算时间差异
   *
   * @author lijie
   * @date 2019 /08/04
   * @since string.
   * @param start the start
   * @param end the end
   * @return the string
   */
  public static Period timeDifferent(LocalDate start, LocalDate end) {
    return Period.between(start, end);
  }

  public static Date toDate(LocalDateTime dateTime) {
    return Date.from(dateTime.atZone(ZoneOffset.systemDefault()).toInstant());
  }

  public static Date toDate(LocalDate dateTime) {
    return Date.from(dateTime.atStartOfDay(ZoneOffset.systemDefault()).toInstant());
  }

  public static LocalDate toLocalDate(Date dateTime) {
    return toLocalDateTime(dateTime).toLocalDate();
  }

  public static LocalDateTime toLocalDateTime(Instant dateTime) {
    return LocalDateTime.ofInstant(dateTime, ZoneOffset.systemDefault());
  }

  public static LocalDateTime toLocalDateTime(Date dateTime) {
    return LocalDateTime.ofInstant(dateTime.toInstant(), ZoneOffset.systemDefault());
  }

  public static Duration timeDifferent(Date startTime, Date endTime) {
    return timeDifferent(toLocalDateTime(startTime), toLocalDateTime(endTime));
  }
}

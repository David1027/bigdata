package com.shoestp.mains.schedulers;

import lombok.Getter;
import lombok.Setter;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.ScheduleBuilder;

import org.springframework.scheduling.quartz.QuartzJobBean;
/** * 定时任务父类 */
public abstract class BaseSchedulers extends QuartzJobBean {
  @Setter @Getter private String jobNmae;

  @Setter @Getter private ScheduleBuilder scheduleBuilder;

  @Setter @Getter private DailyTimeIntervalScheduleBuilder dailyTimeIntervalScheduleBuilder;

}

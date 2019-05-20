package com.shoestp.mains.schedulers;

import lombok.Getter;
import lombok.Setter;
import org.quartz.SimpleScheduleBuilder;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class BaseSchedulers extends QuartzJobBean {
  @Setter @Getter private String jobNmae = this.getClass().getName();

  @Setter @Getter private SimpleScheduleBuilder scheduleBuilder;

  public BaseSchedulers(SimpleScheduleBuilder scheduleBuilder, String jobNmae) {
    this.scheduleBuilder = scheduleBuilder;
    this.jobNmae = jobNmae;
  }
}

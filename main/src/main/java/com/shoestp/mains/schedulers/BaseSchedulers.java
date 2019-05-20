package com.shoestp.mains.schedulers;

import org.quartz.SimpleScheduleBuilder;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseSchedulers extends QuartzJobBean {
  @Setter @Getter private String jobNmae = this.getClass().getName();

  @Setter @Getter private SimpleScheduleBuilder scheduleBuilder;

  public BaseSchedulers(SimpleScheduleBuilder scheduleBuilder, String jobNmae) {
    this.scheduleBuilder = scheduleBuilder;
    this.jobNmae = jobNmae;
  }
  //
  //  public JobDetail JobDetail() {
  //    return JobBuilder.newJob(this.getClass()).withIdentity(jobNmae).storeDurably().build();
  //  }
  //
  //  public void defaults() {
  //    SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever();
  //  }
  //
  //  public Trigger sampleJobTrigger() {
  //    return TriggerBuilder.newTrigger()
  //        .forJob(JobDetail())
  //        .withIdentity(jobNmae + "Trigger")
  //        .withSchedule(scheduleBuilder)
  //        .build();
  //  }
}

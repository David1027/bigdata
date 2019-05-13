package com.shoestp.mains.schedulers;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseSchedulers extends QuartzJobBean {
  @Setter @Getter private String jobNmae = this.getClass().getName();

  private SimpleScheduleBuilder scheduleBuilder;

  public BaseSchedulers(SimpleScheduleBuilder scheduleBuilder) {
    this.scheduleBuilder = scheduleBuilder;
  }

  @Bean
  public JobDetail JobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(jobNmae).storeDurably().build();
  }

  public void defaults() {
    SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever();
  }

  @Bean
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(JobDetail())
        .withIdentity(jobNmae + "Trigger")
        .withSchedule(scheduleBuilder)
        .build();
  }
}

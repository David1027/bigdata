package com.shoestp.mains.schedulers;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class BaseSchedulers extends QuartzJobBean {
  private static final Logger logger = LogManager.getLogger(BaseSchedulers.class);
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

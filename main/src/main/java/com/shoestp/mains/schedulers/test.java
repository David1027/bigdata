package com.shoestp.mains.schedulers;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import org.springframework.stereotype.Component;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/8 Time: 14:32 */
@Component
public class test extends BaseSchedulers {

  public test() {
    super(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever());
  }

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext)
      throws JobExecutionException {
    System.out.println("我是定时任务");
  }
}

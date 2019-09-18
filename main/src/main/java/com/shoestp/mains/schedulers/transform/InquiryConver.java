package com.shoestp.mains.schedulers.transform;

import com.shoestp.mains.schedulers.BaseSchedulers;
import com.shoestp.mains.service.dataview.inquiry.InquiryNewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * The type Inquiry conver.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /09/17
 * @since
 * @description: 定时转化源数据表
 * @author: lingjian
 * @create: 2019 /8/16 10:25
 */
@Component
public class InquiryConver extends BaseSchedulers {

  /** The constant logger. */
  private static final Logger logger = LogManager.getLogger(InquiryConver.class);

  /**
   * Init
   *
   * @author lijie
   * @date 2019 /09/17
   * @since .
   */
  @PostConstruct
  public void init() {
    setJobNmae(InquiryConver.class.getName());
    setScheduleBuilder(
        DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
            .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(0, 1))
            .withInterval(24, DateBuilder.IntervalUnit.HOUR));
  }

  /** The Enable. */
  @Value("${shoestp.scheduler.InquiryConver.enable}")
  private Boolean enable = false;

  /**
   * Job detail
   *
   * @author lijie
   * @date 2019 /09/17
   * @since job detail.
   * @return the job detail
   */
  @Bean(name = "InquiryConver")
  public JobDetail jobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(getJobNmae()).storeDurably().build();
  }

  /**
   * Sample job trigger
   *
   * @author lijie
   * @date 2019 /09/17
   * @since trigger.
   * @return the trigger
   */
  @Bean(name = "InquiryConverTrigger")
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail())
        .withIdentity(getJobNmae() + "Trigger")
        .withSchedule(getScheduleBuilder())
        .build();
  }

  /** The Meta to view service. */
  @Resource private InquiryNewService inquiryNewService;

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext)
      throws JobExecutionException {
    if (!enable) {
      return;
    }
    logger.info("定时任务:{},现在开始执行", getJobNmae());
    inquiryNewService.schedulers();
  }
}

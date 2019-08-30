package com.shoestp.mains.schedulers.transform;

import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiryRank;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.entitys.dataview.user.DataViewUser;
import com.shoestp.mains.entitys.dataview.user.DataViewUserArea;
import com.shoestp.mains.schedulers.BaseSchedulers;
import com.shoestp.mains.service.transform.MetaToViewService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description: 定时转化源数据表
 * @author: lingjian
 * @create: 2019/8/16 10:25
 */
@Component
public class TransformConver extends BaseSchedulers {

  private static final Logger logger = LogManager.getLogger(TransformConver.class);

  @PostConstruct
  public void init() {
    setJobNmae(TransformConver.class.getName());
    setScheduleBuilder(
        DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
            .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(0, 0))
            .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(23, 59))
            .withIntervalInHours(1));
  }

  @Value("${shoestp.scheduler.transformconver.enable}")
  private Boolean enable = false;
  /** * 定时60分钟 */
  @Value("${shoestp.scheduler.transformconver.timing}")
  private int timing = 60;

  @Bean(name = "TransformConver")
  public JobDetail jobDetail() {
    return JobBuilder.newJob(this.getClass()).withIdentity(getJobNmae()).storeDurably().build();
  }

  @Bean(name = "TransformConverTrigger")
  public Trigger sampleJobTrigger() {
    return TriggerBuilder.newTrigger()
        .forJob(jobDetail())
        .withIdentity(getJobNmae() + "Trigger")
        .withSchedule(getScheduleBuilder())
        .build();
  }

  @Resource private MetaToViewService metaToViewService;

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext)
      throws JobExecutionException {
    if (!enable) {
      return;
    }
    Date start = DateTimeUtil.getPreviousHour(new Date(), -1);
    Date end = new Date();
    try {
      // 转化实时表
      DataViewReal real = metaToViewService.toReal(start, end);
      logger.debug("执行成功=====> " + real);
      // 转化流量表
      List<DataViewFlow> flow = metaToViewService.toFlow(start, end);
      logger.debug("执行成功=====> " + flow);
      // 转化页面分析表
      List<DataViewFlowPage> flowPage = metaToViewService.toFlowPage(start, end);
      logger.debug("执行成功=====> " + flowPage);
      // 转化询盘表
      DataViewInquiry inquiry = metaToViewService.toInquiry(start, end);
      logger.debug("执行成功=====> " + inquiry);
      // 转化询盘排行表
      List<DataViewInquiryRank> inquiryRank = metaToViewService.toInquiryRank(start, end);
      logger.debug("执行成功=====> " + inquiryRank);
      // 转化用户表
      DataViewUser user = metaToViewService.toUser(start, end);
      logger.debug("执行成功=====> " + user);
      // 转化用户地域表
      List<DataViewUserArea> userArea = metaToViewService.toUserArea(start, end);
      logger.debug("执行成功=====> " + userArea);
      // 转化国家表
      List<DataViewCountry> country = metaToViewService.toCountry(start, end);
      logger.debug("执行成功=====> " + country);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }
  }
}

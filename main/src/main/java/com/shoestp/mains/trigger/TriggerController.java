package com.shoestp.mains.trigger;

import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.entitys.dataview.user.DataViewUser;
import com.shoestp.mains.entitys.dataview.user.DataViewUserArea;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.dataview.inquiry.InquiryNewService;
import com.shoestp.mains.service.metadata.UserInfoService;
import com.shoestp.mains.service.transform.MetaToViewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/trigger")
public class TriggerController {
  private static final Logger logger = LogManager.getLogger(TriggerController.class);
  @Resource private InquiryNewService inquiryNewService;
  @Resource private MetaToViewService metaToViewService;
  @Resource private UserInfoService userInfoService;

  @PostMapping(value = "/exec")
  public MessageResult exec(
      String key,
      String type,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end) {
    if (!"adgawhiudhnawjdhygaswi8fhjesubfguaswhfewsgu#DFDYHFGDTYHFDFRTDERyfeghswiuf".equals(key)) {
      return MessageResult.builder().msg("无效密钥").build();
    }
    switch (type) {
      case "inquiryRank":
        inquiryNewService.schedulers();
        break;
        // 转化实时表
      case "real":
        DataViewReal real = metaToViewService.toReal(start, end);
        logger.debug("执行成功=====> " + real);
        break;
      case "flow":
        // 转化流量表
        List<DataViewFlow> flow = metaToViewService.toFlow(start, end);
        logger.debug("执行成功=====> " + flow);
        break;
      case "flowPage":
        // 转化页面分析表
        List<DataViewFlowPage> flowPage = metaToViewService.toFlowPage(start, end);
        logger.debug("执行成功=====> " + flowPage);
        break;
      case "inquiry":
        // 转化询盘表
        DataViewInquiry inquiry = metaToViewService.toInquiry(start, end);
        logger.debug("执行成功=====> " + inquiry);
        break;
        //      case "inquiryRank":
        //        // 转化询盘排行表
        //        List<DataViewInquiryRank> inquiryRank = metaToViewService.toInquiryRank(start,
        // end);
        //        logger.debug("执行成功=====> " + inquiryRank);
      case "user":
        // 转化用户表
        DataViewUser user = metaToViewService.toUser(start, end);
        logger.debug("执行成功=====> " + user);
        break;
      case "userArea":
        // 转化用户地域表
        List<DataViewUserArea> userArea = metaToViewService.toUserArea(start, end);
        logger.debug("执行成功=====> " + userArea);
        break;
      case "country":
        /** 转化国家表 */
        List<DataViewCountry> country = metaToViewService.toCountry(start, end);
        logger.debug("执行成功=====> " + country);
        break;
      case "removeDuplicateUser":
        userInfoService.removeDuplicateUser();
        break;
      case "ALL":
        inquiryNewService.schedulers();
        metaToViewService.toReal(start, end);
        metaToViewService.toFlow(start, end);
        metaToViewService.toFlowPage(start, end);
        metaToViewService.toInquiry(start, end);
        metaToViewService.toUser(start, end);
        metaToViewService.toUserArea(start, end);
        metaToViewService.toCountry(start, end);
        break;
      default:
        return MessageResult.builder().msg("没有该触发器").build();
    }
    return MessageResult.builder().msg("没有该触发器").build();
  }
}

package com.shoestp.mains.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metaData.WebVisitInfoService;
import com.shoestp.mains.views.analytics.WebVisitInfoView;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 用于接受js统计信息 */
@RequestMapping("/api/analytics")
@RestController
@CrossOrigin
public class AnalyticsController extends BaseController {
  private static final Logger logger = LogManager.getLogger(AnalyticsController.class);

  @Resource WebVisitInfoService webVisitInfoService;
  @Resource ObjectMapper objectMapper;

  @PostMapping({"save", ""})
  public Object save(@RequestBody String body, HttpServletRequest httpRequest) throws IOException {
    logger.info("Body Messages =>{}", body);
    WebVisitInfoView webVisitInfoView = objectMapper.readValue(body, WebVisitInfoView.class);
    logger.info("Pojo Info =>{}", webVisitInfoView);
    webVisitInfoService.save(
        webVisitInfoView, getIpByHeader(httpRequest), getUserAgentByHeader(httpRequest));

    return MessageResult.builder().code(1).build();
  }
}

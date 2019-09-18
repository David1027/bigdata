package com.shoestp.mains.controllers.analytics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoestp.mains.constant.analytics.AnalyticsConstant;
import com.shoestp.mains.controllers.BaseController;
import com.shoestp.mains.controllers.analytics.view.SignView;
import com.shoestp.mains.controllers.analytics.view.WebVisitInfoView;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.metadata.LocationService;
import com.shoestp.mains.service.metadata.WebVisitInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * 用于接受js统计信息
 *
 * @author lijie
 * @date 2019 /08/06
 * @since
 */
@RequestMapping("/api/analytics")
@RestController
@CrossOrigin
public class AnalyticsController extends BaseController {
  private static final Logger logger = LogManager.getLogger(AnalyticsController.class);

  @Resource WebVisitInfoService webVisitInfoService;
  @Resource ObjectMapper objectMapper;
  @Resource private LocationService locationService;

  @PostMapping({"save", ""})
  public Object save(@RequestBody String body, HttpServletRequest httpRequest) throws IOException {
    logger.info("Body Messages =>{}", body);
    WebVisitInfoView webVisitInfoView = objectMapper.readValue(body, WebVisitInfoView.class);
    logger.info("Pojo Info =>{}", webVisitInfoView);
    if (webVisitInfoView.getPage_wait_time() > AnalyticsConstant.PAGEONTIME) {
      webVisitInfoView.setPage_wait_time(AnalyticsConstant.PAGEONTIME);
    }
    webVisitInfoService.save(
        webVisitInfoView, getIpByHeader(httpRequest), getUserAgentByHeader(httpRequest));
    return MessageResult.builder().code(1).build();
  }

  @GetMapping("device_sign")
  public Object device_sign(HttpServletRequest httpRequest, String get) throws IOException {
    logger.debug(
        "device_sign :{},{}", getIpByHeader(httpRequest), getUserAgentByHeader(httpRequest));
    SignView sign = new SignView();
    sign.setSession(UUID.randomUUID().toString().replace("-", ""));
    if (get != null && "session".equalsIgnoreCase(get)) {
      return sign;
    }
    sign.setSign(UUID.randomUUID().toString().replace("-", ""));
    return sign;
  }

  @RequestMapping(value = "fix")
  public Object fixData() {
    webVisitInfoService.fixdata();
    return "ok";
  }

  @GetMapping("test")
  public Object test(String ip) {
    logger.debug(ip);
    return locationService.getCountryByIp(ip);
  }
}

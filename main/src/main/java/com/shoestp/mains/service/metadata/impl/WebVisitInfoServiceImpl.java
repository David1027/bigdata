package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.config.AppConfig;
import com.shoestp.mains.controllers.analytics.view.WebVisitInfoView;
import com.shoestp.mains.dao.shoestpdata.WebVisitInfoDao;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.service.metadata.AddressService;
import com.shoestp.mains.service.metadata.UserInfoService;
import com.shoestp.mains.service.metadata.WebVisitInfoService;
import com.shoestp.mains.utils.iputils.City;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.start2do.utils.DateTimeUtil;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 */
@Service
public class WebVisitInfoServiceImpl implements WebVisitInfoService {
  private static final Logger logger = LogManager.getLogger(WebVisitInfoServiceImpl.class);
  @Resource private WebVisitInfoDao webVisitInfoDao;
  @Resource private UserInfoService userInfoService;
  @Resource private AddressService addressService;
  @Resource private AppConfig appConfig;

  @Resource(name = "ipCity")
  private City city;

  @Override
  public WebVisitInfo save(WebVisitInfo webVisitInfo) {
    return webVisitInfoDao.save(webVisitInfo);
  }

  @Override
  public WebVisitInfo save(WebVisitInfoView pojo, String ip, String ua) {
    WebVisitInfo webVisitInfo = new WebVisitInfo();
    webVisitInfo.setTitle(pojo.getTitle());
    webVisitInfo.setUrl(pojo.getUrl());
    webVisitInfo.setUri(pojo.getUri());
    webVisitInfo.setUserAgent(ua);
    webVisitInfo.setReferer(pojo.getFirstReferrer());
    switch (UserAgent.parseUserAgentString(ua).getOperatingSystem().getDeviceType()) {
      case MOBILE:
      case TABLET:
        webVisitInfo.setEquipmentPlatform(DeviceTypeEnum.WAP);
        break;
      case UNKNOWN:
      case COMPUTER:
      case WEARABLE:
      case GAME_CONSOLE:
      default:
        webVisitInfo.setEquipmentPlatform(DeviceTypeEnum.PC);
    }
    webVisitInfo.setIp(ip);
    webVisitInfo.setUserId(userInfoService.save(pojo.getUserInfo()));
    String[] address = city.find(ip);
    if (address != null && address.length > 1) {
      webVisitInfo.setLocation(addressService.getCountry(address[0]));
      webVisitInfo.setProvince(addressService.getProvince(address[1]));
    } else {
      logger.info("该IP{},未能找到响应国家", ip);
    }
    webVisitInfo.setImg(pojo.getImg());
    webVisitInfo.setPageWaitTime(pojo.getPageWaitTime());
    webVisitInfo.setTimeOnPage(pojo.getTimeOnPage());
    webVisitInfo.setSessionCreateTime(
        DateTimeUtil.toLocalDateTime(Instant.ofEpochMilli(pojo.getSession_create_time())));
    webVisitInfo.setSession(pojo.getSession());
    webVisitInfo.setCreateTime(new Date());
    return webVisitInfoDao.save(webVisitInfo);
  }
}

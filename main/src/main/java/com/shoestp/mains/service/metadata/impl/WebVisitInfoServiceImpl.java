package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.controllers.analytics.view.WebVisitInfoView;
import com.shoestp.mains.dao.metadata.WebVisitInfoDao;
import com.shoestp.mains.entitys.metadata.PltCountry;
import com.shoestp.mains.entitys.metadata.Province;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.service.metadata.LocationService;
import com.shoestp.mains.service.metadata.UserInfoService;
import com.shoestp.mains.service.metadata.WebVisitInfoService;
import com.shoestp.mains.service.urlmatchdatautil.URLMatchDataUtilService;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.start2do.utils.DateTimeUtil;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 */
@Service
public class WebVisitInfoServiceImpl implements WebVisitInfoService {
  private static final Logger logger = LogManager.getLogger(WebVisitInfoServiceImpl.class);

  @Resource(name = "com.shoestp.mains.dao.metadata.WebVisitDao")
  private WebVisitInfoDao webVisitInfoDao;

  @Resource private UserInfoService userInfoService;
  @Resource private LocationService locationService;
  @Resource private URLMatchDataUtilService urlMatchDataUtilService;

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
    String[] address = locationService.getAddress(ip);
    logger.debug("address Info:{}", address);
    if (address != null && address.length > 0) {
      webVisitInfo.setLocation(locationService.getCountry(address[0]));
      if (address.length > 1) {
        webVisitInfo.setProvince(locationService.getProvince(address[1]));
      }
    } else {
      logger.info("该IP{},未能找到响应国家", ip);
      /** 没有找到国家设置为Unkonw */
      webVisitInfo.setLocation(locationService.getCountry(null));
    }
    webVisitInfo.setUserId(
        userInfoService.save(
            pojo.getUserInfo(), webVisitInfo.getLocation(), webVisitInfo.getProvince()));
    webVisitInfo.setImg(pojo.getImg());
    webVisitInfo.setPageWaitTime(pojo.getPage_wait_time());
    webVisitInfo.setTimeOnPage(pojo.getTime_on_page());
    webVisitInfo.setSessionCreateTime(
        DateTimeUtil.toLocalDateTime(Instant.ofEpochMilli(pojo.getSession_create_time())));
    webVisitInfo.setSession(pojo.getSession());
    webVisitInfo.setCreateTime(new Date());
    /** @modify Lijie HelloBox@outlook.com 2019-08-07 16:53 根据lingjiang要求添加页面类型 */
    webVisitInfo.setPageType(urlMatchDataUtilService.getPageType(pojo.getUri()));
    return webVisitInfoDao.save(webVisitInfo);
  }

  /**
   * Fixdata 修复数据
   *
   * @author lijie
   * @date 2019 /08/28
   * @since .
   */
  @Override
  public void fixdata() {
    List<WebVisitInfo> list = webVisitInfoDao.findAll();
    list.forEach(
        webVisitInfo -> {
          boolean update = false;
          String[] address = locationService.getAddress(webVisitInfo.getIp());
          PltCountry country = locationService.getCountry(address[0]);
          if (country == null
              || webVisitInfo.getLocation() == null
              || !country.getId().equals(webVisitInfo.getLocation().getId())) {
            webVisitInfo.setLocation(country);
            update = false;
          }
          UserInfo userInfo = webVisitInfo.getUserId();
          userInfo.setCountry(country);
          if (address.length > 1) {
            Province province = locationService.getProvince(address[1]);
            webVisitInfo.setProvince(province);
            userInfo.setProvince(province);
          }
          userInfoService.update(userInfo);
          AccessTypeEnum anEnum = urlMatchDataUtilService.getPageType(webVisitInfo.getUri());
          if (!webVisitInfo.getPageType().equals(anEnum)) {
            webVisitInfo.setPageType(anEnum);
            update = true;
          }
          if (update) {
            webVisitInfoDao.saveAndFlush(webVisitInfo);
          }
        });
  }
}

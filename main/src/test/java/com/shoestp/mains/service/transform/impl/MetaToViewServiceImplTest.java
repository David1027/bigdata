package com.shoestp.mains.service.transform.impl;

import base.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoestp.mains.controllers.analytics.view.WebVisitInfoView;
import com.shoestp.mains.entitys.dataview.user.DataViewUser;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.service.metadata.UserInfoService;
import com.shoestp.mains.service.metadata.WebVisitInfoService;
import com.shoestp.mains.service.transform.MetaToViewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.start2do.utils.DateTimeUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The type Meta to view service impl test.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /08/09
 * @since
 */
public class MetaToViewServiceImplTest extends BaseTest {
  /** The constant logger. */
  private static final Logger logger = LogManager.getLogger(MetaToViewServiceImplTest.class);

  /** The Meta to view service. */
  @Resource private MetaToViewService metaToViewService;

  /** The Web visit info service. */
  @Resource private WebVisitInfoService webVisitInfoService;

  @Resource private UserInfoService userInfoService;

  /** The Object mapper. */
  @Resource private ObjectMapper objectMapper;

  /** The constant json. */
  public static final String json =
      "{\"title\":\"\",\"url\":\"http://localhost/\",\"uri\":\"/\",\"firstReferrer\":\"\",\"pageReferrer\":\"\",\"pageLoadInfo\":{\"navigationStart\":1565312498207,\"unloadEventStart\":0,\"unloadEventEnd\":0,\"redirectStart\":0,\"redirectEnd\":0,\"fetchStart\":1565312498212,\"domainLookupStart\":1565312498216,\"domainLookupEnd\":1565312498216,\"connectStart\":1565312498216,\"connectEnd\":1565312498226,\"secureConnectionStart\":0,\"requestStart\":1565312498227,\"responseStart\":1565312498611,\"responseEnd\":1565312498615,\"domLoading\":1565312498619,\"domInteractive\":1565312498954,\"domContentLoadedEventStart\":1565312498954,\"domContentLoadedEventEnd\":1565312498956,\"domComplete\":1565312499229,\"loadEventStart\":1565312499229,\"loadEventEnd\":1565312499232},\"mouseClick\":[],\"windows\":{\"height\":1080,\"width\":1920},\"userInfo\":{\"userName\":\"3aee7465de0e4c3f84cc877d2c279139\"},\"session\":\"107e807c898e450391f7b2da3e53f8d3\",\"session_create_time\":1565312509175}\n";
  /**
   * ; Sets up.
   *
   * @author lijie
   * @date 2019 /08/08
   * @since *
   * @throws IOException the io exception
   */
  @BeforeEach
  void setUp() throws IOException {}

  /**
   * Old 测试新游客逻辑
   *
   * @author lijie
   * @date 2019 /08/09
   * @since .
   * @throws IOException the io exception
   */
  @Test
  void newuser() throws IOException {
    WebVisitInfoView webVisitInfoView = objectMapper.readValue(json, WebVisitInfoView.class);
    webVisitInfoService.save(webVisitInfoView, "127.0.0.1", "ios");
    UserInfo userInfo = userInfoService.getUser("3aee7465de0e4c3f84cc877d2c279139");
    userInfo.setCreateTime(DateTimeUtil.toDate(DateTimeUtil.getLastWeekEnd().plusHours(1)));
    userInfoService.update(userInfo);
    DataViewUser viewUser =
        metaToViewService.toUser(
            DateTimeUtil.toDate(DateTimeUtil.getLastWeekEnd()),
            DateTimeUtil.toDate(LocalDateTime.now()));
    logger.info(viewUser);
    Assert.assertEquals(viewUser.getNewVisitorCount().intValue(), 1);
  }

  /**
   * Newuser 测试老游客逻辑
   *
   * @author lijie
   * @date 2019 /08/09
   * @since .
   * @throws IOException the io exception
   */
  @Test
  void old() throws IOException {
    WebVisitInfoView webVisitInfoView = objectMapper.readValue(json, WebVisitInfoView.class);
    webVisitInfoService.save(webVisitInfoView, "127.0.0.1", "ios");
    UserInfo userInfo = userInfoService.getUser("3aee7465de0e4c3f84cc877d2c279139");
    userInfo.setCreateTime(DateTimeUtil.toDate(DateTimeUtil.getLastWeekEnd().minusDays(1)));
    userInfoService.update(userInfo);
    DataViewUser viewUser =
        metaToViewService.toUser(
            DateTimeUtil.toDate(DateTimeUtil.getLastWeekEnd()),
            DateTimeUtil.toDate(LocalDateTime.now()));
    logger.info(viewUser);
    Assert.assertEquals(viewUser.getOldVisitorCount().intValue(), 1);
  }
}

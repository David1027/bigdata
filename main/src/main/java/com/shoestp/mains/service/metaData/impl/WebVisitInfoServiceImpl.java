package com.shoestp.mains.service.metaData.impl;

import com.shoestp.mains.dao.shoestpData.WebVisitInfoDao;
import com.shoestp.mains.entitys.MetaData.WebVisitInfo;
import com.shoestp.mains.service.metaData.WebVisitInfoService;
import com.shoestp.mains.views.analytics.WebVisitInfoView;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 */
@Service
public class WebVisitInfoServiceImpl implements WebVisitInfoService {

  @Resource WebVisitInfoDao webVisitInfoDao;

  @Override
  public WebVisitInfo save(WebVisitInfo webVisitInfo) {
    return webVisitInfoDao.save(webVisitInfo);
  }

  @Override
  public WebVisitInfo save(WebVisitInfoView pojo, String ip, String ua) {
    //    TODO 夏炎IP库引入后需要对localtion进行设置
    WebVisitInfo webVisitInfo = new WebVisitInfo();
    webVisitInfo.setTitle(pojo.getTitle());
    webVisitInfo.setUrl(pojo.getUrl());
    webVisitInfo.setUserAgent(ua);
    webVisitInfo.setReferer(pojo.getFirstReferrer());
    webVisitInfo.setIp(ip);
    if (pojo.getUserInfo() != null) {
      webVisitInfo.setUserId(pojo.getUserInfo().getUserId());
      webVisitInfo.setVisitName(pojo.getUserInfo().getUserName());
    }
    webVisitInfo.setCreateTime(new Date());
    webVisitInfoDao.save(webVisitInfo);
    return null;
  }
}

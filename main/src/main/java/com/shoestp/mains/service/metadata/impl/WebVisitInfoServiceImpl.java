package com.shoestp.mains.service.metadata.impl;

import com.shoestp.mains.config.AppConfig;
import com.shoestp.mains.dao.shoestpdata.WebVisitInfoDao;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.service.metadata.WebVisitInfoService;
import com.shoestp.mains.utils.iputils.City;
import com.shoestp.mains.views.Page;
import com.shoestp.mains.views.analytics.WebVisitInfoView;
import com.shoestp.mains.views.dataview.metadata.QueryWebVisitInfoView;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.start2do.utils.MyStringUtils;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 */
@Service
public class WebVisitInfoServiceImpl implements WebVisitInfoService {

  @Resource private WebVisitInfoDao webVisitInfoDao;
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
    webVisitInfo.setUserAgent(ua);
    webVisitInfo.setReferer(pojo.getFirstReferrer());
    webVisitInfo.setIp(ip);
    if (pojo.getUserInfo() != null) {
      if (pojo.getUserInfo().getUserId() != null) {
//        webVisitInfo.setUserId(pojo.getUserInfo().getUserId());
        webVisitInfo.setVisitName(pojo.getUserInfo().getUserName());
      } else {
//        webVisitInfo.setUserId(-1);
        webVisitInfo.setVisitName(pojo.getUserInfo().getUserName());
      }
    } else {
//      webVisitInfo.setUserId(-1);
      webVisitInfo.setVisitName("游客");
    }
    String[] find = city.find(ip);
    if (find.length > 0) {
//      webVisitInfo.setLocation(find[0]);
//      if ("中国".equals(find[0])) {
//        webVisitInfo.setProvince(find[1]);
//      }
    }
    webVisitInfo.setCreateTime(new Date());
    return webVisitInfoDao.save(webVisitInfo);
  }

  @Override
  public Page<QueryWebVisitInfoView> getWebVisitInfo(
      Integer visitType, Integer sourceType, String page, String country, int start, int limit) {
    Map<String, Object> queryList =
        webVisitInfoDao.queryList(visitType, sourceType, page, country, start, limit);
    long count = (long) queryList.get("count");
    List<WebVisitInfo> list = (List<WebVisitInfo>) queryList.get("list");
    List<QueryWebVisitInfoView> listView =
        list.stream()
            .map(
                bean ->
                    new QueryWebVisitInfoView() {
                      {
                        setDate(bean.getCreateTime());
                        setId(bean.getId());
//                        setLoation(bean.getLocation());
                        setPage(bean.getUrl());
                        setName(bean.getVisitName());
                        if (bean.getReferer().indexOf("google.com") != -1) {
                          setSource("Google");
                        } else if (bean.getReferer().indexOf("baidu.com") != -1) {
                          setSource("Baidu");
                        } else if ("".equals(bean.getReferer())) {
                          setSource("自主访问");
                        } else {
                          if (appConfig.getDomain() != null) {
                            for (String s : appConfig.getDomain()) {
                              if (MyStringUtils.isMatch(bean.getReferer(), "*" + s + "*")) {
                                setSource("站内跳转");
                                break;
                              }
                            }
                          }
                          if (getSource() == null || getSource().length() < 1) {
                            setSource("社交访问");
                          }
                        }
                      }
                    })
            .collect(Collectors.toList());
    Page<QueryWebVisitInfoView> pages = new Page<>();
    pages.setList(listView);
    pages.setTotal(count);
    return pages;
  }
}

package com.shoestp.mains.service.metaData.impl;

import com.shoestp.mains.dao.shoestpData.WebVisitInfoDao;
import com.shoestp.mains.entitys.metaData.WebVisitInfo;
import com.shoestp.mains.service.metaData.WebVisitInfoService;
import com.shoestp.mains.views.DataView.metaData.QueryWebVisitInfoView;
import com.shoestp.mains.views.Page;
import com.shoestp.mains.views.analytics.WebVisitInfoView;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

  public List<WebVisitInfoView> queryWebVisitInfo(
      Integer visitType, Integer sourceType, String page) {
    return null;
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
                        setLoation(bean.getLocation());
                        setPage(bean.getUrl());
                        setName(bean.getVisitName());
                        if (bean.getReferer().indexOf("google.com") != -1) {
                          setSource("Google");
                        } else if (bean.getReferer().indexOf("baidu.com") != -1) {
                          setSource("Baidu");
                        } else if (bean.getReferer().equals("")) {
                          setSource("自主访问");
                        } else {
                          setSource("社交访问");
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

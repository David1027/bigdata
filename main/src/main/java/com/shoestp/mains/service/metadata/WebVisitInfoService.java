package com.shoestp.mains.service.metadata;

import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.views.dataview.metadata.QueryWebVisitInfoView;
import com.shoestp.mains.views.Page;
import com.shoestp.mains.views.analytics.WebVisitInfoView;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 */
public interface WebVisitInfoService {

  WebVisitInfo save(WebVisitInfo webVisitInfo);

  WebVisitInfo save(WebVisitInfoView webVisitInfoView, String ip, String ua);

  public Page<QueryWebVisitInfoView> getWebVisitInfo(
      Integer visitType, Integer sourceType, String page, String country, int start, int limit);
}

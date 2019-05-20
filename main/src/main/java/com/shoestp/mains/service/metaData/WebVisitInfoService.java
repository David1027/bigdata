package com.shoestp.mains.service.metaData;

import com.shoestp.mains.entitys.MetaData.WebVisitInfo;
import com.shoestp.mains.views.analytics.WebVisitInfoView;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 */
public interface WebVisitInfoService {

  WebVisitInfo save(WebVisitInfo webVisitInfo);

  WebVisitInfo save(WebVisitInfoView webVisitInfoView, String ip, String ua);
}

package com.shoestp.mains.service.metadata;

import com.shoestp.mains.controllers.analytics.view.WebVisitInfoView;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.views.Page;
import com.shoestp.mains.views.dataview.metadata.QueryWebVisitInfoView;

/**
 * Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 @author lijie
 *
 * @date 2019 /08/06
 * @since
 */
public interface WebVisitInfoService {

  /**
   * Save
   *
   * @author lijie
   * @date 2019 /08/06
   * @since web visit info.
   * @param webVisitInfo the web visit info
   * @return the web visit info
   */
  WebVisitInfo save(WebVisitInfo webVisitInfo);

  /**
   * Save
   *
   * @author lijie
   * @date 2019 /08/06
   * @since web visit info.
   * @param webVisitInfoView the web visit info view
   * @param ip the ip
   * @param ua the ua
   * @return the web visit info
   */
  WebVisitInfo save(WebVisitInfoView webVisitInfoView, String ip, String ua);


}

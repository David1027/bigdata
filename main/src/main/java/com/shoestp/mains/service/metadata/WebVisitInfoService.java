package com.shoestp.mains.service.metadata;

import com.shoestp.mains.controllers.analytics.view.WebVisitInfoView;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.views.Page;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 11:17 @author lijie
 *
 * @author lijie
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

  /**
   * Fixdata 修复数据
   *
   * @author lijie
   * @date 2019 /08/28
   * @since .
   */
  void fixdata();

  /**
   * Gets all by page type. 按照类型,并且根据开始结束时间的访问记录
   *
   * @author lijie
   * @date 2019 /09/12
   * @since *
   * @param startDate the start date
   * @param endDate the end date
   * @param start the start
   * @param limit the limit
   * @param typeEnum the type enum
   * @return the all by page type
   */
  Page<WebVisitInfo> getAllByPageTypeAndStartTimeAndEndTime(
      LocalDateTime startDate,
      LocalDateTime endDate,
      Integer start,
      Integer limit,
      AccessTypeEnum... typeEnum);
}

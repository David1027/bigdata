package com.shoestp.mains.views.dataview.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.transform.WebVisitDao;
import com.shoestp.mains.enums.flow.AccessTypeEnum;

import org.springframework.stereotype.Component;

/**
 * @description: 访客集合单例
 * @author: lingjian
 * @create: 2019/8/28 16:34
 */
@Component
public class VisitorList {

  @Resource private WebVisitDao webVisitDao;

  private List list = null;

  /**
   * 根据时间获取分组后的ip集合
   *
   * @author: lingjian @Date: 2019/8/29 15:12
   * @param start 开始时间
   * @param end 结束时间
   * @return List
   */
  public List getList(Date start, Date end) {
    list = webVisitDao.listIpGroup(null, start, end);
    return list;
  }

  /**
   * 根据页面类型，时间获取分组后的ip集合
   *
   * @author: lingjian @Date: 2019/8/29 15:13
   * @param accessTypeEnum 页面类型
   * @param start 开始时间
   * @param end 结束时间
   * @return List
   */
  public List getList(AccessTypeEnum accessTypeEnum, Date start, Date end) {
    list = webVisitDao.listIpGroup(accessTypeEnum, start, end);
    return list;
  }
}

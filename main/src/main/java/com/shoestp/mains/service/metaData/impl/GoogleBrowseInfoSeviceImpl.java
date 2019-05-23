package com.shoestp.mains.service.metaData.impl;

import com.shoestp.mains.dao.metaData.GoogleBrowseInfoDao;
import com.shoestp.mains.service.metaData.GoogleBrowseInfoSevice;
import com.shoestp.mains.views.DataView.metaData.PageRankingView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleBrowseInfoSeviceImpl implements GoogleBrowseInfoSevice {

  @Autowired private GoogleBrowseInfoDao googleBrowseInfoDao;

  /**
   * -获取访问页面排行 按页面浏览量排行
   *
   * @author xiayan
   * @param
   * @return
   */
  @Override
  public List<PageRankingView> getPageRanking(Date startTime, Date endTime, Integer limit) {
    if (startTime != null && endTime != null) {
      SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmm");
      String start = sim.format(startTime);
      String end = sim.format(endTime);
      return googleBrowseInfoDao.queryPageRanking(start, end, limit);
    } else {
      return googleBrowseInfoDao.queryPageRanking(limit);
    }
  }
}

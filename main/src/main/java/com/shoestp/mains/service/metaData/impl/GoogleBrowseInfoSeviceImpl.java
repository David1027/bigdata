package com.shoestp.mains.service.metaData.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoestp.mains.dao.metaData.GoogleBrowseInfoDao;
import com.shoestp.mains.service.metaData.GoogleBrowseInfoSevice;
import com.shoestp.mains.views.DataView.metaData.PageRankingView;

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
  public List<PageRankingView> getPageRanking(Integer limit) {
    return googleBrowseInfoDao.queryPageRanking(limit);
  }
}

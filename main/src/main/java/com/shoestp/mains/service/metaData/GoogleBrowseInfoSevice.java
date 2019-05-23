package com.shoestp.mains.service.metaData;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.views.DataView.metaData.PageRankingView;

public interface GoogleBrowseInfoSevice {

  public List<PageRankingView> getPageRanking(Date startTime, Date endTime, Integer limit);
}

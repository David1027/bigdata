package com.shoestp.mains.service.metaData;

import com.shoestp.mains.views.DataView.metaData.PageRankingView;
import java.util.Date;
import java.util.List;

public interface GoogleBrowseInfoSevice {

  public List<PageRankingView> getPageRanking(Date startTime, Date endTime, Integer limit);
}

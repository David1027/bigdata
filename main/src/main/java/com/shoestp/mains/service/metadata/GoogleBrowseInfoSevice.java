package com.shoestp.mains.service.metadata;

import com.shoestp.mains.views.dataview.metadata.PageRankingView;
import java.util.Date;
import java.util.List;

public interface GoogleBrowseInfoSevice {

  public List<PageRankingView> getPageRanking(Date startTime, Date endTime, Integer limit);
}

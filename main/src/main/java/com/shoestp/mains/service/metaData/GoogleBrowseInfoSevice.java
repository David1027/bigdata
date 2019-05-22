package com.shoestp.mains.service.metaData;

import java.util.List;

import com.shoestp.mains.views.DataView.metaData.PageRankingView;

public interface GoogleBrowseInfoSevice {

  public List<PageRankingView> getPageRanking(Integer limit);
}

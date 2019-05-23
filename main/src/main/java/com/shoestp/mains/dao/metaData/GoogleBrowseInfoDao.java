package com.shoestp.mains.dao.metaData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;
import com.shoestp.mains.repositorys.metaData.GoogleBrowseInfoRepository;
import com.shoestp.mains.views.DataView.metaData.PageRankingView;

@Component
public class GoogleBrowseInfoDao extends BaseDao<GoogleBrowseInfo> {

  @Resource private GoogleBrowseInfoRepository googleBrowseInfoRepository;

  public GoogleBrowseInfo save(GoogleBrowseInfo t) {
    return googleBrowseInfoRepository.save(t);
  }

  @Override
  public GoogleBrowseInfo find(GoogleBrowseInfo t) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GoogleBrowseInfo findById(Integer id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int update(GoogleBrowseInfo t) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int updateByList(List<GoogleBrowseInfo> list) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int remove(GoogleBrowseInfo t) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    // TODO Auto-generated method stub
    return 0;
  }

  public Optional<GoogleBrowseInfo> findTopByOrderByCreateTimeDesc() {
    return googleBrowseInfoRepository.findTopByOrderByCreateTimeDesc();
  }

  public List<PageRankingView> queryPageRanking(Integer limit) {
    List result = new ArrayList();
    for (Object o : googleBrowseInfoRepository.queryPageRanking(limit)) {
      Object[] objects = (Object[]) o;
      result.add(
          new PageRankingView(
              String.valueOf(objects[0]),
              BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[1]))),
              BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[2]))),
              BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[3])))));
    }
    return result;
  }

  public List<PageRankingView> queryPageRanking(String startTime, String endTime, Integer limit) {
    List result = new ArrayList();
    for (Object o : googleBrowseInfoRepository.queryPageRanking(startTime, endTime, limit)) {
      Object[] objects = (Object[]) o;
      result.add(
          new PageRankingView(
              String.valueOf(objects[0]),
              BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[1]))),
              BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[2]))),
              BigDecimal.valueOf(0)));
    }
    return result;
  }

  public List<GoogleBrowseInfo> queryByStartTimeAndEndTime(String startTime, String endTime) {
    return googleBrowseInfoRepository.queryByStartTimeAndEndTime(startTime, endTime);
  }

  public Integer[] getPdtVisitCountAndPageViews(Integer pkey, String startTime, String endTime) {
    List<Object> pdtVisitCountAndPageViews =
        googleBrowseInfoRepository.getPdtVisitCountAndPageViews(pkey, startTime, endTime);
    if (pdtVisitCountAndPageViews.size() > 0) {
      for (Object item : pdtVisitCountAndPageViews) {
        return (Integer[]) item;
      }
    }
    Integer[] i = {0, 0};
    return i;
  }

  public Integer[] getSupVisitCountAndPageViews(Integer pkey, String startTime, String endTime) {
    List<Object> supVisitCountAndPageViews =
        googleBrowseInfoRepository.getSupVisitCountAndPageViews(pkey, startTime, endTime);
    if (supVisitCountAndPageViews.size() > 0) {
      for (Object item : supVisitCountAndPageViews) {
        return (Integer[]) item;
      }
    }
    Integer[] i = {0, 0};
    return i;
  }

  public List<Object> getPageViewsAndSessionsGrupByCountrty(
      int param, String startTime, String endTime) {
    List<Object> obj =
        googleBrowseInfoRepository.getPageViewsAndSessionsGrupByCountrty(param, startTime, endTime);
    return obj;
  }
}

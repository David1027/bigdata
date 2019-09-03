package com.shoestp.mains.dao.transform;

import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QSearchWordInfo;
import com.shoestp.mains.entitys.metadata.SearchWordInfo;
import com.shoestp.mains.views.dataview.real.HomeSearchView;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @description: 搜索关键词源数据 - 数据访问层
 * @author: lingjian
 * @create: 2019/8/21 9:59
 */
@Repository
public class SearchWordInfoDao extends BaseDao<SearchWordInfo> {

  /**
   * 根据时间分组获取搜索表关键词字段列表记录
   *
   * @author: lingjian @Date: 2019/9/2 9:50
   * @param start 开始时间
   * @param end 结束时间
   * @return List<String>
   */
  public List<HomeSearchView> listSearchWord(Date start, Date end, Integer page, Integer limit) {
    QSearchWordInfo qSearchWordInfo = QSearchWordInfo.searchWordInfo;
    return getQuery()
        .select(
            Projections.bean(
                HomeSearchView.class,
                qSearchWordInfo.keyword.as("keyword"),
                qSearchWordInfo.id.count().as("viewCount")))
        .where(qSearchWordInfo.createTime.between(start, end))
        .groupBy(qSearchWordInfo.keyword)
        .from(qSearchWordInfo)
        .orderBy(qSearchWordInfo.id.count().desc())
        .offset(page)
        .limit(limit)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间分组获取搜索表关键词字段的总条目数
   *
   * @author: lingjian @Date: 2019/9/2 9:50
   * @param start 开始时间
   * @param end 结束时间
   * @return Long
   */
  public Long countSearchWord(Date start, Date end) {
    QSearchWordInfo qSearchWordInfo = QSearchWordInfo.searchWordInfo;
    return getQuery()
        .select(qSearchWordInfo)
        .where(qSearchWordInfo.createTime.between(start, end))
        .groupBy(qSearchWordInfo.keyword)
        .from(qSearchWordInfo)
        .fetchCount();
  }
}

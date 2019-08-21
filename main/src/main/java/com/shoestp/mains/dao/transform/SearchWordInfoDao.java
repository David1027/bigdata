package com.shoestp.mains.dao.transform;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QSearchWordInfo;
import com.shoestp.mains.entitys.metadata.SearchWordInfo;

import org.springframework.stereotype.Repository;

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
   * @param start 开始时间
   * @param end 结束时间
   * @return List<String>
   */
  public List<String> listSearchWord(Date start, Date end) {
    QSearchWordInfo qSearchWordInfo = QSearchWordInfo.searchWordInfo;
    return getQuery()
        .select(qSearchWordInfo.keyword)
        .where(qSearchWordInfo.createTime.between(start, end))
        .groupBy(qSearchWordInfo.keyword)
        .from(qSearchWordInfo)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据搜索关键词，时间获取搜索表对应记录的数量
   *
   * @author: lingjian @Date: 2019/8/21 10:34
   * @param keyword 搜索关键词
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countSearchWordView(String keyword, Date start, Date end) {
    QSearchWordInfo qSearchWordInfo = QSearchWordInfo.searchWordInfo;
    return (int)
        getQuery()
            .select(qSearchWordInfo)
            .where(qSearchWordInfo.createTime.between(start, end))
            .where(qSearchWordInfo.keyword.eq(keyword))
            .from(qSearchWordInfo)
            .fetchCount();
  }
}

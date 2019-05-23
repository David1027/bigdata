package com.shoestp.mains.dao.DataView.inquiry;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiryRank;
import com.shoestp.mains.entitys.DataView.inquiry.QDataViewInquiryRank;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.repositorys.DataView.inquory.InquiryRankRepository;

/**
 * @description: 询盘-数据层
 * @author: lingjian @Date: 2019/5/9 10:19
 */
@Repository
public class InquiryRankDao extends BaseDao<DataViewInquiryRank> {

  @Resource private InquiryRankRepository inquiryRankRepository;

  /**
   * 根据时间，询盘类型获取数据（降序排序，取前50条，在时间之前的总和）
   *
   * @param inquiryType
   * @param date
   * @return
   */
  public List<DataViewInquiryRank> findAllByInquiryType(
      InquiryTypeEnum inquiryType, Date date, Integer page, Integer pageSize) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    JPAQuery<DataViewInquiryRank> quiry =
        getQuery()
            .select(qDataViewInquiryRank)
            .from(qDataViewInquiryRank)
            .where(qDataViewInquiryRank.createTime.before(date))
            .where(qDataViewInquiryRank.inquiryType.eq(inquiryType))
            .orderBy(qDataViewInquiryRank.inquiryCount.desc());
    if (page != null) {
      quiry.offset(page).limit(pageSize);
    }
    return quiry.fetchResults().getResults();
  }

  /**
   * 根据时间，询盘类型获取数据（降序排序，取前50条）
   *
   * @author: lingjian @Date: 2019/5/16 14:16
   * @param inquiryType
   * @param startDate
   * @param endDate
   * @return
   */
  public List<DataViewInquiryRank> findAllByInquiryTypeBetween(
      InquiryTypeEnum inquiryType, Date startDate, Date endDate, Integer page, Integer pageSize) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    JPAQuery<DataViewInquiryRank> quiry =
        getQuery()
            .select(qDataViewInquiryRank)
            .from(qDataViewInquiryRank)
            .where(qDataViewInquiryRank.createTime.between(startDate, endDate))
            .where(qDataViewInquiryRank.inquiryType.eq(inquiryType))
            .orderBy(qDataViewInquiryRank.inquiryCount.desc());
    if (page != null) {
      quiry.offset(page).limit(pageSize);
    }
    return quiry.fetchResults().getResults();
  }

  /**
   * 根据搜索名称，实时类型，时间获取搜索结果数据记录
   *
   * @author: lingjian @Date: 2019/5/20 9:36
   * @param inquiryName
   * @param type
   * @param startDate
   * @param endDate
   * @return
   */
  public List<DataViewInquiryRank> findInquiryByInquiryName(
      String inquiryName, String type, Date startDate, Date endDate, int page, int pageSize) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    JPAQuery<DataViewInquiryRank> quiry =
        getQuery()
            .select(qDataViewInquiryRank)
            .from(qDataViewInquiryRank)
            .where(qDataViewInquiryRank.inquiryName.like("%" + inquiryName + "%"));
    if ("real".equals(type)) {
      quiry.where(qDataViewInquiryRank.createTime.between(startDate, endDate));
    } else {
      quiry.where(qDataViewInquiryRank.createTime.before(startDate));
    }
    return quiry
        .orderBy(qDataViewInquiryRank.inquiryCount.desc())
        .offset(page)
        .limit(pageSize)
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间，询盘类型，询盘名称获取数据
   *
   * @param inquiryType
   * @param inquiryName
   * @param startDate
   * @param endDate
   * @return
   */
  public List<DataViewInquiryRank> findAllByInquiryTypeAndInquiryNameBetween(
      InquiryTypeEnum inquiryType, String inquiryName, Date startDate, Date endDate) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    return getQuery()
        .select(qDataViewInquiryRank)
        .from(qDataViewInquiryRank)
        .where(qDataViewInquiryRank.inquiryType.eq(inquiryType))
        .where(qDataViewInquiryRank.inquiryName.eq(inquiryName))
        .where(qDataViewInquiryRank.createTime.between(startDate, endDate))
        .orderBy(qDataViewInquiryRank.inquiryCount.desc())
        .limit(50)
        .fetchResults()
        .getResults();
  }

  @Override
  public DataViewInquiryRank find(DataViewInquiryRank dataViewInquiryRank) {
    return null;
  }

  @Override
  public DataViewInquiryRank findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewInquiryRank dataViewInquiryRank) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewInquiryRank> list) {
    return 0;
  }

  @Override
  public int remove(DataViewInquiryRank dataViewInquiryRank) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }

  public void save(DataViewInquiryRank rank) {
    inquiryRankRepository.save(rank);
  }

  public List<Object> getRanking(String type, Date startTime, Date endTime, int start, int limit) {
    return inquiryRankRepository.getRanking(type, startTime, endTime, start, limit);
  }
}

package com.shoestp.mains.dao.dataview.inquiry;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiryRank;
import com.shoestp.mains.entitys.dataview.inquiry.QDataViewInquiryRank;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.repositorys.dataview.inquory.InquiryRankRepository;
import com.shoestp.mains.views.dataview.inquiry.InquiryTypeView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description: 询盘-数据层
 * @author: lingjian @Date: 2019/5/9 10:19
 */
@Repository
public class InquiryRankDao extends BaseDao<DataViewInquiryRank> {

  @Resource private InquiryRankRepository inquiryRankRepository;

  /**
   * 新增询盘排行表
   *
   * @author: lingjian @Date: 2019/8/8 15:08
   * @param dataViewInquiryRank 询盘排行对象
   */
  public void saveInquiryRank(DataViewInquiryRank dataViewInquiryRank) {
    inquiryRankRepository.save(dataViewInquiryRank);
  }

  /**
   * 关联InquiryTypeView询盘排行前端展示类
   *
   * @param qDataViewInquiryRank q询盘排行表
   * @return QBean<InquiryTypeView>
   */
  private QBean<InquiryTypeView> getInquiryTypeView(QDataViewInquiryRank qDataViewInquiryRank) {
    return Projections.bean(
        InquiryTypeView.class,
        qDataViewInquiryRank.inquiryType.as("inquiryType"),
        qDataViewInquiryRank.inquiryName.as("inquiryName"),
        qDataViewInquiryRank.visitorCount.sum().as("visitorCount"),
        qDataViewInquiryRank.viewCount.sum().as("viewCount"),
        qDataViewInquiryRank.inquiryCount.sum().as("inquiryCount"),
        qDataViewInquiryRank.inquiryNumber.sum().as("inquiryNumber"),
        qDataViewInquiryRank.inquiryAmount.sum().as("inquiryAmount"));
  }

  /**
   * 根据时间，询盘类型获取数据（降序排序，取前50条，在时间之前的总和）
   *
   * @param inquiryType
   * @param date
   * @return
   */
  public List<InquiryTypeView> findAllByInquiryType(
      InquiryTypeEnum inquiryType, Date date, Integer page, Integer pageSize) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    JPAQuery<InquiryTypeView> quiry =
        getQuery()
            .select(getInquiryTypeView(qDataViewInquiryRank))
            .from(qDataViewInquiryRank)
            .where(qDataViewInquiryRank.createTime.before(date))
            .where(qDataViewInquiryRank.inquiryType.eq(inquiryType))
            .groupBy(qDataViewInquiryRank.inquiryName)
            .orderBy(qDataViewInquiryRank.inquiryCount.sum().desc());
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
  public List<InquiryTypeView> findAllByInquiryTypeBetween(
      InquiryTypeEnum inquiryType, Date startDate, Date endDate, Integer page, Integer pageSize) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    JPAQuery<InquiryTypeView> quiry =
        getQuery()
            .select(getInquiryTypeView(qDataViewInquiryRank))
            .from(qDataViewInquiryRank)
            .where(qDataViewInquiryRank.createTime.between(startDate, endDate))
            .where(qDataViewInquiryRank.inquiryType.eq(inquiryType))
            .groupBy(qDataViewInquiryRank.inquiryName)
            .orderBy(qDataViewInquiryRank.inquiryCount.sum().desc());
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
  public List<InquiryTypeView> findInquiryByInquiryName(
      String inquiryName, String type, Date startDate, Date endDate, int page, int pageSize) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    JPAQuery<InquiryTypeView> quiry =
        getQuery()
            .select(getInquiryTypeView(qDataViewInquiryRank))
            .from(qDataViewInquiryRank)
            .groupBy(qDataViewInquiryRank.inquiryName)
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
  public List<InquiryTypeView> findAllByInquiryTypeAndInquiryNameBetween(
      InquiryTypeEnum inquiryType, String inquiryName, Date startDate, Date endDate) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    return getQuery()
        .select(getInquiryTypeView(qDataViewInquiryRank))
        .from(qDataViewInquiryRank)
        .where(qDataViewInquiryRank.inquiryType.eq(inquiryType))
        .where(qDataViewInquiryRank.inquiryName.eq(inquiryName))
        .where(qDataViewInquiryRank.createTime.between(startDate, endDate))
        .orderBy(qDataViewInquiryRank.inquiryCount.desc())
        .limit(50)
        .fetchResults()
        .getResults();
  }

  public void save(DataViewInquiryRank rank) {
    inquiryRankRepository.save(rank);
  }

  public List<Object> getRanking(String type, Date startTime, Date endTime, int start, int limit) {
    return inquiryRankRepository.getRanking(type, startTime, endTime, start, limit);
  }
}

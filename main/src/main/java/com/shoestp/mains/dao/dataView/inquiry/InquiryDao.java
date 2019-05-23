package com.shoestp.mains.dao.dataView.inquiry;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataView.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.dataView.inquiry.QDataViewInquiry;
import com.shoestp.mains.repositorys.dataView.inquory.InquiryRepository;
import com.shoestp.mains.views.dataView.inquiry.InquiryView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description: 询盘-数据层
 * @author: lingjian @Date: 2019/5/9 10:19
 */
@Repository
public class InquiryDao extends BaseDao<DataViewInquiry> {

  @Resource private InquiryRepository inquiryRepository;

  /**
   * 根据时间获取访客数，询盘数，询盘人数,返回集合
   *
   * @author: lingjian @Date: 2019/5/14 10:10
   * @param start
   * @param end
   * @return
   */
  public List<Tuple> findAllByCreateTime(Date start, Date end) {
    QDataViewInquiry qDataViewInquiry = QDataViewInquiry.dataViewInquiry;
    return getQuery()
        .select(
            qDataViewInquiry.visitorCount.sum(),
            qDataViewInquiry.inquiryNumber.sum(),
            qDataViewInquiry.inquiryCount.sum())
        .from(qDataViewInquiry)
        .where(qDataViewInquiry.createTime.between(start, end))
        .fetchResults()
        .getResults();
  }

  /**
   * 根据时间获取访客数，询盘数，询盘人数,返回对象
   *
   * @author: lingjian @Date: 2019/5/17 9:55
   * @param start
   * @param end
   * @return
   */
  public InquiryView findAllByCreateTimeObject(Date start, Date end) {
    QDataViewInquiry qDataViewInquiry = QDataViewInquiry.dataViewInquiry;
    return getQuery()
        .select(
            Projections.bean(
                InquiryView.class,
                qDataViewInquiry.visitorCount.sum().as("visitorCount"),
                qDataViewInquiry.inquiryNumber.sum().as("inquiryNumber"),
                qDataViewInquiry.inquiryCount.sum().as("inquiryCount")))
        .from(qDataViewInquiry)
        .where(qDataViewInquiry.createTime.between(start, end))
        .fetchFirst();
  }

  /**
   * 获取所有的总询盘数
   *
   * @author: lingjian @Date: 2019/5/14 10:10
   * @return
   */
  public List<Integer> findAllByInquiry() {
    QDataViewInquiry qDataViewInquiry = QDataViewInquiry.dataViewInquiry;
    return getQuery()
        .select(qDataViewInquiry.inquiryCount.sum().as("totalInquiryCount"))
        .from(qDataViewInquiry)
        .fetchResults()
        .getResults();
  }

  @Override
  public DataViewInquiry find(DataViewInquiry dataViewInquiry) {
    return null;
  }

  @Override
  public DataViewInquiry findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewInquiry dataViewInquiry) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewInquiry> list) {
    return 0;
  }

  @Override
  public int remove(DataViewInquiry dataViewInquiry) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }

  public void save(DataViewInquiry in) {
    inquiryRepository.save(in);
  }
}

package com.shoestp.mains.dao.DataView.inquiry;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.DataView.inquiry.QDataViewInquiry;
import com.shoestp.mains.repositorys.DataView.inquory.InquiryRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 询盘-数据层
 * @author: lingjian @Date: 2019/5/9 10:19
 */
@Repository
public class InquiryDao extends BaseDao<DataViewInquiry> {

  @Resource private InquiryRepository inquiryRepository;

  /**
   * 根据时间获取访客数，询盘数，询盘人数
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
   * 获取所有的总询盘数
   *
   * @author: lingjian @Date: 2019/5/14 10:10
   * @return
   */
  public Integer findAllByInquiry() {
    QDataViewInquiry qDataViewInquiry = QDataViewInquiry.dataViewInquiry;
    return getQuery()
        .select(qDataViewInquiry.inquiryCount.sum().as("totalInquiryCount"))
        .from(qDataViewInquiry)
        .fetchResults()
        .getResults()
        .get(0);
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
}

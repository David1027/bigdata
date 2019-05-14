package com.shoestp.mains.dao.DataView.inquiry;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.DataView.inquiry.DataViewInquiryRank;
import com.shoestp.mains.entitys.DataView.inquiry.QDataViewInquiry;
import com.shoestp.mains.entitys.DataView.inquiry.QDataViewInquiryRank;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.repositorys.DataView.inquory.InquiryRankRepository;
import com.shoestp.mains.repositorys.DataView.inquory.InquiryRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 询盘-数据层
 * @author: lingjian @Date: 2019/5/9 10:19
 */
@Repository
public class InquiryRankDao extends BaseDao<DataViewInquiryRank> {

  @Resource private InquiryRankRepository inquiryRankRepository;

  /**
   * 根据询盘类型获取数据（降序排序，取前50条）
   * @param inquiryType
   * @return
   */
  public List<DataViewInquiryRank> findAllByInquiryType(InquiryTypeEnum inquiryType) {
    QDataViewInquiryRank qDataViewInquiryRank = QDataViewInquiryRank.dataViewInquiryRank;
    return getQuery()
        .select(qDataViewInquiryRank)
        .from(qDataViewInquiryRank)
        .where(qDataViewInquiryRank.inquiryType.eq(inquiryType))
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
}

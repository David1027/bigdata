package com.shoestp.mains.dao.transform;

import java.util.Date;
import java.util.List;

import com.querydsl.jpa.impl.JPAQuery;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.entitys.metadata.QInquiryInfo;
import com.shoestp.mains.entitys.metadata.UserInfo;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;

import org.springframework.stereotype.Repository;

/**
 * @description: 用户询盘 - 数据访问层
 * @author: lingjian @Date: 2019/8/7 9:59
 */
@Repository
public class NewInquiryInfoDao extends BaseDao<UserInfo> {

  /**
   * 根据搜索条件执行对应语句
   *
   * @param inquiryTypeEnum 询盘类型
   * @param inquiryName 询盘名称
   * @param qInquiryInfo 询盘info表对象
   * @param query 查找语句
   */
  private void searchInquery(
      InquiryTypeEnum inquiryTypeEnum,
      String inquiryName,
      QInquiryInfo qInquiryInfo,
      JPAQuery<InquiryInfo> query) {
    if (inquiryTypeEnum == null && inquiryName == null) {
      query.where(qInquiryInfo.type.ne(InquiryTypeEnum.RFQ));
    }
    if (inquiryTypeEnum != null) {
      query.where(qInquiryInfo.type.eq(inquiryTypeEnum));
    }
    if (inquiryName != null) {
      query.where(qInquiryInfo.name.eq(inquiryName));
    }
  }

  /**
   * 根据时间获取询盘量
   *
   * @author: lingjian @Date: 2019/8/8 14:35
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countInquiry(
      InquiryTypeEnum inquiryTypeEnum, String inquiryName, Date start, Date end) {
    QInquiryInfo qInquiryInfo = QInquiryInfo.inquiryInfo;
    JPAQuery<InquiryInfo> query =
        getQuery()
            .select(qInquiryInfo)
            .where(qInquiryInfo.createTime.between(start, end))
            .from(qInquiryInfo);
    searchInquery(inquiryTypeEnum, inquiryName, qInquiryInfo, query);
    return (int) query.fetchCount();
  }

  /**
   * 根据时间获取询盘人数
   *
   * @author: lingjian @Date: 2019/8/8 14:35
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countInquiryNumber(
      InquiryTypeEnum inquiryTypeEnum, String inquiryName, Date start, Date end) {
    QInquiryInfo qInquiryInfo = QInquiryInfo.inquiryInfo;
    JPAQuery<InquiryInfo> query =
        getQuery()
            .select(qInquiryInfo)
            .where(qInquiryInfo.createTime.between(start, end))
            .groupBy(qInquiryInfo.ip)
            .from(qInquiryInfo);
    searchInquery(inquiryTypeEnum, inquiryName, qInquiryInfo, query);
    return (int) query.fetchCount();
  }

  /**
   * 根据时间获取RFQ数
   *
   * @author: lingjian @Date: 2019/8/8 14:35
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countRFQ(Date start, Date end) {
    QInquiryInfo qInquiryInfo = QInquiryInfo.inquiryInfo;
    return (int)
        getQuery()
            .select(qInquiryInfo)
            .where(qInquiryInfo.type.eq(InquiryTypeEnum.RFQ))
            .where(qInquiryInfo.createTime.between(start, end))
            .from(qInquiryInfo)
            .fetchCount();
  }

  /**
   * 根据询盘类型，时间获取询盘记录
   *
   * @author: lingjian @Date: 2019/8/8 14:50
   * @param inquiryTypeEnum 询盘类型
   * @param start 开始时间
   * @param end 结束时间
   * @return List<InquiryInfo> 询盘表集合对象
   */
  public List<InquiryInfo> getInquiryName(InquiryTypeEnum inquiryTypeEnum, Date start, Date end) {
    QInquiryInfo qInquiryInfo = QInquiryInfo.inquiryInfo;
    return getQuery()
        .select(qInquiryInfo)
        .where(qInquiryInfo.type.eq(inquiryTypeEnum))
        .where(qInquiryInfo.createTime.between(start, end))
        .groupBy(qInquiryInfo.name)
        .from(qInquiryInfo)
        .fetchResults()
        .getResults();
  }

  @Override
  public UserInfo find(UserInfo userInfo) {
    return null;
  }

  @Override
  public UserInfo findById(Integer id) {
    return null;
  }

  @Override
  public int update(UserInfo userInfo) {
    return 0;
  }

  @Override
  public int updateByList(List<UserInfo> list) {
    return 0;
  }

  @Override
  public int remove(UserInfo userInfo) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

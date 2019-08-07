package com.shoestp.mains.dao.transform;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.metadata.QInquiryInfo;
import com.shoestp.mains.entitys.metadata.QUserInfo;
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
   * 根据时间获取询盘量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer
   */
  public Integer countInquiry(Date start, Date end) {
    QInquiryInfo qInquiryInfo = QInquiryInfo.inquiryInfo;
    return (int)
        getQuery()
            .select(qInquiryInfo)
            .where(qInquiryInfo.type.ne(InquiryTypeEnum.RFQ))
            .where(qInquiryInfo.createTime.between(start, end))
            .from(qInquiryInfo)
            .fetchCount();
  }

  /**
   * 根据时间获取RFQ数
   *
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

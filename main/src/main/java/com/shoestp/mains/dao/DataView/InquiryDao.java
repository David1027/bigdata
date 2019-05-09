package com.shoestp.mains.dao.DataView;

import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.DataViewInquiry;
import com.shoestp.mains.repositorys.DataView.InquiryRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 询盘-数据层
 * @author: lingjian @Date: 2019/5/9 10:19
 */
@Repository
public class InquiryDao extends BaseDao<DataViewInquiry> {

  @Resource private InquiryRepository inquiryRepository;

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

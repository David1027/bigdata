package com.shoestp.mains.service.dataview.inquiry;

import com.shoestp.mains.controllers.dataview.inquiry.pojo.InquiryType;
import com.shoestp.mains.controllers.dataview.inquiry.views.InquiryView;
import com.shoestp.mains.entitys.dataview.inquirynew.DataViewInquiryNew;
import com.shoestp.mains.views.Page;

import java.util.Optional;

/**
 * The interface Inquiry service.
 *
 * @author lijie
 * @date 2019 /09/12
 * @since
 */
public interface InquiryNewService {

  /**
   * Gets inquiry date view by key word. 根据关键词及类型获取数据暂时层的 List
   *
   * @author lijie
   * @date 2019 /09/12
   * @since *
   * @param keyword the keyword
   * @param type the type
   * @return the inquiry date view by key word
   */
  Page<DataViewInquiryNew> getInquiryDateViewByKeyWordAndType(
      String keyword, Integer start, Integer limit, InquiryType type);

  /**
   * Schedulers 统计计划任务
   *
   * @author lijie
   * @date 2019 /09/12
   * @since .
   */
  void schedulers();

}

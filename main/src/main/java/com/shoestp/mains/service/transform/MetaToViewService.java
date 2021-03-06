package com.shoestp.mains.service.transform;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiryRank;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.entitys.dataview.user.DataViewUser;
import com.shoestp.mains.entitys.dataview.user.DataViewUserArea;

/**
 * @description: 源数据转化展示数据 - 服务层接口
 * @author: lingjian
 * @create: 2019/8/6 13:55
 */
public interface MetaToViewService {

  /**
   * 源数据转化real数据总表
   *
   * @author: lingjian @Date: 2019/8/8 13:43
   * @param start 开始时间
   * @param end 结束时间
   * @return DataViewReal 数据对象
   */
  DataViewReal toReal(Date start, Date end);

  /**
   * 源数据转化flow流量表
   *
   * @author: lingjian @Date: 2019/8/8 13:43
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewFlow> flow流量表集合对象
   */
  List<DataViewFlow> toFlow(Date start, Date end);

  /**
   * 源数据转化flowpage页面分析表
   *
   * @author: lingjian @Date: 2019/8/8 13:42
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewFlowPage> flowpage页面分析集合对象
   */
  List<DataViewFlowPage> toFlowPage(Date start, Date end);

  /**
   * 源数据转化inquiry询盘表
   *
   * @author: lingjian @Date: 2019/8/8 14:18
   * @param start 开始时间
   * @param end 结束时间
   * @return DataViewInquiry询盘对象
   */
  DataViewInquiry toInquiry(Date start, Date end);

  /**
   * 源数据转化inquiryrank询盘排行表
   *
   * @author: lingjian @Date: 2019/8/8 15:14
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewInquiryRank> 询盘排行集合对象
   */
  List<DataViewInquiryRank> toInquiryRank(Date start, Date end);

  /**
   * 源数据转化user用户表
   *
   * @author: lingjian @Date: 2019/8/12 14:20
   * @param start 开始时间
   * @param end 结束时间
   * @return DataViewUser 用户表对象
   */
  DataViewUser toUser(Date start, Date end);

  /**
   * 源数据转化userarea用户地域表
   *
   * @author: lingjian @Date: 2019/8/13 9:41
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewUserArea> 用户地域表集合对象
   */
  List<DataViewUserArea> toUserArea(Date start, Date end);

  /**
   * 源数据转化country国家表
   *
   * @author: lingjian @Date: 2019/8/19 10:02
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewCountry> 国家表集合对象
   */
  List<DataViewCountry> toCountry(Date start, Date end);
}

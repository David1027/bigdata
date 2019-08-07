package com.shoestp.mains.service.transform;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;

/**
 * @description: 源数据转化展示数据 - 服务层接口
 * @author: lingjian
 * @create: 2019/8/6 13:55
 */
public interface MetaToViewService {

  /**
   * 源数据转化real数据总表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return DataViewReal 数据对象
   */
  DataViewReal toReal(Date start, Date end);

  /**
   * 源数据转化flow流量表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewFlow> flow流量表集合对象
   */
  List<DataViewFlow> toFlow(Date start, Date end);

  List<DataViewFlowPage> toFlowPage(Date start, Date end);
}

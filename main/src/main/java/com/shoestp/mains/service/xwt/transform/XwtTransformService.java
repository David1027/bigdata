package com.shoestp.mains.service.xwt.transform;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.xwt.dataview.plat.country.XwtViewCountry;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlow;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlowPage;
import com.shoestp.mains.entitys.xwt.dataview.plat.real.XwtViewReal;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUser;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUserArea;

/**
 * @description: 源数据转化展示数据服务层接口
 * @author: lingjian
 * @create: 2019/12/31 15:35
 */
public interface XwtTransformService {

  /**
   * 源数据转化real表数据 - 实时表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewReal 实时表对象
   */
  XwtViewReal toReal(Date start, Date end);

  /**
   * 源数据转化flow表数据 - 流量表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlow> flow流量表集合对象
   */
  List<XwtViewFlow> toFlow(Date start, Date end);

  /**
   * 源数据转化flowpage表数据 - 页面分析表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlowPage> flowpage页面分析表集合对象
   */
  List<XwtViewFlowPage> toFlowPage(Date start, Date end);

  /**
   * 源数据转化user表数据 - 用户表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewUser user用户表对象
   */
  XwtViewUser toUser(Date start, Date end);

  /**
   * 源数据转化userarea表数据 - 用户地域表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewUserArea> userarea用户地域表集合对象
   */
  List<XwtViewUserArea> toUserArea(Date start, Date end);

  /**
   * 源数据转化country表数据 - 国家表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewCountry> country国家表集合对象
   */
  List<XwtViewCountry> toCountry(Date start, Date end);
}

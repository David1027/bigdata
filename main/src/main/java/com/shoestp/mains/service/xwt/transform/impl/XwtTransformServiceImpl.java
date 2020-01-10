package com.shoestp.mains.service.xwt.transform.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shoestp.mains.dao.xwt.dataview.plat.dao.*;
import com.shoestp.mains.dao.xwt.metadata.dao.XwtMetaAccessLogDAO;
import com.shoestp.mains.dao.xwt.metadata.dao.XwtMetaMemberInfoDAO;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.entitys.xwt.dataview.plat.country.XwtViewCountry;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlow;
import com.shoestp.mains.entitys.xwt.dataview.plat.flow.XwtViewFlowPage;
import com.shoestp.mains.entitys.xwt.dataview.plat.real.XwtViewReal;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUser;
import com.shoestp.mains.entitys.xwt.dataview.plat.user.XwtViewUserArea;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.enums.xwt.OMemberRoleEnum;
import com.shoestp.mains.service.xwt.transform.XwtTransformService;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.views.transform.DeviceVisitorVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 源数据转化展示数据服务层接口实现类
 * @author: lingjian
 * @create: 2019/12/31 15:35
 */
@Service
@SuppressWarnings("ALL")
public class XwtTransformServiceImpl implements XwtTransformService {

  @Autowired private XwtMetaAccessLogDAO accessLogDAO;
  @Autowired private XwtMetaMemberInfoDAO memberInfoDAO;
  @Autowired private XwtViewRealDAO realDAO;
  @Autowired private XwtViewFlowDAO flowDAO;
  @Autowired private XwtViewFlowPageDAO flowPageDAO;
  @Autowired private XwtViewUserDAO userDAO;
  @Autowired private XwtViewUserAreaDAO userAreaDAO;
  @Autowired private XwtViewCountryDAO countryDAO;

  /**
   * 判断XwtViewReal实时表的数据是否为空
   *
   * @param real 实时表对象
   * @return boolean 布尔
   */
  private boolean decideIsNull(XwtViewReal real) {
    return real.getPageViewsCount() != 0
        || real.getVisitorCountPc() != 0
        || real.getVisitorCountWap() != 0
        || real.getVisitorCount() != 0
        || real.getRegisterCount() != 0;
  }

  /**
   * 判断XwtViewFlowPage页面分析表的数据是否为空
   *
   * @param flowPage 页面分析表对象
   * @return boolean 布尔
   */
  private boolean decideIsNull(XwtViewFlowPage flowPage) {
    return flowPage.getViewCount() != 0
        || flowPage.getVisitorCount() != 0
        || flowPage.getClickRate() != 0
        || flowPage.getJumpRate() != 0
        || flowPage.getAverageStayTime() != 0;
  }

  /**
   * 判断XwtViewUser用户表的数据是否为空
   *
   * @param user 用户表
   * @return boolean 布尔
   */
  private boolean decideIsNull(XwtViewUser user) {
    return user.getVisitorCount() != 0
        || user.getNewVisitorCount() != 0
        || user.getOldVisitorCount() != 0
        || user.getRegisterCount() != 0
        || user.getGeneralCount() != 0
        || user.getShoesCount() != 0
        || user.getMaterialCount() != 0
        || user.getDesignerCount() != 0;
  }

  /**
   * 根据开始时间和结束时间获取设备和访客集合对象
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DeviceVisitorVO> 设备和访客VO集合对象
   */
  private List<DeviceVisitorVO> listDeviceVisitor(Date start, Date end) {
    return accessLogDAO.listByCreateTimeGroupByMemberId(start, end);
  }

  /**
   * 根据开始时间和结束时间获取用户信息表id集合对象
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<Integer> 用户信息表id集合对象
   */
  private List<Integer> listMemberId(Date start, Date end) {
    return accessLogDAO.listGroupByMemberId(start, end);
  }

  /**
   * 根据开始时间，结束时间，设备类型和来源类型获取日志信息集合对象
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param device 设备类型
   * @param source 来源类型
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  private List<XwtMetaAccessLog> listDeviceSource(
      Date start, Date end, DeviceTypeEnum device, SourceTypeEnum source) {
    return accessLogDAO.findAllByCreateTimeBetweenAndDeviceTypeAndSourceType(
        start, end, device, source);
  }

  /**
   * 根据时间和页面类型获取日志信息记录集合对象
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param access 页面类型
   * @return List<XwtMetaAccessLog> 日志信息集合对象
   */
  private List<XwtMetaAccessLog> listAccess(Date start, Date end, OAccessTypeEnum access) {
    return accessLogDAO.findAllByCreateTimeBetweenAndAccessType(start, end, access);
  }

  /**
   * 根据时间，页面类型获取会话次数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param access 页面类型
   * @return Long 会话次数
   */
  private Long getSessionCount(Date start, Date end, OAccessTypeEnum access) {
    return accessLogDAO.countByCreateTimeAndAccessTypeGroupBySsId(start, end, access);
  }

  /**
   * 根据时间，页面类型获取会话次数 - 会话中只有一次的会话次数
   *
   * @param start 开始时间
   * @param end 结束时间
   * @param access 页面类型
   * @return Long 会话次数
   */
  private Long getSessionCountOne(Date start, Date end, OAccessTypeEnum access) {
    return (long)
        accessLogDAO
            .countByCreateTimeAndAccessTypeGroupBySsIdHavingSsCount(start, end, access)
            .size();
  }

  /**
   * 源数据转化real表数据 - 实时表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewReal 实时表对象
   */
  @Override
  public XwtViewReal toReal(Date start, Date end) {
    // 创建对象
    XwtViewReal real = new XwtViewReal();

    // 访客数,PC端访客数,WAP端访客数
    long count = 0L;
    long countPc = 0L;
    long countWap = 0L;
    // 循环判断：
    for (DeviceVisitorVO v : listDeviceVisitor(start, end)) {
      // 如果查询的用户信息id不在当天0点到查询的开始时间的集合内
      if (!listMemberId(DateTimeUtil.getTimesOfDay(start), start).contains(v.getMemberId())) {
        count++;
        // 根据查询记录的设备来源判断PC端和WAP端的访客数量
        if (DeviceTypeEnum.PC.equals(v.getDeviceType())) {
          countPc++;
        } else {
          countWap++;
        }
      }
    }
    real.setVisitorCount(count);
    real.setVisitorCountPc(countPc);
    real.setVisitorCountWap(countWap);
    // 浏览量
    real.setPageViewsCount(countTime(start, end));
    // 注册量
    real.setRegisterCount(0L);
    // 创建时间
    real.setCreateTime(new Date());

    // 判断对象是否为空,保存对象
    if (decideIsNull(real)) {
      realDAO.save(real);
    }
    return real;
  }

  /**
   * 根据时间获取日志信息对象的数量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return Long 浏览量
   */
  private Long countTime(Date start, Date end) {
    return accessLogDAO.countByCreateTimeBetween(start, end);
  }

  /**
   * 源数据转化flow表数据 - 流量表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlow> flow流量表集合对象
   */
  @Override
  public List<XwtViewFlow> toFlow(Date start, Date end) {
    // 创建流量表集合对象
    List<XwtViewFlow> list = new ArrayList<>();
    // 双循环：设备类型，来源类型
    for (DeviceTypeEnum device : DeviceTypeEnum.values()) {
      for (SourceTypeEnum source : SourceTypeEnum.values()) {
        // 创建流量表对象
        XwtViewFlow flow = new XwtViewFlow();
        // 设备来源
        flow.setDeviceType(device);
        // 来源类型
        flow.setSourceType(source);
        // 获取日志信息集合对象
        List<XwtMetaAccessLog> accessLogs = listDeviceSource(start, end, device, source);
        // 访客数
        long count = 0L;
        // 循环判断
        List<Integer> members = new ArrayList<>();
        for (XwtMetaAccessLog access : accessLogs) {
          if (!listMemberId(DateTimeUtil.getTimesOfDay(start), start)
              .contains(access.getMemberInfoId())) {
            if (!members.contains(access.getMemberInfoId())) {
              members.add(access.getMemberInfoId());
              count++;
            }
          }
        }
        flow.setVisitorCount(count);
        // 浏览量
        flow.setViewCount((long) accessLogs.size());
        // 创建时间
        flow.setCreateTime(new Date());
        // 判断对象是否为空，保存对象
        if (flow.getVisitorCount() != 0) {
          // 保存对象
          flowDAO.save(flow);
          // 将对象添加到集合中
          list.add(flow);
        }
      }
    }

    return list;
  }

  /**
   * 源数据转化flowpage表数据 - 页面分析表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewFlowPage> flowpage页面分析表集合对象
   */
  @Override
  public List<XwtViewFlowPage> toFlowPage(Date start, Date end) {
    // 创建页面分析表集合对象
    List<XwtViewFlowPage> list = new ArrayList<>();
    // 循环页面类型
    for (OAccessTypeEnum access : OAccessTypeEnum.values()) {
      XwtViewFlowPage flowPage = new XwtViewFlowPage();
      // 页面类型
      flowPage.setAccessType(access);
      // 获取日志信息集合对象
      List<XwtMetaAccessLog> accessLogs = listAccess(start, end, access);
      // 访客数
      long count = 0L;
      List<Integer> members = new ArrayList<>();
      for (XwtMetaAccessLog log : accessLogs) {
        if (!listMemberId(DateTimeUtil.getTimesOfDay(start), start)
            .contains(log.getMemberInfoId())) {
          if (!members.contains(log.getMemberInfoId())) {
            members.add(log.getMemberInfoId());
            count++;
          }
        }
      }
      flowPage.setVisitorCount(count);
      // 浏览量
      long viewCount = accessLogs.size();
      flowPage.setViewCount(viewCount);
      // 点击率：该页面类型浏览量/所有页面类型浏览量
      Long click = countTime(start, end);
      flowPage.setClickRate(
          CustomDoubleSerialize.setDouble(click != 0 ? (viewCount * 1.0 / click) : 0.0));
      // 跳失率(只浏览一个页面就离开的访问次数 / 该页面的全部访问次数)
      // 只浏览一个页面就离开的访问次数
      Long sessionOne = getSessionCountOne(start, end, access);
      // 该页面的全部访问次数(会话次数)
      Long session = getSessionCount(start, end, access);
      flowPage.setJumpRate(
          CustomDoubleSerialize.setDouble(session != 0 ? (sessionOne * 1.0 / session) : 0.0));
      // 平均停留时长
      Double avg = accessLogDAO.getTimeOnPageAvg(start, end, access);
      flowPage.setAverageStayTime(CustomDoubleSerialize.setDouble(avg));
      // 创建时间
      flowPage.setCreateTime(new Date());
      if (decideIsNull(flowPage)) {
        flowPageDAO.save(flowPage);
        list.add(flowPage);
      }
    }
    return list;
  }

  /**
   * 源数据转化user表数据 - 用户表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return XwtViewUser user用户表对象
   */
  @Override
  public XwtViewUser toUser(Date start, Date end) {
    // 创建用户表对象
    XwtViewUser user = new XwtViewUser();
    // 访客数
    long count = 0L;
    // 循环判断：
    for (DeviceVisitorVO v : listDeviceVisitor(start, end)) {
      // 如果查询的用户信息id不在当天0点到查询的开始时间的集合内
      if (!listMemberId(DateTimeUtil.getTimesOfDay(start), start).contains(v.getMemberId())) {
        count++;
      }
    }
    user.setVisitorCount(count);
    // 新用户数
    Long newVisitorCount =
        accessLogDAO.countByCreateTimeAndUserRole(start, end, OMemberRoleEnum.VISITOR);
    user.setNewVisitorCount(newVisitorCount);
    // 老用户数
    Long oldVisitorCount =
        accessLogDAO.countByCreateTimeAndUserRole(start, end, OMemberRoleEnum.REGISTER);
    user.setOldVisitorCount(oldVisitorCount);
    // 总注册量
    user.setRegisterCount(0L);
    // 普通用户数量
    user.setGeneralCount(0L);
    // 鞋企数量
    user.setShoesCount(0L);
    // 辅料商数量
    user.setMaterialCount(0L);
    // 设计师数量
    user.setDesignerCount(0L);
    // 创建时间
    user.setCreateTime(new Date());
    if (decideIsNull(user)) {
      userDAO.save(user);
    }
    return user;
  }

  /**
   * 源数据转化userarea表数据 - 用户地域表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewUserArea> userarea用户地域表集合对象
   */
  @Override
  public List<XwtViewUserArea> toUserArea(Date start, Date end) {
    // 创建用户地域表集合对象
    List<XwtViewUserArea> list = new ArrayList<>();
    // 获取日志信息记录
    List<XwtMetaAccessLog> accessLogs = accessLogDAO.listByCreateTimeGroupByProvince(start, end);
    // 循环判断
    for (XwtMetaAccessLog access : accessLogs) {
      // 创建用户地域表对象
      XwtViewUserArea area = new XwtViewUserArea();
      // 地域名称
      area.setArea(access.getProvince().getName());
      // 地域访客人数
      long count = 0L;
      // 根据地域获取访客列表
      List<XwtMetaAccessLog> logs =
          accessLogDAO.listByCreateTimeAndProvince(start, end, access.getProvinceId());
      // 循环判断：
      for (XwtMetaAccessLog log : logs) {
        if (!listMemberId(DateTimeUtil.getTimesOfDay(start), start)
            .contains(log.getMemberInfoId())) {
          count++;
        }
      }
      area.setAreaCount(count);
      // 创建时间
      area.setCreateTime(new Date());
      if (area.getAreaCount() != 0) {
        userAreaDAO.save(area);
        list.add(area);
      }
    }

    return list;
  }

  /**
   * 源数据转化country表数据 - 国家表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<XwtViewCountry> country国家表集合对象
   */
  @Override
  public List<XwtViewCountry> toCountry(Date start, Date end) {
    // 创建国家表集合对象
    List<XwtViewCountry> list = new ArrayList<>();
    // 获取日志信息记录
    List<XwtMetaAccessLog> accessLogs = accessLogDAO.listByCreateTimeGroupByCountry(start, end);
    // 循环判断
    for (XwtMetaAccessLog access : accessLogs) {
      // 创建国家对象
      XwtViewCountry country = new XwtViewCountry();
      // 国家名称
      country.setCountryName(access.getXwtMetaCountry().getName());
      // 国家英文名称
      country.setCountryEnglishName(access.getXwtMetaCountry().getEnglishName());
      // 国旗图片
      country.setCountryImage(access.getXwtMetaCountry().getImg());
      // 访客数
      long count = 0L;
      // 根据国家获取访客列表
      List<XwtMetaAccessLog> logs =
          accessLogDAO.listByCreateTimeAndCountry(start, end, access.getCountryId());
      // 循环判断：
      for (XwtMetaAccessLog log : logs) {
        if (!listMemberId(DateTimeUtil.getTimesOfDay(start), start)
            .contains(log.getMemberInfoId())) {
          count++;
        }
      }
      country.setVisitorCount(count);
      // 创建时间
      country.setCreateTime(new Date());
      if (country.getVisitorCount() != 0) {
        countryDAO.save(country);
        list.add(country);
      }
    }
    return list;
  }
}

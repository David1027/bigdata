package com.shoestp.mains.service.transform.impl;

import com.shoestp.mains.dao.dataview.realcountry.RealCountryDao;
import com.shoestp.mains.dao.dataview.flow.FlowDao;
import com.shoestp.mains.dao.dataview.flow.FlowPageDao;
import com.shoestp.mains.dao.dataview.inquiry.InquiryDao;
import com.shoestp.mains.dao.dataview.inquiry.InquiryRankDao;
import com.shoestp.mains.dao.dataview.real.RealDao;
import com.shoestp.mains.dao.dataview.user.UserAreaDao;
import com.shoestp.mains.dao.dataview.user.UserDao;
import com.shoestp.mains.dao.transform.NewInquiryInfoDao;
import com.shoestp.mains.dao.transform.NewUserInfoDao;
import com.shoestp.mains.dao.transform.WebVisitDao;
import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiry;
import com.shoestp.mains.entitys.dataview.inquiry.DataViewInquiryRank;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.entitys.dataview.user.DataViewUser;
import com.shoestp.mains.entitys.dataview.user.DataViewUserArea;
import com.shoestp.mains.entitys.metadata.InquiryInfo;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.enums.flow.AccessTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
import com.shoestp.mains.service.transform.MetaToViewService;
import com.shoestp.mains.service.urlmatchdatautil.URLMatchDataUtilService;
import org.springframework.stereotype.Service;
import org.start2do.utils.DateTimeUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 源数据转化展示数据 - 服务层实现类
 * @author: lingjian
 * @create: 2019/8/6 13:57
 */
@Service
public class MetaToViewServiceImpl implements MetaToViewService {

  @Resource private WebVisitDao webVisitDao;
  @Resource private NewUserInfoDao newUserInfoDao;
  @Resource private NewInquiryInfoDao newInquiryInfoDao;
  @Resource private RealDao realDao;
  @Resource private FlowDao flowDao;
  @Resource private FlowPageDao flowPageDao;
  @Resource private InquiryDao inquiryDao;
  @Resource private InquiryRankDao inquiryRankDao;
  @Resource private URLMatchDataUtilService urlMatchDataUtilService;
  @Resource private UserDao userDao;
  @Resource private UserAreaDao userAreaDao;
  @Resource private RealCountryDao realCountryDao;

  /**
   * 判断DataViewReal的数据是否为空
   *
   * @author: lingjian @Date: 2019/8/8 13:35
   * @param real DataViewReal数据对象
   */
  private boolean decideIsNull(DataViewReal real) {
    boolean flag = true;
    if (real.getPageViewsCount() == 0
        && real.getVisitorCountPc() == 0
        && real.getVisitorCountWap() == 0
        && real.getVisitorCount() == 0
        && real.getRegisterCount() == 0
        && real.getInquiryCount() == 0
        && real.getRfqCount() == 0) {
      flag = false;
    }
    return flag;
  }

  /**
   * 判断DataViewFlowPage的数据是否为空
   *
   * @author: lingjian @Date: 2019/8/8 13:41
   * @param flowPage 页面分析表对象
   * @return boolean
   */
  private boolean decideIsNull(DataViewFlowPage flowPage) {
    boolean flag = true;
    if (flowPage.getViewCount() == 0
        && flowPage.getVisitorCount() == 0
        && flowPage.getClickCount() == 0
        && flowPage.getClickNumber() == 0
        && flowPage.getClickRate() == 0
        && flowPage.getJumpRate() == 0
        && flowPage.getAverageStayTime() == 0) {
      flag = false;
    }
    return flag;
  }

  /**
   * 判断DataViewInquiry的数据是否为空
   *
   * @author: lingjian @Date: 2019/8/8 14:14
   * @param inquiry 询盘表对象
   * @return boolean
   */
  private boolean decideIsNull(DataViewInquiry inquiry) {
    boolean flag = true;
    if (inquiry.getVisitorCount() == 0
        && inquiry.getInquiryCount() == 0
        && inquiry.getInquiryNumber() == 0) {
      flag = false;
    }
    return flag;
  }

  /**
   * 判断DataViewUser的数据是否为空
   *
   * @author: lingjian @Date: 2019/8/13 9:33
   * @param user 用户表
   * @return boolean
   */
  private boolean decideIsNull(DataViewUser user) {
    boolean flag = true;
    if (user.getVisitorCount() == 0
        && user.getNewVisitorCount() == 0
        && user.getOldVisitorCount() == 0
        && user.getRegisterCount() == 0
        && user.getPurchaseCount() == 0
        && user.getSupplierCount() == 0) {
      flag = false;
    }
    return flag;
  }

  /**
   * 判断DataViewUserArea的数据是否为空
   *
   * @author: lingjian @Date: 2019/8/13 9:35
   * @param userArea 用户地域表
   * @return boolean
   */
  private boolean decideIsNull(DataViewUserArea userArea) {
    boolean flag = true;
    if (userArea.getAreaCount() == 0 && userArea.getAreaCountTotal() == 0) {
      flag = false;
    }
    return flag;
  }

  /**
   * 获取访客数
   *
   * @author: lingjian @Date: 2019/8/8 13:58
   * @param start 开始时间
   * @param end 结束时间
   * @param o 设备来源
   * @return Integer 访客数
   */
  private Integer getVisitorCount(Date start, Date end, DeviceTypeEnum o) {
    return webVisitDao.countVisitor(o, start, end);
  }

  /**
   * 获取询盘量
   *
   * @author: lingjian @Date: 2019/8/8 14:05
   * @param inquiryTypeEnum 询盘类型
   * @param inquiryName 询盘名称
   * @param start 开始时间
   * @param end 结束时间
   * @return Integer 询盘数量
   */
  private Integer getInquiryCount(
      InquiryTypeEnum inquiryTypeEnum, String inquiryName, Date start, Date end) {
    return newInquiryInfoDao.countInquiry(inquiryTypeEnum, inquiryName, start, end);
  }

  /**
   * 获取询盘人数
   *
   * @author: lingjian @Date: 2019/8/8 15:01
   * @param inquiryTypeEnum 询盘类型
   * @param inquiryName 询盘名称
   * @param start 开始时间
   * @param end 结束时间
   * @return 询盘人数
   */
  private Integer getInquiryNumber(
      InquiryTypeEnum inquiryTypeEnum, String inquiryName, Date start, Date end) {
    return newInquiryInfoDao.countInquiryNumber(inquiryTypeEnum, inquiryName, start, end);
  }

  /**
   * 获取注册量
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return 注册量
   */
  private Integer getRegisterCount(RegisterTypeEnum registerTypeEnum, Date start, Date end) {
    return newUserInfoDao.countRegister(registerTypeEnum, start, end);
  }

  /**
   * 源数据转化real数据总表
   *
   * @author: lingjian @Date: 2019/8/8 13:35
   * @param start 开始时间
   * @param end 结束时间
   * @return DataViewReal对象
   */
  @Override
  public DataViewReal toReal(Date start, Date end) {
    DataViewReal real = new DataViewReal();
    // 浏览量
    real.setPageViewsCount(webVisitDao.countWebVisitInfo(start, end));
    // 总访客数，pc端访客数，wap端访客数
    real.setVisitorCountPc(getVisitorCount(start, end, DeviceTypeEnum.PC));
    real.setVisitorCountWap(getVisitorCount(start, end, DeviceTypeEnum.WAP));
    real.setVisitorCount(getVisitorCount(start, end, null));
    // 注册量
    real.setRegisterCount(getRegisterCount(null, start, end));
    // 询盘量
    real.setInquiryCount(getInquiryCount(null, null, start, end));
    // RFQ数
    real.setRfqCount(newInquiryInfoDao.countRFQ(start, end));
    // 转化时间
    real.setCreateTime(new Date());
    if (decideIsNull(real)) {
      realDao.save(real);
    }
    return real;
  }

  /**
   * 源数据转化flow流量表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewFlow> flow流量表集合对象
   */
  @Override
  public List<DataViewFlow> toFlow(Date start, Date end) {
    /**
     * @modify Lijie HelloBox@outlook.com 2019-08-08 09:26
     *     <p>添加逻辑注释
     *     <p>从用元数据表页面访问表,转换规定时间内的数据到流量展示层
     */
    List<DataViewFlow> list = new ArrayList<>();
    String[] arr = new String[] {"直接访问", "自然搜索"};
    for (DeviceTypeEnum d : DeviceTypeEnum.values()) {
      for (SourceTypeEnum s : SourceTypeEnum.values()) {
        for (String a : arr) {
          DataViewFlow flow = new DataViewFlow();
          // 设备来源
          flow.setDeviceType(d);
          // 来源类型
          flow.setSourceType(s);
          // TODO
          // 来源渠道
          flow.setSourcePage(a);
          // TODO
          // 访客数
          int count = 0;
          List<WebVisitInfo> webVisitInfo = webVisitDao.getWebVisitInfo(d, start, end);
          List<String> l = new ArrayList();
          for (WebVisitInfo w : webVisitInfo) {
            if (w.getEquipmentPlatform().equals(d)) {
              DataViewFlow temp = new DataViewFlow();
              if (w.getReferer() == null) {
                temp.setSourceType(SourceTypeEnum.INTERVIEW);
                temp.setSourcePage("直接访问");
              } else if (w.getReferer().indexOf("baidu") > 0) {
                temp.setSourceType(SourceTypeEnum.BAIDU);
                temp.setSourcePage("自然搜索");
              } else if (w.getReferer().indexOf("google") > 0) {
                temp.setSourceType(SourceTypeEnum.GOOGLE);
                temp.setSourcePage("自然搜索");
              } else {
                temp.setSourceType(SourceTypeEnum.OTHER);
                temp.setSourcePage("自然搜索");
              }
              if (s.equals(temp.getSourceType()) && a.equals(temp.getSourcePage())) {
                if (!l.contains(w.getIp())) {
                  l.add(w.getIp());
                  count++;
                }
              }
            }
          }

          flow.setVisitorCount(count);
          // 询盘量
          flow.setInquiryCount(0);
          // 创建时间
          flow.setCreateTime(new Date());
          if (flow.getDeviceType().equals(DeviceTypeEnum.PC)
              || flow.getDeviceType().equals(DeviceTypeEnum.WAP)) {
            if (flow.getVisitorCount() != 0) {
              flowDao.save(flow);
              list.add(flow);
            }
          }
        }
      }
    }
    return list;
  }

  /**
   * 源数据转化flowpage页面分析表
   *
   * @author: lingjian @Date: 2019/8/8 13:43
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewFlowPage> 页面分析表集合对象
   */
  @Override
  public List<DataViewFlowPage> toFlowPage(Date start, Date end) {
    List<DataViewFlowPage> list = new ArrayList<>();
    for (AccessTypeEnum a : AccessTypeEnum.values()) {
      DataViewFlowPage flowPage = new DataViewFlowPage();
      // 页面类型
      flowPage.setAccessType(a);
      // 浏览量
      Integer view = webVisitDao.countPageTypeView(a, start, end);
      flowPage.setViewCount(view);
      // 访客数,点击人数
      Integer visitor = webVisitDao.countPageTypeVisitor(a, start, end);
      flowPage.setVisitorCount(visitor);
      flowPage.setClickNumber(visitor);
      // 点击量
      Integer click = webVisitDao.getPageTypeClickCount(a, start, end);
      flowPage.setClickCount(click);
      // 点击率(该页面类型点击量/网站所有类型点击量)
      Integer clickAll = webVisitDao.getPageTypeClickCount(null, start, end);
      flowPage.setClickRate(clickAll != 0 ? click * 1.0 / clickAll : 0.0);
      /*
      访问次数：会话次数
       跳失率(只浏览一个页面就离开的访问次数 / 该页面的全部访问次数)
      */
      // 该页面的全部访问次数
      Integer session = webVisitDao.countPageTypeSession(a, start, end);
      // 只浏览该页面就离开的访问次数
      int count = 0;
      List<String> sessionList = webVisitDao.getPageTypeSession(a, start, end);
      for (String s : sessionList) {
        List<WebVisitInfo> webVisitList = webVisitDao.countPageTypeSession(s, start, end);
        if (webVisitList.size() == 1 && webVisitList.get(0).getPageType().equals(a)) {
          count++;
        }
      }
      // 跳失率
      flowPage.setJumpRate(session != 0 ? count * 1.0 / session : 0.0);
      // 平均停留时长（访问页面的总时长 / 浏览量）
      Long time = webVisitDao.getPageTypeTimeOnPage(a, start, end);
      if (view != 0) {
        flowPage.setAverageStayTime(time / view / 1000.0);
      } else {
        flowPage.setAverageStayTime(0.0);
      }
      // 创建时间
      flowPage.setCreateTime(new Date());
      if (decideIsNull(flowPage)) {
        flowPageDao.save(flowPage);
        list.add(flowPage);
      }
    }

    return list;
  }

  /**
   * 源数据转化inquiry询盘表
   *
   * @author: lingjian @Date: 2019/8/8 14:19
   * @param start 开始时间
   * @param end 结束时间
   * @return DataViewInquiry询盘对象
   */
  @Override
  public DataViewInquiry toInquiry(Date start, Date end) {
    DataViewInquiry inquiry = new DataViewInquiry();
    // 访客数
    inquiry.setVisitorCount(getVisitorCount(start, end, null));
    // 询盘数
    inquiry.setInquiryCount(getInquiryCount(null, null, start, end));
    // 询盘人数
    inquiry.setInquiryNumber(getInquiryNumber(null, null, start, end));
    // 创建时间
    inquiry.setCreateTime(new Date());
    if (decideIsNull(inquiry)) {
      inquiryDao.save(inquiry);
    }
    return inquiry;
  }

  /**
   * 源数据转化inquiryrank询盘排行表
   *
   * @author: lingjian @Date: 2019/8/8 15:15
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewInquiryRank> 询盘排行集合对象
   */
  @Override
  public List<DataViewInquiryRank> toInquiryRank(Date start, Date end) {
    List<DataViewInquiryRank> list = new ArrayList<>();
    for (InquiryTypeEnum i : InquiryTypeEnum.values()) {
      List<InquiryInfo> inquiryName = newInquiryInfoDao.getInquiryName(i, start, end);
      for (InquiryInfo ii : inquiryName) {
        DataViewInquiryRank inquiryRank = new DataViewInquiryRank();
        // 询盘类型
        inquiryRank.setInquiryType(i);
        // 询盘名称
        inquiryRank.setInquiryName(ii.getName());
        // 商家或商品pkey
        inquiryRank.setPkey(ii.getPkey());
        // 商家或商品图片
        inquiryRank.setImg(ii.getImg());
        // 浏览量
        // TODO
        inquiryRank.setViewCount(0);
        // 访客数
        // TODO
        inquiryRank.setVisitorCount(0);
        // 询盘数
        inquiryRank.setInquiryCount(getInquiryCount(i, ii.getName(), start, end));
        // 询盘人数
        inquiryRank.setInquiryNumber(getInquiryNumber(i, ii.getName(), start, end));
        // 询盘金额
        inquiryRank.setInquiryAmount(0);
        // 创建时间
        inquiryRank.setCreateTime(new Date());
        list.add(inquiryRank);
        inquiryRankDao.save(inquiryRank);
      }
    }
    return list;
  }

  /**
   * 源数据转化user用户表
   *
   * @author: lingjian @Date: 2019/8/13 9:36
   * @param start 开始时间
   * @param end 结束时间
   * @return DataViewUser 用户表对象
   * @title 新增新用户数，老用户数
   * @author Lijie HelloBox@outlook.com
   * @updateTime 2019-08-09 09:29
   */
  @Override
  public DataViewUser toUser(Date start, Date end) {
    DataViewUser user = new DataViewUser();
    /* 定义每次查询多少条 */
    int count = 1000;
    List<WebVisitInfo> webVisitInfos = null;
    /* 新用户数 */
    AtomicInteger newCount = new AtomicInteger();
    /* 老用户数 */
    AtomicInteger oldCount = new AtomicInteger();
    /* 数据库查询的起始位置 */
    Long offset = 0L;
    do {
      /* 查询数据 */
      webVisitInfos = webVisitDao.getWebVisitUserId(start, end, offset, count);
      CountDownLatch countDownLatch = new CountDownLatch(webVisitInfos.size());
      webVisitInfos
          .parallelStream()
          .forEach(
              webVisitInfo -> {
                /* 比较时间,和起始时间作比较,然后早于start的时间,为老用户,晚于的是新用户 */
                if (DateTimeUtil.timeDifferent(start, webVisitInfo.getUserId().getCreateTime())
                        .toNanos()
                    >= 0) {
                  newCount.getAndIncrement();
                } else {
                  oldCount.getAndIncrement();
                }
                countDownLatch.countDown();
              });
      offset += count;
      try {
        /* 等待处理完成 */
        countDownLatch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      /* 只有查询出结果等于每次查询条数的时候才会继续查询,否则 */
    } while (webVisitInfos.size() == count);
    user.setNewVisitorCount(newCount.intValue());
    user.setOldVisitorCount(oldCount.intValue());

    // 访客数
    user.setVisitorCount(getVisitorCount(start, end, null));
    // 总注册量
    user.setRegisterCount(getRegisterCount(null, start, end));
    // 采购商数量
    user.setPurchaseCount(getRegisterCount(RegisterTypeEnum.PURCHASE, start, end));
    // 供应商数量
    user.setSupplierCount(getRegisterCount(RegisterTypeEnum.SUPPLIER, start, end));
    // 创建时间
    user.setCreateTime(new Date());
    if (decideIsNull(user)) {
      userDao.save(user);
    }
    return user;
  }

  /**
   * 源数据转化userarea用户地域表
   *
   * @author: lingjian @Date: 2019/8/13 9:40
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewUserArea> 用户地域表集合对象
   */
  @Override
  public List<DataViewUserArea> toUserArea(Date start, Date end) {
    List<DataViewUserArea> list = new ArrayList<>();
    List<WebVisitInfo> webVisitUserArea = webVisitDao.getWebVisitUserArea(start, end);
    for (WebVisitInfo w : webVisitUserArea) {
      DataViewUserArea area = new DataViewUserArea();
      // 地域名称
      area.setArea(w.getLocation().getName());
      // 地域访客数
      area.setAreaCount(webVisitDao.countWebVisitUserArea(w.getLocation().getId(), start, end));
      // TODO
      // 地域访客总数
      area.setAreaCountTotal(0);
      // 创建时间
      area.setCreateTime(new Date());
      if (decideIsNull(area)) {
        userAreaDao.save(area);
      }
      list.add(area);
    }
    return list;
  }

  /**
   * 源数据转化country国家表
   *
   * @author: lingjian @Date: 2019/8/19 10:02
   * @param start 开始时间
   * @param end 结束时间
   * @return List<DataViewCountry> 国家表集合对象
   */
  @Override
  public List<DataViewCountry> toCountry(Date start, Date end) {
    List<DataViewCountry> list = new ArrayList<>();
    List<WebVisitInfo> webVisitUserArea = webVisitDao.getWebVisitUserArea(start, end);
    for (WebVisitInfo w : webVisitUserArea) {
      DataViewCountry country = new DataViewCountry();
      // 国家名称
      country.setCountryName(w.getLocation().getName());
      // 国家英文名称
      country.setCountryEnglishName(w.getLocation().getEngName());
      // 国旗图片
      country.setCountryImage(w.getLocation().getImg());
      // 访客数
      country.setVisitorCount(
          webVisitDao.countWebVisitUserArea(w.getLocation().getId(), start, end));
      // 创建时间
      country.setCreateTime(new Date());
      realCountryDao.save(country);
      list.add(country);
    }
    return list;
  }
}

package com.shoestp.mains.service.transform.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.dataview.flow.FlowDao;
import com.shoestp.mains.dao.dataview.real.RealDao;
import com.shoestp.mains.dao.transform.NewInquiryInfoDao;
import com.shoestp.mains.dao.transform.NewUserInfoDao;
import com.shoestp.mains.dao.transform.WebVisitDao;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlow;
import com.shoestp.mains.entitys.dataview.flow.DataViewFlowPage;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.entitys.metadata.WebVisitInfo;
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.service.transform.MetaToViewService;

import org.springframework.stereotype.Service;

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

  /**
   * 判断DataViewReal的数据是否为空
   *
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
   * 源数据转化real数据总表
   *
   * @param start 开始时间
   * @param end 结束时间
   * @return
   */
  @Override
  public DataViewReal toReal(Date start, Date end) {
    DataViewReal real = new DataViewReal();
    // 浏览量
    real.setPageViewsCount(webVisitDao.countWebVisitInfo(start, end));
    // 总访客数，pc端访客数，wap端访客数
    real.setVisitorCountPc(webVisitDao.countVisitor(DeviceTypeEnum.PC, start, end));
    real.setVisitorCountWap(webVisitDao.countVisitor(DeviceTypeEnum.WAP, start, end));
    real.setVisitorCount(webVisitDao.countVisitor(null, start, end));
    // 注册量
    real.setRegisterCount(newUserInfoDao.countRegister(start, end));
    // 询盘量
    real.setInquiryCount(newInquiryInfoDao.countInquiry(start, end));
    // RFQ数
    real.setRfqCount(newInquiryInfoDao.countRFQ(start, end));
    // 转化时间
    real.setCreateTime(new Date());
    if (decideIsNull(real)) {
      realDao.saveReal(real);
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
          // 来源渠道
          flow.setSourcePage(a);
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
              } else if (w.getReferer().indexOf("www.baidu") > 0) {
                temp.setSourceType(SourceTypeEnum.BAIDU);
                temp.setSourcePage("自然搜索");
              } else if (w.getReferer().indexOf("www.google") > 0) {
                temp.setSourceType(SourceTypeEnum.GOOGLE);
                temp.setSourcePage("自然搜索");
              } else {
                temp.setSourceType(SourceTypeEnum.OTHER);
                temp.setSourcePage("自然搜索");
              }
              if (temp.getSourceType().equals(s) && temp.getSourcePage().equals(a)) {
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

  @Override
  public List<DataViewFlowPage> toFlowPage(Date start, Date end) {
    return null;
  }
}

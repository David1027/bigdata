package com.shoestp.mains.service.transform.impl;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.dao.transform.NewUserInfoDao;
import com.shoestp.mains.dao.transform.WebVisitDao;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.entitys.metadata.enums.EquipmentPlatform;
import com.shoestp.mains.service.transform.MetaToViewService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;

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

  @Override
  public DataViewReal toCountry(Date start, Date end) {

    Date startDate = DateTimeUtil.getTimesOfDay(start, 0);
    Date endDate = DateTimeUtil.getTimesOfDay(end, 24);

    DataViewReal real = new DataViewReal();
    // 浏览量
    Integer page = webVisitDao.countWebVisitInfo(startDate, endDate);
    real.setPageViewsCount(page);

    // 总访客数，pc端访客数，wap端访客数
    Integer visitorPc = webVisitDao.countVisitor(EquipmentPlatform.PC, startDate, endDate);
    Integer visitorWap = webVisitDao.countVisitor(EquipmentPlatform.WAP, startDate, endDate);
    Integer visitor = webVisitDao.countVisitor(null, startDate, endDate);
    real.setVisitorCountPc(visitorPc);
    real.setVisitorCountWap(visitorWap);
    real.setVisitorCount(visitor);

    // 注册量
    Integer register = newUserInfoDao.countRegister(startDate, endDate);
    real.setRegisterCount(register);

    // 转化时间
    real.setCreateTime(new Date());

    return real;
  }
}

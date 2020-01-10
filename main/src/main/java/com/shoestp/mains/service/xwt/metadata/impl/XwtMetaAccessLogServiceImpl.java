package com.shoestp.mains.service.xwt.metadata.impl;

import java.io.IOException;
import java.util.Date;

import com.shoestp.mains.dao.xwt.metadata.dao.XwtMetaAccessLogDAO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaMemberInfo;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import com.shoestp.mains.enums.xwt.OAccessTypeEnum;
import com.shoestp.mains.enums.xwt.OMemberRoleEnum;
import com.shoestp.mains.service.urlmatchdatautil.URLMatchDataUtilService;
import com.shoestp.mains.service.xwt.metadata.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.start2do.utils.iputils.IpUtils;

/**
 * @description: 访问日志服务层接口实现类
 * @author: lingjian
 * @create: 2019/12/27 10:13
 */
@SuppressWarnings("ALL")
@Service
public class XwtMetaAccessLogServiceImpl implements XwtMetaAccessLogService {

  @Autowired private XwtMetaAccessLogDAO dao;
  @Autowired private XwtMetaMemberInfoService memberInfoService;
  @Autowired private XwtMetaCountryService countryService;
  @Autowired private XwtMetaProvinceService provinceService;
  @Autowired private URLMatchDataUtilService urlMatchDataUtilService;
  @Autowired private XwtMetaProductService productService;
  @Autowired private XwtMetaFavoriteService favoriteService;

  /**
   * 保存日志信息
   *
   * @param accessLog 访问日志对象
   */
  @Override
  public void save(XwtMetaAccessLog accessLog) throws IOException {
    // 处理来源类型
    accessLog.setSourceType(getSourceType(accessLog.getRef()));
    // 处理页面类型
    accessLog.setAccessType(urlMatchDataUtilService.getAccessType(accessLog.getUri()));
    // 关联用户信息表
    accessLog.setMemberInfoId(getMemberInfo(accessLog));
    // 关联国家表,关联省份表
    setCountryAndProvince(accessLog);
    // 处理页面停留时长
    accessLog.setTimeOnPage(getTimeOnPage(accessLog));
    // 添加创建时间
    accessLog.setCreateTime(new Date());
    // 保存日志信息
    dao.save(accessLog);
    // 保存店铺产品日志信息
    productService.saveProduct(accessLog);
    // 保存搜索关键词日志信息
    favoriteService.saveFavorite(accessLog);
  }

  /**
   * 设置页面停留时长
   *
   * @param accessLog 日志信息对象
   * @return Long 页面停留时长
   */
  private Long getTimeOnPage(XwtMetaAccessLog accessLog) {
    if (accessLog.getSsCount() > 0) {
      XwtMetaAccessLog access =
          dao.findBySsIdAndSsCount(accessLog.getSsId(), accessLog.getSsCount() - 1);
      if (access != null) {
        access.setTimeOnPage(accessLog.getSsTime().getTime() - access.getSsTime().getTime());
        dao.save(access);
      }
      return 0L;
    }
    return 0L;
  }

  /**
   * 设置日志信息对象的关联国家和关联身份
   *
   * @param xwtMetaAccessLog 日志信息对象
   */
  private void setCountryAndProvince(XwtMetaAccessLog accessLog) {
    // 根据ip获取[国家, 省份, 城市]
    String[] address = IpUtils.find(accessLog.getIp());
    if (address != null && address.length > 0) {
      // 设置国家id
      accessLog.setCountryId(countryService.getCountryId(address[0]));
      if (address.length > 1) {
        // 设置身份id
        accessLog.setProvinceId(provinceService.getProvinceId(address[1]));
      }
    } else {
      // 如果查询不存在，则默认返回6
      accessLog.setCountryId(6);
      accessLog.setProvinceId(1);
    }
  }

  /**
   * 根据用户id，用户token和用户访客id获取用户信息，保存并返回用户信息表id
   *
   * @param userId 用户id
   * @param token 用户token
   * @param uvId 访客id
   * @return Integer 用户信息表id
   */
  private Integer getMemberInfo(XwtMetaAccessLog accessLog) {
    // 判断访客id对应的用户信息记录是否已经存在，0-用户信息不存在，不等于0-用户信息已经存在
    XwtMetaMemberInfo memberInfo = memberInfoService.getUserInfoIdByUvId(accessLog.getUvId());
    if (memberInfo != null) {
      // 如果用户id存在，更新登陆用户信息
      if (accessLog.getUserId() != "") {
        // 添加第三方用户信息
        memberInfo.setUserId(Integer.parseInt(accessLog.getUserId()));
        memberInfo.setNickName(accessLog.getNickName());
        memberInfo.setEmail("null".equals(accessLog.getEmail()) ? null : accessLog.getEmail());
        memberInfo.setPhone("null".equals(accessLog.getPhone()) ? null : accessLog.getPhone());
        memberInfo.setUserRole(OMemberRoleEnum.REGISTER);
        memberInfoService.save(memberInfo);
      }
      // 用户信息已经存在，直接返回用户信息表id
      return memberInfo.getId();
    } else {
      // 用户信息不存在存在，获取用户信息，保存，并返回用户信息id
      // 创建用户信息对象
      XwtMetaMemberInfo xwtMetaMemberInfo = new XwtMetaMemberInfo();

      // 判断用户是否登陆，true-从第三方查询用户信息，false-创建游客信息
      if (accessLog.getUserId() != "") {
        // 设置用户信息
        xwtMetaMemberInfo.setUserId(Integer.parseInt(accessLog.getUserId()));
        xwtMetaMemberInfo.setNickName(accessLog.getNickName());
        xwtMetaMemberInfo.setEmail(
            "null".equals(accessLog.getEmail()) ? null : accessLog.getEmail());
        xwtMetaMemberInfo.setPhone(
            "null".equals(accessLog.getPhone()) ? null : accessLog.getPhone());
        xwtMetaMemberInfo.setUserRole(OMemberRoleEnum.REGISTER);
      } else {
        xwtMetaMemberInfo.setUserRole(OMemberRoleEnum.VISITOR);
      }
      // 设置访客id
      xwtMetaMemberInfo.setUvId(accessLog.getUvId());

      // 保存对象并返回id
      return memberInfoService.save(xwtMetaMemberInfo);
    }
  }

  /**
   * 根据ref跳转页面url获取来源类型
   *
   * @param ref 跳转类型url
   * @return SourceTypeEnum 来源类型枚举
   */
  private SourceTypeEnum getSourceType(String ref) {
    if ("/".equals(ref)) {
      return SourceTypeEnum.INTERVIEW;
    } else if (ref.indexOf("baidu") > 0) {
      return SourceTypeEnum.BAIDU;
    } else if (ref.indexOf("google") > 0) {
      return SourceTypeEnum.GOOGLE;
    }
    return SourceTypeEnum.OTHER;
  }
}

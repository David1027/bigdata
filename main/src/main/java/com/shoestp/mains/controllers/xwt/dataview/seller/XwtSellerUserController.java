package com.shoestp.mains.controllers.xwt.dataview.seller;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.seller.dto.XwtSellerUserDTO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaProduct;
import com.shoestp.mains.enums.xwt.OProductRoleEnum;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.metadata.XwtMetaProductService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 鞋网通商家后台用户控制层
 * @author: lingjian
 * @create: 2020/1/9 14:24
 */
@RestController
@RequestMapping("/xwt/seller/user/")
public class XwtSellerUserController {

  @Resource private XwtMetaProductService service;
  @Resource private XwtSellerUserDTO dto;

  /**
   * 根据时间，店铺信息获取实时访客
   *
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param role 店铺角色
   * @param enterpriseId 店铺id
   * @param countryId 国家id
   * @param provinceId 省份id
   * @return PageVO<XwtSellerUserVO> 实时访客VO分页对象
   */
  @PostMapping("list_real_visitor")
  public Object listRealVisitor(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      OProductRoleEnum role,
      Integer enterpriseId,
      Integer countryId,
      Integer provinceId) {
    return MessageResult.builder()
        .code(1)
        .result(
            dto.toVoPage(
                service.listRealVisitor(
                    startDate, endDate, role, enterpriseId, countryId, provinceId)))
        .build();
  }

  /**
   * 根据时间，店铺信息和用户信息获取访客url信息
   *
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param role 店铺角色
   * @param enterpriseId 店铺id
   * @param memberId 用户id
   * @return
   */
  @PostMapping("list_real_url")
  public Object listRealUrl(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      OProductRoleEnum role,
      Integer enterpriseId,
      Integer memberId) {
    return MessageResult.builder()
        .code(1)
        .result(
            dto.toVoPageUrl(service.listRealUrl(startDate, endDate, role, enterpriseId, memberId)))
        .build();
  }
}

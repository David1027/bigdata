package com.shoestp.mains.controllers.xwt.dataview.seller;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.seller.dto.XwtSellerProductDTO;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.dataview.seller.XwtSellerProductService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 鞋网通商家后台商品控制层
 * @author: lingjian
 * @create: 2020/1/9 14:23
 */
@RestController
@RequestMapping("/xwt/seller/product/")
public class XwtSellerProductController {

  @Resource private XwtSellerProductService service;
  @Resource private XwtSellerProductDTO dto;

  /**
   * 根据时间，店铺id获取产品排行详情
   *
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param enterpriseId 店铺id
   * @param sortType 排序规则
   * @param country 国家id
   * @return PageVO<XwtSellerProductVO> 产品VO分页对象
   */
  @PostMapping("list_product_rank")
  public Object listProductRank(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      Integer enterpriseId,
      String sortType,
      Integer country) {
    return MessageResult.builder()
        .code(1)
        .result(
            dto.toVoPage(
                service.listProductRank(startDate, endDate, enterpriseId, sortType, country)))
        .build();
  }
}

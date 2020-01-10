package com.shoestp.mains.controllers.xwt.dataview.seller;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.xwt.dataview.seller.dto.XwtSellerSearchDTO;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.xwt.dataview.seller.XwtSellerSearchTermService;

import org.springframework.web.bind.annotation.*;

/**
 * @description: 鞋网通商家后台-搜索控制层
 * @author: lingjian
 * @create: 2020/1/9 14:24
 */
@RestController
@RequestMapping("/xwt/seller/search/")
public class XwtSellerSearchController {

  @Resource private XwtSellerSearchTermService service;
  @Resource private XwtSellerSearchDTO dto;

  /**
   * 获取商家后台首页今日热搜
   *
   * @return PageVO<XwtSellerSearchVO> 搜索词VO分页对象
   */
  @GetMapping(value = "search")
  public Object listSearch(Integer countryId) {
    return MessageResult.builder().code(1).result(dto.toVoList(service.listSearch(countryId))).build();
  }
}

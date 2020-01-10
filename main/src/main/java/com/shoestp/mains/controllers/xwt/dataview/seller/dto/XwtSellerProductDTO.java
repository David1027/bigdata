package com.shoestp.mains.controllers.xwt.dataview.seller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.dataview.seller.vo.product.XwtSellerProductVO;
import com.shoestp.mains.entitys.xwt.dataview.seller.product.XwtSellerProduct;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;
import com.shoestp.mains.views.xwt.PageVO;

import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * @description: 产品DTO
 * @author: lingjian
 * @create: 2020/1/10 11:27
 */
@Component
@ToString
public class XwtSellerProductDTO {

  /**
   * 将BO转换为VO
   *
   * @param product 产品对象
   * @return XwtSellerProductVO 产品VO
   */
  public XwtSellerProductVO toVo(XwtSellerProduct product) {
    return BeanCopyUtils.copyProperties(product, XwtSellerProductVO.class);
  }

  /**
   * 将BO分页对象转换为VO分页对象
   *
   * @param page 产品分页对象
   * @return PageVO<XwtSellerProductVO> 产品VO分页对象
   */
  public PageVO<XwtSellerProductVO> toVoPage(Page<XwtSellerProduct> page) {
    List<XwtSellerProductVO> result =
        page.getContent().parallelStream().map(this::toVo).collect(Collectors.toList());
    return new PageVO<>(page.getTotalElements(), result);
  }
}

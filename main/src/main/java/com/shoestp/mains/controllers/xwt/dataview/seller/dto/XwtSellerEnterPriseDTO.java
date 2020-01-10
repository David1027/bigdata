package com.shoestp.mains.controllers.xwt.dataview.seller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.dataview.seller.vo.enterprise.XwtSellerEnterPriseCountryVO;
import com.shoestp.mains.controllers.xwt.dataview.seller.vo.enterprise.XwtSellerEnterPriseVO;
import com.shoestp.mains.entitys.xwt.dataview.seller.enterprise.XwtSellerEnterprise;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @description: 店铺DTO
 * @author: lingjian
 * @create: 2020/1/9 16:52
 */
@Component
@ToString
public class XwtSellerEnterPriseDTO {

  /**
   * 将BO转换为VO
   *
   * @param enterprise 店铺对象
   * @return XwtSellerEnterPriseVO 店铺VO
   */
  public XwtSellerEnterPriseVO toVo(XwtSellerEnterprise enterprise) {
    return BeanCopyUtils.copyProperties(enterprise, XwtSellerEnterPriseVO.class);
  }

  /**
   * 将BO转换为VO
   *
   * @param enterprise 店铺对象
   * @return XwtSellerEnterPriseCountryVO 店铺国家VO
   */
  private XwtSellerEnterPriseCountryVO toVoCountry(XwtSellerEnterprise enterprise) {
    return BeanCopyUtils.copyProperties(enterprise, XwtSellerEnterPriseCountryVO.class);
  }

  /**
   * 将BO集合转换为VO集合
   *
   * @param list 店铺集合对象
   * @return List<XwtSellerEnterPriseCountryVO> 店铺国家VO集合对象
   */
  public List<XwtSellerEnterPriseCountryVO> toVoList(List<XwtSellerEnterprise> list) {
    return list.parallelStream().map(this::toVoCountry).collect(Collectors.toList());
  }
}

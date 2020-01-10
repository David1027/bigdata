package com.shoestp.mains.controllers.xwt.dataview.seller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.dataview.seller.vo.user.XwtSellerUrlVO;
import com.shoestp.mains.controllers.xwt.dataview.seller.vo.user.XwtSellerUserVO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaProduct;
import com.shoestp.mains.enums.xwt.OMemberRoleEnum;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;
import com.shoestp.mains.views.xwt.PageVO;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * @description: 实时访客DTO
 * @author: lingjian
 * @create: 2020/1/10 14:43
 */
@Component
@Data
public class XwtSellerUserDTO {

  /**
   * 将BO转化为VO
   *
   * @param product 日志信息对象
   * @return XwtSellerUserVO 实时访客VO
   */
  public XwtSellerUserVO toVo(XwtMetaProduct product) {
    XwtSellerUserVO vo = BeanCopyUtils.copyProperties(product, XwtSellerUserVO.class);
    // 用户名称
    vo.setMemberName(
        product.getMemberInfo().getNickName() == null
            ? "游客"
            : product.getMemberInfo().getNickName());
    // 是否新用户
    vo.setIsNewMember(OMemberRoleEnum.VISITOR.equals(product.getMemberInfo().getUserRole()));
    // 国家名称
    vo.setCountryName(product.getCountry().getName());
    // 省份名称
    vo.setProvinceName(product.getProvince().getName());
    return vo;
  }

  /**
   * 将BO集合对象转化为VO集合对象
   *
   * @param page 日志信息对象分页对象
   * @return PageVO<XwtSellerUserVO> 实时访客VO分页对象
   */
  public PageVO<XwtSellerUserVO> toVoPage(Page<XwtMetaProduct> page) {
    List<XwtSellerUserVO> result =
        page.getContent().parallelStream().map(this::toVo).collect(Collectors.toList());
    return new PageVO<>(page.getTotalElements(), result);
  }

  /**
   * 将BO转化为VO
   *
   * @param product 日志信息对象
   * @return XwtSellerUserVO 实时访客VO
   */
  private XwtSellerUrlVO toVoUrl(XwtMetaProduct product) {
    return BeanCopyUtils.copyProperties(product, XwtSellerUrlVO.class);
  }

  /**
   * 将BO集合对象转化为VO集合对象
   *
   * @param page 日志信息对象分页对象
   * @return PageVO<XwtSellerUserVO> 实时访客VO分页对象
   */
  public PageVO<XwtSellerUrlVO> toVoPageUrl(Page<XwtMetaProduct> page) {
    List<XwtSellerUrlVO> result =
        page.getContent().parallelStream().map(this::toVoUrl).collect(Collectors.toList());
    return new PageVO<>(page.getTotalElements(), result);
  }
}

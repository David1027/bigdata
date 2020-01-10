package com.shoestp.mains.controllers.xwt.dataview.seller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.dataview.seller.vo.search.XwtSellerSearchVO;
import com.shoestp.mains.entitys.xwt.dataview.seller.search.XwtSellerSearchTerm;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;
import com.shoestp.mains.views.xwt.PageVO;

import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * @description: 搜索词DTO
 * @author: lingjian
 * @create: 2020/1/9 15:47
 */
@Component
@ToString
public class XwtSellerSearchDTO {

  /**
   * 将BO转化为VO
   *
   * @param searchTerm 搜索词表对象
   * @return XwtSellerSearchVO 搜索词VO
   */
  private XwtSellerSearchVO toVo(XwtSellerSearchTerm searchTerm) {
    return BeanCopyUtils.copyProperties(searchTerm, XwtSellerSearchVO.class);
  }

  /**
   * 将BO分页集合转化为VO分页集合
   *
   * @param page 搜索词表分页对象
   * @return PageVO<XwtSellerSearchVO> 搜索词VO分页对象
   */
  public PageVO<XwtSellerSearchVO> toVoList(Page<XwtSellerSearchTerm> page) {
    List<XwtSellerSearchVO> result =
        page.getContent().parallelStream().map(this::toVo).collect(Collectors.toList());
    return new PageVO<>(page.getTotalElements(), result);
  }
}

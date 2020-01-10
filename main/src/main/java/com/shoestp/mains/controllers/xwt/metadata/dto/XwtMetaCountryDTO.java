package com.shoestp.mains.controllers.xwt.metadata.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.metadata.vo.XwtMetaCountryVO;
import com.shoestp.mains.entitys.xwt.metadata.XwtMetaCountry;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @description: 国家表DTO
 * @author: lingjian
 * @create: 2020/1/7 9:30
 */
@Component
@ToString
public class XwtMetaCountryDTO {

  /**
   * 将BO转换为VO
   *
   * @param country 国家表对象
   * @return XwtMetaCountryVO 国家选择框VO
   */
  public XwtMetaCountryVO toVo(XwtMetaCountry country) {
    return BeanCopyUtils.copyProperties(country, XwtMetaCountryVO.class);
  }

  /**
   * 将BO集合转换为VO集合
   *
   * @param list 国家表集合对象
   * @return List<XwtMetaCountryVO> 国家选择框VO集合对象
   */
  public List<XwtMetaCountryVO> toVoList(List<XwtMetaCountry> list) {
    return list.parallelStream().map(this::toVo).collect(Collectors.toList());
  }
}

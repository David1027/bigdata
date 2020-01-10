package com.shoestp.mains.controllers.xwt.dataview.plat.dto.country;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.dataview.plat.vo.country.XwtCountryVO;
import com.shoestp.mains.entitys.xwt.dataview.plat.country.XwtViewCountry;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @description: 国家表dto
 * @author: lingjian
 * @create: 2020/1/3 9:33
 */
@Component
@ToString
public class XwtCountryDTO {

  /**
   * 将BO转化为VO
   *
   * @param country 国家表对象
   * @return XwtMetaCountryVO 国家前端展示类
   */
  public XwtCountryVO toVo(XwtViewCountry country) {
    return BeanCopyUtils.copyProperties(country, XwtCountryVO.class);
  }

  /**
   * 将BO集合转化为VO集合
   *
   * @param list 国家表集合对象
   * @return List<XwtMetaCountryVO> 国家前端展示类集合对象
   */
  public List<XwtCountryVO> toVoList(List<XwtViewCountry> list) {
    return list.parallelStream().map(this::toVo).collect(Collectors.toList());
  }
}

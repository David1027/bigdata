package com.shoestp.mains.controllers.xwt.dataview.plat.dto.real;

import com.shoestp.mains.controllers.xwt.dataview.plat.vo.real.XwtIndexOverVO;
import com.shoestp.mains.entitys.xwt.dataview.real.XwtViewReal;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @description: 首页整体看板前端展示类
 * @author: lingjian
 * @create: 2020/1/3 10:34
 */
@Component
@ToString
public class XwtRealDTO {

  /**
   * 将BO转换为VO
   *
   * @param real 实时表对象
   * @return XwtIndexOverVO 首页整体看板前端展示类对象
   */
  public XwtIndexOverVO toVo(XwtViewReal real) {
    return BeanCopyUtils.copyProperties(real, XwtIndexOverVO.class);
  }
}

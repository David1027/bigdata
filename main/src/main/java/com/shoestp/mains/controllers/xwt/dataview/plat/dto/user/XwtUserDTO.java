package com.shoestp.mains.controllers.xwt.dataview.plat.dto.user;

import java.util.List;
import java.util.stream.Collectors;

import com.shoestp.mains.controllers.xwt.dataview.plat.vo.user.XwtUserAreaVO;
import com.shoestp.mains.controllers.xwt.dataview.plat.vo.user.XwtUserVO;
import com.shoestp.mains.entitys.xwt.dataview.user.XwtViewUser;
import com.shoestp.mains.entitys.xwt.dataview.user.XwtViewUserArea;
import com.shoestp.mains.utils.xwt.BeanCopyUtils;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @description: 用户前端展示类
 * @author: lingjian
 * @create: 2020/1/3 16:00
 */
@Component
@ToString
public class XwtUserDTO {

  /**
   * 将BO转换为VO
   *
   * @param user 用户对象
   * @return XwtUserVO 用户前端展示类
   */
  public XwtUserVO toVo(XwtViewUser user) {
    return BeanCopyUtils.copyProperties(user, XwtUserVO.class);
  }

  /**
   * 将BO转换为VO
   *
   * @param userArea 用户地域对象
   * @return XwtUserAreaVO 用户地域前端展示类
   */
  public XwtUserAreaVO toVo(XwtViewUserArea userArea) {
    return BeanCopyUtils.copyProperties(userArea, XwtUserAreaVO.class);
  }

  /**
   * 将BO集合转换为VO集合
   *
   * @param list 用户表集合对象
   * @return List<XwtUserAreaVO> 用户地域集合对象
   */
  public List<XwtUserAreaVO> toVoList(List<XwtViewUserArea> list) {
    return list.parallelStream().map(this::toVo).collect(Collectors.toList());
  }
}

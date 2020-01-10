package com.shoestp.mains.enums.xwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 用户角色
 * @author: lingjian
 * @create: 2019/12/30 14:53
 */
@Getter
@AllArgsConstructor
public enum ORegisterRoleEnum {
  /** 普通用户 */
  GENERAL("普通用户"),
  /** 预备鞋企 */
  PREPARE_SHOES_ENTERPRISE("预备鞋企"),
  /** 鞋企 */
  SHOES_ENTERPRISE("鞋企"),
  /** 预备辅料商 */
  PREPARE_MATERIAL_ENTERPRISE("预备辅料商"),
  /** 辅料商 */
  MATERIAL_ENTERPRISE("辅料商"),
  /** 设计师 */
  SHOES_DESIGNER("设计师");
  /** 描述 */
  private String description;
}

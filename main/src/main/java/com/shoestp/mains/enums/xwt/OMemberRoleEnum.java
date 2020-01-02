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
public enum OMemberRoleEnum {
  /** 游客 */
  VISITOR("游客"),
  /** 注册用户 */
  REGISTER("注册用户");
  /** 描述 */
  private String description;
}

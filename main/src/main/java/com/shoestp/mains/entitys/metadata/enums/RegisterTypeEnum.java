package com.shoestp.mains.entitys.metadata.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 注册类型枚举类
 * @author: lingjian @Date: 2019/5/8 9:27
 */
@Getter
@AllArgsConstructor
public enum RegisterTypeEnum {
  /** 采购商 */
  PURCHASE("采购商"),
  /** 供应商 */
  SUPPLIER("供应商"),
  /** 管理员  */
  ADMIN("供应商"),
  /** 游客 */
  VISITOR("游客");

  /** 名称 */
  private String name;
}

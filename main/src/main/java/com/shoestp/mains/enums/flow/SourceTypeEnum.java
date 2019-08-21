package com.shoestp.mains.enums.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 来源类型
 * @author: lingjian
 * @create: 2019/5/8 9:57
 */
@Getter
@AllArgsConstructor
public enum SourceTypeEnum {
  /** 自主访问 */
  INTERVIEW("自主访问"),
  /** 谷歌 */
  GOOGLE("谷歌访问"),
  /** 百度 */
  BAIDU("百度访问"),
  /** 其他 */
  OTHER("其他访问");

  private String name;
}

package com.shoestp.mains.views.DataView.user;

import javax.persistence.Column;

import lombok.Data;

/**
 * @description: 用户性别前端展示类
 * @author: lingjian
 * @Date: 2019/5/13 16:02
 */
@Data
public class DataViewUserSexView {
  /** 男性人数 */
  @Column(name = "man_sex_count")
  private Integer manSexCount;
  /** 女性人数 */
  @Column(name = "woman_sex_count")
  private Integer womanSexCount;
  /** 未知人数 */
  @Column(name = "unknown_sex_count")
  private Integer unknownSexCount;
}

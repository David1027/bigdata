package com.shoestp.mains.views.dataview.user;

import lombok.Data;

/**
 * @description: 用户性别前端展示类
 * @author: lingjian @Date: 2019/5/13 16:02
 */
@Data
public class DataViewUserSexView {
  /** 性别类型：MAN-男，WOMAN-女，UNKNOWN-未知 */
  private String sex;
  /** 性别人数 */
  private Integer sexCount;
}

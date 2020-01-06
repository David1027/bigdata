package com.shoestp.mains.controllers.xwt.dataview.plat.vo.user;

import lombok.Data;

/**
 * @description: 用户性别前端展示类
 * @author: lingjian
 * @create: 2020/1/3 16:36
 */
@Data
public class XwtUserAreaVO {

  /** 地域名称 */
  private String area;
  /** 地域访客数 */
  private Long areaCount;
}

package com.shoestp.mains.controllers.xwt.dataview.plat.vo.user;

import lombok.Data;

/**
 * @description: 用户前端展示类
 * @author: lingjian
 * @create: 2020/1/3 16:01
 */
@Data
public class XwtUserVO {

  /** 访客数 */
  private Long visitorCount;
  /** 新用户数 */
  private Long newVisitorCount;
  /** 老用户数 */
  private Long oldVisitorCount;
  /** 总注册量 */
  private Long registerCount;
  /** 普通用户数量 */
  private Long generalCount;
  /** 鞋企数量 */
  private Long shoesCount;
  /** 辅料商数量 */
  private Long materialCount;
  /** 设计师数量 */
  private Long designerCount;
}

package com.shoestp.mains.views.dataview.user;

import lombok.Data;

/**
 * @description: 用户前端展示类
 * @author: lingjian @Date: 2019/5/13 14:25
 */
@Data
public class DataViewUserView {
  /** 总访客数 */
  private Integer visitorCount;
  /** 新用户数 */
  private Integer newVisitorCount;
  /** 老用户数 */
  private Integer oldVisitorCount;
  /** 总注册量 */
  private Integer registerCount;
  /** 采购商数量 */
  private Integer purchaseCount;
  /** 供应商数量 */
  private Integer supplierCount;
}

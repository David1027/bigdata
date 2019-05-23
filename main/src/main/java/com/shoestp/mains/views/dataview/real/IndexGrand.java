package com.shoestp.mains.views.dataview.real;

import lombok.Data;

/**
 * @description: 首页实时前端展示类
 * @author: lingjian @Date: 2019/5/23 14:11
 */
@Data
public class IndexGrand {
  /** 累计询盘量 */
  private Integer grandInquiry;
  /** 累计RDQ数 */
  private Integer grandRfq;
  /** 累计注册量 */
  private Integer grandRegister;
  /** 累计采购商 */
  private Integer grandPurchase;
  /** 累计供应商 */
  private Integer grandSupplier;
}

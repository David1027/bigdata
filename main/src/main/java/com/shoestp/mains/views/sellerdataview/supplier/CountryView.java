package com.shoestp.mains.views.sellerdataview.supplier;

import lombok.Data;

/**
 * @description: 实时访客前端展示类
 * @author: lingjian
 * @create: 2019/5/24 14:50
 */
@Data
public class CountryView {
  /** 国家 */
  private String country;
  /** 访客数 */
  private Integer visitorCount;
}

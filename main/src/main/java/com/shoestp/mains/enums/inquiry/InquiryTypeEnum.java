package com.shoestp.mains.enums.inquiry;

/**
 * @description: 询盘类型
 * @author: lingjian
 * @create: 2019/5/8 9:44
 */
public enum InquiryTypeEnum {
  /** 供应商询盘 */
  SUPPLIER(1, "供应商询盘"),
  /** 商品询盘 */
  COMMODITY(2, "商品询盘"),
  /** 热门关键词 */
  SEARCHTERM(3, "热门关键词"),
  /** RFQ */
  RFQ(4, "RFQ"),
  /** 着陆页询盘 */
  DISHENG(298, "迪盛着陆页"),
  JUNA(295, "巨纳着陆页"),
  NAIRUI(322, "耐瑞着陆页"),
  AILAIFA(281, "爱莱发着陆页"),
  QIAONAI(292, "乔奈着陆页"),
  FENGSHENG(283, "丰盛着陆页"),
  HUALIOU(13, "华利欧着陆页"),
  HUAYOU(318, "华友着陆页"),
  JIANSHA(279, "剑鲨着陆页"),
  JIEKBAQIAO(78, "杰克巴乔着陆页"),
  KANGRUI(16, "康睿着陆页"),
  KANGYIDA(291, "康益达着陆页"),
  QIANBAIMENG(282, "千百梦着陆页"),
  XINJIYUAN(317, "新纪元着陆页"),
  YILIZHOU(23, "亿力洲着陆页"),
  ZHANHAO(301, "展豪着陆页"),
  SHOESTP(-1, "shoestp着陆页");

  public String name;
  public int sup;

  public String getName() {
    return name;
  }

  public int getSup() {
    return sup;
  }

  InquiryTypeEnum(int sup, String name) {
    this.sup = sup;
    this.name = name;
  }
}

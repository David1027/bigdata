package com.shoestp.mains.controllers.xwt.dataview.seller.vo.search;

import lombok.Data;

/**
 * @description: 搜索词VO
 * @author: lingjian
 * @create: 2020/1/9 15:48
 */
@Data
public class XwtSellerSearchVO {

  /** 搜索关键词 */
  private String keyWord;
  /** 搜索次数 */
  private Long searchCount;
  /** 国家id */
  private Integer countryId;
}

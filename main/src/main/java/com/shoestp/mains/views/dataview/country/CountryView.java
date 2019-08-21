package com.shoestp.mains.views.dataview.country;

import lombok.Data;

/**
 * @description: 国家前端展示类
 * @author: lingjian @Date: 2019/5/13 10:31
 */
@Data
public class CountryView {
  /** 国家名称 */
  private String countryName;
  /** 国家英文名称 */
  private String countryEnglishName;
  /** 国旗图片 */
  private String countryImage;
  /** 访客数 */
  private Integer visitorCount;
}

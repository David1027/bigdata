package com.shoestp.mains.controllers.xwt.dataview.plat.vo.country;

import lombok.Data;

/**
 * @description: 国家前端展示类
 * @author: lingjian
 * @create: 2020/1/3 9:23
 */
@Data
public class XwtCountryVO {
  /** 国家名称 */
  private String countryName;
  /** 国家英文名称 */
  private String countryEnglishName;
  /** 国旗图片 */
  private String countryImage;
  /** 访客数 */
  private Long visitorCount;
}

package com.shoestp.mains.views.dataview.real;

import lombok.Data;

/**
 * @description: 搜索关键词前端展示类
 * @author: lingjian
 * @create: 2019/8/21 10:10
 */
@Data
public class HomeSearchView {
  /** 搜索关键词 */
  private String keyword;
  /** 搜索人气-浏览量 */
  private Integer viewCount;
}

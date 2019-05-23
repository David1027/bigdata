package com.shoestp.mains.views.dataview.utils;

import java.util.List;
import lombok.Data;

/**
 * @description: 分页类
 * @author: lingjian
 * @create: 2019/5/23 16:32
 */
@Data
public class Page {
  private List page;
  private Integer totalCount;
}

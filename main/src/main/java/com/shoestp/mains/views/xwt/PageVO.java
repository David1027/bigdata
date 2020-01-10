package com.shoestp.mains.views.xwt;

import java.util.List;

import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * 分页前端类
 *
 * @author: lingjian
 * @create: 2020/1/9 16:05
 * @param <T>
 */
@Data
public class PageVO<T> {

  /** 总条数 */
  private Long total;

  /** 集合对象 */
  private List<T> list;

  public PageVO(Page<T> page) {
    this.total = page.getTotalElements();
    this.list = page.getContent();
  }

  public PageVO(Long total, List<T> items) {
    this.total = total;
    this.list = items;
  }
}

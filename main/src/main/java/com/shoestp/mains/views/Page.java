package com.shoestp.mains.views;

import lombok.Data;

import java.util.List;

/**
 * The type Page.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /09/12
 * @since *
 * @param <T> the type parameter
 */
@Data
public class Page<T> {

  /** The Total. */
  private Long total;

  /** The List. */
  private List<T> list;

  /** The Limit. */
  private Integer limit;

  public static Page build(org.springframework.data.domain.Page allByNameAndType) {
    Page page = new Page();
    page.setTotal(allByNameAndType.getTotalElements());
    page.setList(allByNameAndType.getContent());
    return page;
  }
}

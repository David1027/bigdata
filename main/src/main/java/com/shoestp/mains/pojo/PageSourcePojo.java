package com.shoestp.mains.pojo;

import com.shoestp.mains.enums.flow.SourceTypeEnum;
import lombok.Data;

/**
 * The type Page source pojo. 用于URLMatchDataUtilServiceImpl 类
 *
 * @author lijie
 * @date 2019 /08/07
 * @since
 */
@Data
public class PageSourcePojo {
  /** The Source type. */
  private SourceTypeEnum sourceType;
  /** The Source page. 对应data_view_flow表source_page */
  private String sourcePage;
}

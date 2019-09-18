package com.shoestp.mains.controllers.dataview.inquiry.views;

import com.shoestp.mains.controllers.dataview.inquiry.views.bodys.InquiryDataView;
import lombok.Data;

/**
 * The type Inquiry view.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /09/12
 * @since
 */
@Data
public class InquiryView {
  /** The Id. */
  private Integer id;

  /** The Data. 数据本体 */
  private InquiryDataView data;

  /** The Count. 分页总条数 */
  private Long count;

  /** The Start. 初始条数 */
  private Long start;

  /** The Limit. 每页显示条数 */
  private Integer limit;
}

package com.shoestp.mains.controllers.dataview.inquiry.views.bodys;

import java.time.LocalDateTime;

/**
 * The type Data.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /09/12
 * @since
 */
@lombok.Data
public class InquiryDataView {
  /** The Statistical time. */
  private LocalDateTime statistical_time;

  /** The Supplier name. 供应商名称 */
  private String supplierName;

  /** The Pv. 访问量 */
  private Long pv;

  /** The Uv. 访客数 */
  private Long uv;

  /** The Inquiry count. 询盘总数 */
  private Long inquiry_count;
}

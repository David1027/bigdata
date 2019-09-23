package com.shoestp.mains.service.dataview.inquiry.impl.pojo;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.Data;

import java.nio.charset.Charset;

/**
 * The type Product data pojo.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /09/12
 * @since
 */
@Data
public class SupplierDataPojo {

  /** The Title. */
  private String title;

  /** The Number. pv */
  private Integer pv;

  /** The Image. */
  private String image;

  /** The Uv. */
  private Integer uv;
  /** Instantiates a new Product data pojo. */
  private SupplierDataPojo() {}

  /** The Inquiry count. */
  private Long inquiryCount;

  /** The Ip filter. */
  private BloomFilter<String> ipFilter;
  /**
   * Gets instance.
   *
   * @author lijie
   * @date 2019 /09/12
   * @since *
   * @return the instance
   */
  public static SupplierDataPojo getInstance() {
    SupplierDataPojo productDataPojo = new SupplierDataPojo();
    productDataPojo.ipFilter =
        BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 1000);
    return productDataPojo;
  }

  public Long getInquiryCount() {
    if (inquiryCount == null) {
      return 0L;
    }
    return inquiryCount;
  }

  /**
   * Add
   *
   * @author lijie
   * @date 2019 /09/12
   * @since .
   */
  public void add() {
    if (pv == null) {
      pv = 1;
    } else {
      pv++;
    }
  }

  /**
   * Add uv
   *
   * @author lijie
   * @date 2019 /09/16
   * @since .
   */
  public void addUV() {
    if (uv == null) {
      uv = 1;
    } else {
      uv++;
    }
  }

  public void addInquiry() {
    if (inquiryCount == null) {
      inquiryCount = 1L;
    } else {
      inquiryCount++;
    }
  }
}

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
public class ProductDataPojo {

  /** The Title. */
  private String title;

  /** The Number. pv */
  private Integer number;

  /** The Image. */
  private String image;
  /** The Uv. */
  private Integer uv;
  /** Instantiates a new Product data pojo. */
  private ProductDataPojo() {}

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
  public static ProductDataPojo getInstance() {
    ProductDataPojo productDataPojo = new ProductDataPojo();
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
    if (number == null) {
      number = 1;
    } else {
      number++;
    }
  }

  /**
   * Add
   *
   * @author lijie
   * @date 2019 /09/12
   * @since .
   * @param title the title
   */
  public void add(String title) {
    this.title = title;
    add();
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

  public void addInquery() {
    if (inquiryCount == null) {
      inquiryCount = 1L;
    } else {
      inquiryCount++;
    }
  }
}

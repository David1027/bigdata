package com.shoestp.mains.controllers.sellerdataview;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.dataview.CountryController;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.sellerdataview.ProductService;
import com.shoestp.mains.service.sellerdataview.SupplierService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 商家后台商品表控制器
 * @author: lingjian
 * @create: 2019/5/24 11:30
 */
@RestController
@RequestMapping("/api/seller")
public class ProductController {

  private static final Logger logger = LogManager.getLogger(CountryController.class);

  @Resource private ProductService productService;

  /**
   * 获取实时排行
   *
   * @author: lingjian @Date: 2019/5/27 9:51
   * @param date
   * @param country
   * @param datetype
   * @param supplierid
   * @param type
   * @param start
   * @param limit
   * @return
   */
  @PostMapping(value = "/realrank")
  public Object getRealRank(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
      String country,
      String datetype,
      Integer supplierid,
      String type,
      Integer start,
      Integer limit) {
    logger.debug(date);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(productService.getRealRank(date, country,datetype, supplierid, type, start, limit))
        .build();
  }
}

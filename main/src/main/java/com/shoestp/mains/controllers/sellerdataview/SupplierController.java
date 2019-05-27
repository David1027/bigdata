package com.shoestp.mains.controllers.sellerdataview;

import java.util.Date;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.dataview.CountryController;
import com.shoestp.mains.pojo.MessageResult;
import com.shoestp.mains.service.sellerdataview.SupplierService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 商家后台供应商表控制器
 * @author: lingjian
 * @create: 2019/5/24 11:30
 */
@RestController
@RequestMapping("/api/seller")
public class SupplierController {

  private static final Logger logger = LogManager.getLogger(CountryController.class);

  @Resource private SupplierService supplierService;

  /**
   * 获取首页概况
   *
   * @author: lingjian @Date: 2019/5/27 10:57
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  @PostMapping(value = "/indexoverview")
  public Object getIndexOverview(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      Integer supplierid) {
    logger.debug(startDate + "===" + endDate);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(supplierService.getIndexOverview(startDate, endDate, supplierid))
        .build();
  }

  /**
   * 获取数据趋势
   *
   * @author: lingjian @Date: 2019/5/27 11:26
   * @param num
   * @param supplierid
   * @return
   */
  @PostMapping(value = "/indextrend")
  public Object getIndexTrend(Integer num, Integer supplierid) {
    logger.debug(supplierid);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(supplierService.getIndexTrend(num, supplierid))
        .build();
  }

  /**
   * 获取国家用户画像
   *
   * @author: lingjian @Date: 2019/5/27 13:31
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  @PostMapping(value = "/indexcountry")
  public Object getIndexCountry(
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
      Integer supplierid) {
    logger.debug(supplierid);
    return MessageResult.builder()
        .code(1)
        .msg("Hello")
        .result(supplierService.getIndexCountry(startDate, endDate, supplierid))
        .build();
  }
}

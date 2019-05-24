package com.shoestp.mains.controllers.sellerdataview;

import javax.annotation.Resource;

import com.shoestp.mains.controllers.dataview.CountryController;
import com.shoestp.mains.service.sellerdataview.ProductService;
import com.shoestp.mains.service.sellerdataview.SupplierService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
}

package com.shoestp.mains.service.sellerdataview.impl;

import javax.annotation.Resource;

import com.shoestp.mains.dao.sellerdataview.product.ProductDao;
import com.shoestp.mains.dao.sellerdataview.supplier.SupplierDao;
import com.shoestp.mains.service.sellerdataview.ProductService;
import com.shoestp.mains.service.sellerdataview.SupplierService;

import org.springframework.stereotype.Service;

/**
 * @description: 商家后台商品-服务层实现类
 * @author: lingjian
 * @create: 2019/5/24 11:48
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Resource private ProductDao ProductDao;
}


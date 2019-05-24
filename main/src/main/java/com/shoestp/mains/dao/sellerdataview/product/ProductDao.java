package com.shoestp.mains.dao.sellerdataview.product;

import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.sellerdataview.product.SellerDataViewProduct;
import com.shoestp.mains.repositorys.sellerdataview.product.ProductRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 商家后台商品类数据层
 * @author: lingjian
 * @create: 2019/5/24 11:35
 */
@Repository
public class ProductDao extends BaseDao<SellerDataViewProduct> {

  @Resource private ProductRepository productRepository;

  @Override
  public SellerDataViewProduct find(SellerDataViewProduct sellerDataViewProduct) {
    return null;
  }

  @Override
  public SellerDataViewProduct findById(Integer id) {
    return null;
  }

  @Override
  public int update(SellerDataViewProduct sellerDataViewProduct) {
    return 0;
  }

  @Override
  public int updateByList(List<SellerDataViewProduct> list) {
    return 0;
  }

  @Override
  public int remove(SellerDataViewProduct sellerDataViewProduct) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

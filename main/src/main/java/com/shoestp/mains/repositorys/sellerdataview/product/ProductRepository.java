package com.shoestp.mains.repositorys.sellerdataview.product;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.sellerdataview.product.SellerDataViewProduct;

/**
 * @description: 商家后台商品类-实现JPA接口
 * @author: lingjian
 * @create: 2019/5/24 11:40
 */
public interface ProductRepository
    extends PagingAndSortingRepository<SellerDataViewProduct, Integer> {

  Optional<SellerDataViewProduct> findTopByOrderByCreateTimeDesc();
}

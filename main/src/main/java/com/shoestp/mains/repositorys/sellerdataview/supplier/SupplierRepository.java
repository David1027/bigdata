package com.shoestp.mains.repositorys.sellerdataview.supplier;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.sellerdataview.supplier.SellerDataViewSupplier;

/**
 * @description: 商家后台供应商类-实现JPA接口
 * @author: lingjian
 * @create: 2019/5/24 11:40
 */
public interface SupplierRepository
    extends PagingAndSortingRepository<SellerDataViewSupplier, Integer> {

  Optional<SellerDataViewSupplier> findTopByOrderByCreateTimeDesc();
}

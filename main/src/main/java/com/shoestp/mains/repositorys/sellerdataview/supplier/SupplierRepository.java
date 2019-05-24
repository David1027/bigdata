package com.shoestp.mains.repositorys.sellerdataview.supplier;

import com.shoestp.mains.entitys.sellerdataview.supplier.SellerDataViewSupplier;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 商家后台供应商类-实现JPA接口
 * @author: lingjian
 * @create: 2019/5/24 11:40
 */
public interface SupplierRepository
    extends PagingAndSortingRepository<SellerDataViewSupplier, Integer> {}

package com.shoestp.mains.dao.sellerdataview.supplier;

import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.sellerdataview.supplier.SellerDataViewSupplier;
import com.shoestp.mains.entitys.sellerdataview.user.SellerDataViewUser;
import com.shoestp.mains.repositorys.sellerdataview.supplier.SupplierRepository;
import com.shoestp.mains.repositorys.sellerdataview.user.UserRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 商家后台供应商总数据类数据层
 * @author: lingjian
 * @create: 2019/5/24 11:35
 */
@Repository
public class SupplierDao extends BaseDao<SellerDataViewSupplier> {

  @Resource private SupplierRepository supplierRepository;

  @Override
  public SellerDataViewSupplier find(SellerDataViewSupplier sellerDataViewSupplier) {
    return null;
  }

  @Override
  public SellerDataViewSupplier findById(Integer id) {
    return null;
  }

  @Override
  public int update(SellerDataViewSupplier sellerDataViewSupplier) {
    return 0;
  }

  @Override
  public int updateByList(List<SellerDataViewSupplier> list) {
    return 0;
  }

  @Override
  public int remove(SellerDataViewSupplier sellerDataViewSupplier) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

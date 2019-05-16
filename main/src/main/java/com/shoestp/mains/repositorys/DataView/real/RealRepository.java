package com.shoestp.mains.repositorys.DataView.real;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.DataView.real.DataViewReal;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 实时-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:23
 */
public interface RealRepository extends PagingAndSortingRepository<DataViewReal, Integer> {

  /**
   * 根据时间间隔获取所有记录
   *
   * @author: lingjian @Date: 2019/5/9 15:47
   * @param start
   * @param end
   * @return
   */
  List findAllByCreateTimeBetween(Date start, Date end);
}

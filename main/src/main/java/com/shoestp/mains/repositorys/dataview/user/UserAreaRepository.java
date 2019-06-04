package com.shoestp.mains.repositorys.dataview.user;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoestp.mains.entitys.dataview.user.DataViewUserArea;

/**
 * @description: 用户概况-实现JPA接口
 * @author: lingjian @Date: 2019/5/13 14:20
 */
public interface UserAreaRepository extends PagingAndSortingRepository<DataViewUserArea, Integer> {

  public Optional<DataViewUserArea> findTopByAreaOrderByCreateTimeDesc(String area);
}

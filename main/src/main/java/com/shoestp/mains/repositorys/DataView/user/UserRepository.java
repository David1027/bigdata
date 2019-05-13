package com.shoestp.mains.repositorys.DataView.user;

import java.util.Date;
import java.util.List;

import com.shoestp.mains.entitys.DataView.DataViewReal;
import com.shoestp.mains.entitys.DataView.user.DataViewUser;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 用户概况-实现JPA接口
 * @author: lingjian @Date: 2019/5/13 14:20
 */
public interface UserRepository extends PagingAndSortingRepository<DataViewUser, Integer> {
}

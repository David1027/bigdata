package com.shoestp.mains.repositorys.DataView.user;

import com.shoestp.mains.entitys.DataView.user.DataViewUser;
import com.shoestp.mains.entitys.DataView.user.DataViewUserSex;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 用户概况-实现JPA接口
 * @author: lingjian @Date: 2019/5/13 14:20
 */
public interface UserSexRepository extends PagingAndSortingRepository<DataViewUserSex, Integer> {
}

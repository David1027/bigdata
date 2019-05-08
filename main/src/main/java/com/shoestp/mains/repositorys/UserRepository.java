package com.shoestp.mains.repositorys;

import com.shoestp.mains.entitys.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/8 Time: 9:25 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {}

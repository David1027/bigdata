package com.shoestp.mains.repositorys.dataview.realcountry;

import com.shoestp.mains.entitys.dataview.country.DataViewCountry;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 国家-实现JPA接口
 * @author: lingjian @Date: 2019/5/9 10:40
 */
public interface RealCountryRepository extends PagingAndSortingRepository<DataViewCountry, Integer> {
    /**
     * 根据时间获取数据
     * @param start
     * @param end
     * @return List
     */
    List findAllByCreateTimeBetween(Date start, Date end);
}

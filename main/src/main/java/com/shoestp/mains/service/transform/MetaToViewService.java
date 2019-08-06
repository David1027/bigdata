package com.shoestp.mains.service.transform;

import java.util.Date;

import com.shoestp.mains.entitys.dataview.real.DataViewReal;

/**
 * @description: 源数据转化展示数据 - 服务层接口
 * @author: lingjian
 * @create: 2019/8/6 13:55
 */
public interface MetaToViewService {

    DataViewReal toCountry(Date start, Date end);
}

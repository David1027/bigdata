package com.shoestp.mains.service.sellerdataview;

import java.util.Date;
import java.util.List;

/**
 * @description: 商家后台用户-服务层接口
 * @author: lingjian
 * @create: 2019/5/24 11:46
 */
public interface UserService {

    List getRealVisitor(Date date, String country, String province, Integer supplierid);
}

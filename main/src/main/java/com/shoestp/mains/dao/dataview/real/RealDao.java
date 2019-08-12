package com.shoestp.mains.dao.dataview.real;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.dataview.real.DataViewReal;
import com.shoestp.mains.repositorys.dataview.real.RealRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @description: 国家-数据层
 * @author: lingjian @Date: 2019/5/9 10:50
 */
@Repository
public class RealDao extends BaseDao<DataViewReal> {

  @Resource private RealRepository realRepository;

  /**
   * 新增DataViewReal记录
   *
   * @param dataViewReal real对象
   */
  public void saveReal(DataViewReal dataViewReal) {
    realRepository.save(dataViewReal);
  }
}

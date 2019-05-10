package com.shoestp.mains.dao.DataView;

import java.util.List;

import javax.annotation.Resource;

import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.DataView.DataViewFlow;
import com.shoestp.mains.repositorys.DataView.FlowRepository;

import org.springframework.stereotype.Repository;

/**
 * @description: 流量-数据层
 * @author: lingjian @Date: 2019/5/9 10:12
 */
@Repository
public class FlowDao extends BaseDao<DataViewFlow> {

  @Resource private FlowRepository flowRepository;

  @Override
  public DataViewFlow find(DataViewFlow dataViewFlow) {
    return null;
  }

  @Override
  public DataViewFlow findById(Integer id) {
    return null;
  }

  @Override
  public int update(DataViewFlow dataViewFlow) {
    return 0;
  }

  @Override
  public int updateByList(List<DataViewFlow> list) {
    return 0;
  }

  @Override
  public int remove(DataViewFlow dataViewFlow) {
    return 0;
  }

  @Override
  public int removeByIds(Integer... id) {
    return 0;
  }
}

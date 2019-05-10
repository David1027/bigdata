package com.shoestp.mains.service.impl.DataView;

import javax.annotation.Resource;

import com.shoestp.mains.dao.DataView.FlowDao;
import com.shoestp.mains.service.DataView.FlowService;

import org.springframework.stereotype.Service;

/**
 * @description: 流量-服务层实现类
 * @author: lingjian @Date: 2019/5/9 10:13
 */
@Service
public class FlowServiceImpl implements FlowService {

  @Resource private FlowDao flowDao;
}

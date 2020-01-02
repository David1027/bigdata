package com.shoestp.mains.service.xwt.metadata;

import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;

/**
 * @description: 访问日志服务层接口
 * @author: lingjian
 * @create: 2019/12/27 10:13
 */
public interface XwtMetaAccessLogService {
  /**
   * 保存日志信息
   *
   * @param accessLog 访问日志对象
   */
  void save(XwtMetaAccessLog accessLog);
}

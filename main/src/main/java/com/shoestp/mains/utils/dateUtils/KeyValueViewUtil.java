package com.shoestp.mains.utils.dateUtils;

import com.shoestp.mains.views.DataView.utils.KeyValue;

/**
 * @description: key-value形式前端展示类
 * @author: lingjian
 * @create: 2019/5/20 13:50
 */
public final class KeyValueViewUtil {

  /**
   * 创建key-value对象
   *
   * @author: lingjian @Date: 2019/5/20 13:38
   * @param parameter
   * @param object
   * @return
   */
  public static KeyValue getFlowKeyValue(String parameter, Object object) {
    KeyValue keyValue = new KeyValue();
    keyValue.setKey(parameter);
    keyValue.setValue(object);
    return keyValue;
  }
}

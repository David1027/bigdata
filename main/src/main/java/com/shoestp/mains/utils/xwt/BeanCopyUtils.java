package com.shoestp.mains.utils.xwt;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;

/**
 * 反射类
 *
 * @author: lingjian
 * @create: 2020/1/3 9:37
 */
public class BeanCopyUtils extends BeanUtils {

  /** 复制属性 */
  public static <T> T copyProperties(Object source, Class<T> target) {
    if (source == null) {
      return null;
    }
    T obj = null;
    try {
      obj = target.newInstance();
      copyProperties(source, obj);
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return obj;
  }

  /** 复制属性并选择忽略 */
  public static <T> T copyPropertiesIgnore(
      Object source, Class<T> target, PropertiesUtils.Property<T, ?>... properties) {
    if (source == null) {
      return null;
    }
    T obj = null;
    try {
      obj = target.newInstance();
      copyProperties(
          source,
          obj,
          Stream.of(properties)
              .map(
                  bean -> {
                    return PropertiesUtils.getFunctionName(bean);
                  })
              .collect(Collectors.toList())
              .toArray(new String[] {}));
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return obj;
  }

  /** 复制属性并选择忽略 */
  public static <T> T copyPropertiesIgnore(
      Object source, T target, PropertiesUtils.Property<T, ?>... properties) {
    if (source == null) {
      return null;
    }
    copyProperties(
        source,
        target,
        Stream.of(properties)
            .map(
                bean -> {
                  return PropertiesUtils.getFunctionName(bean);
                })
            .collect(Collectors.toList())
            .toArray(new String[] {}));
    return target;
  }
}

package com.shoestp.mains.utils.xwt;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 对象属性工具类
 * @author: lingjian
 * @create: 2019/12/26 16:15
 */
public class FieldUtils {

  /**
   * 根据对象获取属性名数组
   *
   * @param o 对象
   * @return String[] 属性名数组
   */
  public static String[] getFiledName(Object o) {
    Field[] fields = o.getClass().getDeclaredFields();
    String[] fieldNames = new String[fields.length];
    for (int i = 0; i < fields.length; i++) {
      fieldNames[i] = fields[i].getName();
    }
    return fieldNames;
  }

  /**
   * 根据属性，获取get方法
   *
   * @param ob 对象
   * @param name 属性名
   * @return Object 对象
   * @throws Exception 异常
   */
  public static Object getValue(Object ob, String name) throws Exception {
    Method[] m = ob.getClass().getMethods();
    for (Method method : m) {
      if (("get" + name).toLowerCase().equals(method.getName().toLowerCase())) {
        return method.invoke(ob);
      }
    }
    return null;
  }

  /**
   * 根据属性，拿到set方法，并把值set到对象中
   *
   * @param obj 对象
   * @param clazz 对象的class
   * @param filedName 属性名
   * @param typeClass 类型
   * @param value 对象的值
   */
  public static void setValue(
      Object obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
    String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
    try {
      Method method = clazz.getDeclaredMethod(methodName, typeClass);
      method.invoke(obj, getClassTypeValue(typeClass, value));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 通过class类型获取对应类型的值
   *
   * @param typeClass 类型
   * @param value 对象
   * @return Object
   */
  private static Object getClassTypeValue(Class<?> typeClass, Object value) {
    if (typeClass == int.class || value instanceof Integer) {
      if (null == value) {
        return 0;
      }
      return value;
    } else if (typeClass == Integer.class) {
      if (null == value) {
        return 0;
      }
      return Integer.parseInt((String) value);
    } else if (typeClass == short.class) {
      if (null == value) {
        return 0;
      }
      return value;
    } else if (typeClass == byte.class) {
      if (null == value) {
        return 0;
      }
      return value;
    } else if (typeClass == double.class) {
      if (null == value) {
        return 0;
      }
      return value;
    } else if (typeClass == long.class) {
      if (null == value) {
        return 0;
      }
      return value;
    } else if (typeClass == String.class) {
      if (null == value) {
        return "";
      }
      return value;
    } else if (typeClass == boolean.class) {
      if (null == value) {
        return true;
      }
      return value;
    } else if (typeClass == BigDecimal.class) {
      if (null == value) {
        return new BigDecimal(0);
      }
      return new BigDecimal(value + "");
    } else if (typeClass == Date.class) {
      if (null == value) {
        return new Date();
      }
      return new Date(Long.parseLong((String) value));
    } else {
      return typeClass.cast(value);
    }
  }
}

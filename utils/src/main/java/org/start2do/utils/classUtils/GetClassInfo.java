package org.start2do.utils.classUtils;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.start2do.utils.MyStringUtils;
import org.start2do.utils.setBean.SetBean;
import org.start2do.utils.setBean.pojo.SetBeanFldInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GetClassInfo {
  private static ConcurrentHashMap<String, List<SetBeanFldInfo>> map;
  private static final Logger logger = LogManager.getLogger(GetClassInfo.class);
  @Setter private static Integer maxItemNumber = 1000;

  static {
    map = new ConcurrentHashMap<>();
  }

  public static void add(String key, List<SetBeanFldInfo> list) {
    if (map.size() > maxItemNumber) map.clear();
    map.put(key, list);
  }

  /**
   * @title getFld
   * @author Lijie HelloBox@outlook.com
   * @params [o, isLeft]
   * @returns java.util.List<org.start2do.utils.setBean.pojo.SetBeanFldInfo>
   * @updateTime 2019-05-03 19:10
   * @throws
   * @description isLef用于校验 方向。。 true SetBean打在source上
   */
  public static List<SetBeanFldInfo> getFld(Class source, Class target, boolean isLeft) {
    String key = source.getName() + "_" + isLeft + "_" + target.getName();
    List<SetBeanFldInfo> result = map.get(key);
    if (result != null) {
      logger.debug("使用缓存");
      return result;
    }
    result = isLeft ? leftgetFld(source) : rightgetFld(target);
    add(key, result);
    return result;
  }

  private static List<SetBeanFldInfo> leftgetFld(Class aClass) {
    List<SetBeanFldInfo> result = new ArrayList<>();
    for (Field declaredField : aClass.getDeclaredFields()) {
      SetBean setBean = declaredField.getAnnotation(SetBean.class);
      SetBeanFldInfo setBeanFldInfo = new SetBeanFldInfo();
      if (setBean != null) {
        if (setBean.skin()) continue;
        setBeanFldInfo.setName(setBean.alias());
        setBeanFldInfo.setSetMethod(setBean.getMethod());
        setBeanFldInfo.setGetMethod(setBean.setMethod());
        setBeanFldInfo.setAuto(false);
      }
      setBeanFldInfo.setType(declaredField.getType());
      if (MyStringUtils.isEmpty(setBeanFldInfo.getName())) {
        setBeanFldInfo.setName(declaredField.getName());
      }
      if (MyStringUtils.isEmpty(setBeanFldInfo.getSetMethod())) {
        if (setBean == null || MyStringUtils.isEmpty(setBean.alias())) {
          setBeanFldInfo.setSetMethod(
              ClassUtils.getSetMethodString("set", declaredField.getName()));
        } else {
          setBeanFldInfo.setSetMethod(ClassUtils.getSetMethodString("set", setBean.alias()));
        }
      }
      if (MyStringUtils.isEmpty(setBeanFldInfo.getGetMethod())) {
        if (!setBeanFldInfo.isAuto()) {
          setBeanFldInfo.setGetMethod(
              ClassUtils.getSetMethodString("get", declaredField.getName()));
        } else {
          setBeanFldInfo.setGetMethod(
              ClassUtils.getSetMethodString("get", declaredField.getName()));
        }
      }
      result.add(setBeanFldInfo);
    }
    return result;
  }

  private static List<SetBeanFldInfo> rightgetFld(Class aClass) {
    List<SetBeanFldInfo> result = new ArrayList<>();
    for (Field declaredField : aClass.getDeclaredFields()) {
      SetBean setBean = declaredField.getAnnotation(SetBean.class);
      SetBeanFldInfo setBeanFldInfo = new SetBeanFldInfo();
      if (setBean != null) {
        if (setBean.skin()) continue;
        setBeanFldInfo.setName(setBean.alias());
        setBeanFldInfo.setGetMethod(setBean.getMethod());
        setBeanFldInfo.setSetMethod(setBean.setMethod());
        setBeanFldInfo.setAuto(false);
      }
      setBeanFldInfo.setType(declaredField.getType());
      if (MyStringUtils.isEmpty(setBeanFldInfo.getName())) {
        setBeanFldInfo.setName(declaredField.getName());
      }
      if (MyStringUtils.isEmpty(setBeanFldInfo.getSetMethod())) {
        setBeanFldInfo.setSetMethod(ClassUtils.getSetMethodString("set", declaredField.getName()));
      }
      if (MyStringUtils.isEmpty(setBeanFldInfo.getGetMethod())) {
        setBeanFldInfo.setGetMethod(ClassUtils.getSetMethodString("get", setBeanFldInfo.getName()));
      }
      result.add(setBeanFldInfo);
    }
    return result;
  }

  private static String getMethod(Class aClass, String field, String prefix, Class type) {
    Method method;
    if (type == null) {
      method = getMethod(aClass, ClassUtils.getSetMethodString(prefix, field));
    } else {
      method = getMethod(aClass, ClassUtils.getSetMethodString(prefix, field), type);
    }
    if (method != null) return method.getName();
    return null;
  }

  public static Method getMethod(Class aClass, String methodName, Class... type) {
    try {
      if (type.length > 0) {
        return aClass.getDeclaredMethod(methodName, type);
      }
      return aClass.getDeclaredMethod(methodName);
    } catch (NoSuchMethodException e) {
      logger.info(
          String.format(
              "Class %s Not Found Method %s Params %s",
              aClass.getName(), methodName, Arrays.asList(type)));
    }
    return null;
  }
}

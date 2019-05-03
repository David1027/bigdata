package org.start2do.utils.classUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtils {
  private static final Logger logger = LogManager.getLogger(ClassUtils.class);

  /**
   * * 获取方法名称
   *
   * @author Lijie HelloBox@outlook.com
   * @date 2019/4/27 11:26
   */
  public static String getSetMethodString(String prefix, String fldName) {
    if (fldName == null || fldName.length() < 1) throw new NullPointerException();
    return prefix + Character.toUpperCase(fldName.charAt(0)) + fldName.substring(1);
  }

  public static Object Value(
      Object target, String methodName, Class type, Object value, boolean isSet) {
    Class aClass = target.getClass();
    try {
      Method method;
      if (isSet) {
        method = aClass.getMethod(methodName, type);
        method.invoke(target, value);
        return target;
      } else {
        method = aClass.getMethod(methodName);
        return method.invoke(target);
      }
    } catch (NoSuchMethodException e) {
      logger.info(
          String.format(
              "Class %s Not Found Method %s  %s",
              aClass.getName(), methodName, type != null ? type.getName() : null));
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.info(
          String.format(
              "Class %s Method %s  %s SetValue Faild",
              aClass.getName(), methodName, type.getName(), value));
    }
    return null;
  }

  public static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;

    try {
      cl = Thread.currentThread().getContextClassLoader();
    } catch (Throwable var3) {
    }

    if (cl == null) {
      cl = ClassUtils.class.getClassLoader();
      if (cl == null) {
        try {
          cl = ClassLoader.getSystemClassLoader();
        } catch (Throwable var2) {
        }
      }
    }

    return cl;
  }
}

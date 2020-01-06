package com.shoestp.mains.utils.xwt;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * 魔法值属性工具
 *
 * @author: lingjian
 * @create: 2020/1/3 9:38
 */
public class PropertiesUtils {
  /**
   * 获取如model::getId 对应的字符串 id
   */
  public static String getFunctionName(Property property) {
    try {
      Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
      declaredMethod.setAccessible(Boolean.TRUE);
      SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);
      String method = serializedLambda.getImplMethodName();
      String attr = null;
      if (method.startsWith("get")) {
        attr = method.substring(3);
      } else {
        attr = method.substring(2);
      }
      return toLowerCaseFirstOne(attr);
    } catch (ReflectiveOperationException var6) {
      throw new RuntimeException(var6);
    }
  }
  private static String toLowerCaseFirstOne(String s) {
    return Character.isLowerCase(s.charAt(0)) ? s
        : Character.toLowerCase(s.charAt(0)) + s.substring(1);
  }

  /**
   * 属性
   */
  public interface Property<T, R> extends Function<T, R>, Serializable {

  }
}

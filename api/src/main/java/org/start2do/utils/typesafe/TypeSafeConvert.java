package org.start2do.utils.typesafe;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface TypeSafeConvert<S, T> {
  default String getName() {
    ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
    Type[] types = type.getActualTypeArguments();
    if (types.length == 2) {
      return TypeSafeTools.getName(types[0], types[1]);
    }
    try {
      throw new Exception(String.format("请重写类：%s 的getName", this));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "Error";
  }

  default Class<T> getType() {
    ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
    Type[] types = type.getActualTypeArguments();
    return (Class<T>) types[1];
  }

  default String getScope() {
    TypeSafe objectSafe = getClass().getAnnotation(TypeSafe.class);
    if (objectSafe != null && objectSafe.scope() != null && objectSafe.scope().length() > 0)
      return objectSafe.scope();
    return null;
  }

  T convert(S source);
}

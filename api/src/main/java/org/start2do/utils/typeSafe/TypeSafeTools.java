package org.start2do.utils.typeSafe;

import java.lang.reflect.Type;

public class TypeSafeTools {
  public static String getName(Class s, Class t) {
    if (s != null && t != null) {
      return s.getName() + "_" + t.getName();
    }
    return null;
  }

  public static String getName(Type s, Type t) {
    if (s != null && t != null) {
      return s.getTypeName() + "_" + t.getTypeName();
    }
    return null;
  }

  public static String getClassKey(Class s, Class t) {
    return s.getSimpleName().charAt(0) > t.getSimpleName().charAt(0)
        ? t.getName() + "_" + s.getName()
        : s.getName() + "_" + t.getName();
  }

}

package org.start2do.utils.typeSafeUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.start2do.utils.MyStringUtils;
import org.start2do.utils.ResourceUtils;
import org.start2do.utils.typeSafe.TypeSafe;
import org.start2do.utils.typeSafe.TypeSafeConvert;
import org.start2do.utils.typeSafe.TypeSafeTools;
import org.start2do.utils.typeSafeUtils.pojo.ObjectSafeConfig;
import org.start2do.utils.typeSafeUtils.pojo.TypeSafeResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TypeSafeUtil {
  private static final Logger logger = LogManager.getLogger(TypeSafeUtil.class);

  private static ConcurrentHashMap<String, TypeSafeConvert> map;

  static {
    map = new ConcurrentHashMap<>();
    try {
      ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

      ObjectSafeConfig config =
          objectMapper.readValue(
              Files.newReader(
                  ResourceUtils.getFile("classpath:ObjectSafeConfig.yml"),
                  Charset.forName("utf-8")),
              ObjectSafeConfig.class);
      for (String aPackage : config.getPackages()) {
        addPackage(aPackage);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void addPackage(String packageString) {
    Reflections reflections = new Reflections(packageString);
    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TypeSafe.class);
    for (Class<?> aClass : annotated) {
      if (TypeSafeConvert.class.isAssignableFrom(aClass)) {
        try {
          TypeSafeConvert safeConvert = (TypeSafeConvert) aClass.newInstance();
          map.put(safeConvert.getName(), safeConvert);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }
  /**
   * @title check
   * @author Lijie HelloBox@outlook.com
   * @params [source, target]
   * @returns boolean
   * @updateTime 2019-05-03 18:51
   * @throws
   * @description 教养类型是否安全
   */
  public static boolean check(Class source, Class target, boolean isLeft) {
    if (source == null) return false;
    if (source.equals(target)) {
      return true;
    }
    Class isBaseType = baseType(source, target);
    if (isBaseType != null) {
      return true;
    }
    return isLeft
        ? map.get(TypeSafeTools.getName(target, source)) != null
        : map.get(TypeSafeTools.getName(source, target)) != null;
  }

  /**
   * @title check
   * @author Lijie HelloBox@outlook.com
   * @params [orginalClass, source, target]
   * @returns org.start2do.utils.typeSafeUtils.pojo.TypeSafeResult
   * @updateTime 2019-05-03 17:54
   * @throws
   * @description 类型安全检查 target 目标值 source 初始值 orginalClass哪个class 进来的 均已source的class 为主
   */
  public static <S, T> TypeSafeResult convert(Class orginalClass, S source, Class<T> target) {
    if (source == null) return null;
    TypeSafeResult result = new TypeSafeResult();
    Class sourceClass = source.getClass();
    if (sourceClass.equals(target)) {
      result.setSetType(target);
      result.setSetValue(source);
      return result;
    }
    Class isBaseType = baseType(sourceClass, target);
    if (isBaseType != null) {
      result.setSetType(isBaseType);
      result.setSetValue(source);
      return result;
    }

    TypeSafeConvert<S, T> safeConvert = map.get(TypeSafeTools.getName(source.getClass(), target));
    if (safeConvert == null) {
      logger.error(String.format("未能找到%s\r\n到%s的方法", sourceClass.getName(), target.getName()));
      return null;
    }
    /**
     * @author Lijie HelloBox@outlook.com
     * @date 2019-05-03 17:22 检查转换空间
     */
    if (orginalClass != null
        && safeConvert.getScope() != null
        && !checkScope(safeConvert.getScope(), orginalClass.getName())) {
      return null;
    }
    result.setSetType(target);
    result.setSetValue(safeConvert.convert(source));
    return result;
  }

  private static boolean checkScope(String scope, String className) {
    return MyStringUtils.isMatch(scope, className);
  }

  private static Class baseType(Class source, Class target) {
    if (target == long.class && source == Long.class) {
      return long.class;
    }
    if (target == int.class && source == Integer.class) {
      return int.class;
    }
    if (target == char.class && source == Character.class) {
      return char.class;
    }
    if (target == double.class && source == Double.class) {
      return double.class;
    }
    if (target == boolean.class && source == Boolean.class) {
      return boolean.class;
    }
    if (target == float.class && source == Float.class) {
      return float.class;
    }
    if (target == short.class && source == Short.class) {
      return short.class;
    }
    if (target == byte.class && source == Byte.class) {
      return byte.class;
    }
    if (target == long.class && source == Long.class) {
      return Long.class;
    }
    Class temp = source;
    source = target;
    target = temp;

    if (source == int.class && target == Integer.class) {
      return Integer.class;
    }
    if (source == char.class && target == Character.class) {
      return Character.class;
    }
    if (source == double.class && target == Double.class) {
      return Double.class;
    }
    if (source == boolean.class && target == Boolean.class) {
      return Double.class;
    }
    if (source == float.class && target == Float.class) {
      return Float.class;
    }
    if (source == short.class && target == Short.class) {
      return Short.class;
    }
    if (source == byte.class && target == Byte.class) {
      return Byte.class;
    }
    return null;
  }
}

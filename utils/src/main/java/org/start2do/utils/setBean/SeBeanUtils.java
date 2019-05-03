package org.start2do.utils.setBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.start2do.utils.MyStringUtils;
import org.start2do.utils.classUtils.ClassUtils;
import org.start2do.utils.classUtils.GetClassInfo;
import org.start2do.utils.setBean.pojo.SetBeanFldInfo;
import org.start2do.utils.typeSafeUtils.TypeSafeUtil;
import org.start2do.utils.typeSafeUtils.pojo.TypeSafeResult;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SeBeanUtils {
  private static final Logger logger = LogManager.getLogger(SeBeanUtils.class);

  public static <T, S> T set(
      S source,
      Class<T> targetClass,
      SetBefore<S, T> setBefore,
      SetRun<S, T> setRun,
      SetAfter<S, T> setAfter) {
    try {
      T target = targetClass.newInstance();
      if (setBefore != null) setBefore.before(source, target);
      if (source instanceof Map) {
        mapSet((Map) source, target, (SetRun<Map, T>) setRun);
      } else {
        ObjectSet(source, target, setRun);
      }
      if (setAfter != null) setAfter.after(source, target);
      return target;
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static <S, T> T ObjectSet(S source, T target, SetRun<S, T> setRun) {
    List<SetBeanFldInfo> fldList = null;
    boolean isLeft = false;
    SetBean setBean = target.getClass().getAnnotation(SetBean.class);
    if (setBean != null) {
      for (String s : setBean.scope()) {
        String name = source.getClass().getName();
        if (!MyStringUtils.isEmpty(s) && !MyStringUtils.isMatch(name, s)) {
          logger.error(String.format("%s 该类不在作用域内", name));
          return target;
        }
        fldList = GetClassInfo.getFld(target, isLeft);
      }
    } else {
      setBean = source.getClass().getAnnotation(SetBean.class);
      if (setBean != null) {
        for (String s : setBean.scope()) {
          String name = target.getClass().getName();
          if (MyStringUtils.isEmpty(s) && MyStringUtils.isMatch(name, s)) {
            logger.error(String.format("%s 该类不在作用域内", name));
            return target;
          }
        }
      }
      isLeft = true;
      fldList = GetClassInfo.getFld(source, isLeft);
    }
    Class sourceClass = isLeft ? target.getClass() : source.getClass();
    for (SetBeanFldInfo setBeanFldInfo : fldList) {
      try {
        Field sourceFld;
        sourceFld = sourceClass.getDeclaredField(setBeanFldInfo.getName());
        if (!TypeSafeUtil.check(sourceFld.getType(), setBeanFldInfo.getType(), isLeft)) continue;
        Object value = ClassUtils.Value(source, setBeanFldInfo.getGetMethod(), null, null, false);
        TypeSafeResult result =
            isLeft
                ? TypeSafeUtil.convert(source.getClass(), value, sourceFld.getType())
                : TypeSafeUtil.convert(source.getClass(), value, setBeanFldInfo.getType());
        if (result == null) continue;
        ClassUtils.Value(
            target, setBeanFldInfo.getSetMethod(), result.getSetType(), result.getSetValue(), true);
        if (setRun != null) setRun.run(source, target);
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
    return target;
  }

  public static <T, S> T set(S source, Class<T> target) {
    return set(source, target, null, null, null);
  }

  public static <T> T mapSet(Map map, T target, SetRun<Map, T> run) {
    for (SetBeanFldInfo setBeanFldInfo : GetClassInfo.getFld(target, false)) {
      Object value = map.get(setBeanFldInfo.getName());
      TypeSafeResult result = TypeSafeUtil.convert(null, value, setBeanFldInfo.getType());
      if (result == null) continue;
      ClassUtils.Value(
          target, setBeanFldInfo.getSetMethod(), result.getSetType(), result.getSetValue(), true);
      if (run != null) run.run(map, target);
    }
    return target;
  }
}

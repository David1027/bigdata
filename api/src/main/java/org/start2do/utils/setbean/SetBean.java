package org.start2do.utils.setbean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SetBean {
  /**
   * @title alias
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.String
   * @updateTime 2019-05-01 10:36
   * @throws
   * @description 别名
   */
  String alias() default "";

  /**
   * @title setMethod
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.String
   * @updateTime 2019-05-01 10:36
   * @throws
   * @description 设置方法
   */
  String setMethod() default "";

  /**
   * @title getMethod
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.String
   * @updateTime 2019-05-01 10:36
   * @throws
   * @description 获取方法
   */
  String getMethod() default "";

  /**
   * @title skin
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns boolean
   * @updateTime 2019-05-01 10:36
   * @throws
   * @description 是否跳过
   */
  boolean skin() default false;

  /**
   * @title ConvertCLass
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.Class
   * @updateTime 2019-05-01 10:36
   * @throws
   * @description 转换实现类
   */
  Class convertCLass() default Object.class;

  /**
   * @title Scope
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.String
   * @updateTime 2019-05-01 10:35
   * @throws
   * @description 作用域,当打在类上面的时候仅上面的类可以是用
   */
  String[] scope() default "";
}

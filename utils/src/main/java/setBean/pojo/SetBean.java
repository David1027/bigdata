package setBean.pojo;


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
  String alias();

  /**
   * @title setMethod
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.String
   * @updateTime 2019-05-01 10:36
   * @throws
   * @description 设置方法
   */
  String setMethod();

  /**
   * @title getMethod
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.String
   * @updateTime 2019-05-01 10:36
   * @throws
   * @description 获取方法
   */
  String getMethod();

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
  Class ConvertCLass();

  /**
   * @title Scope
   * @author Lijie HelloBox@outlook.com
   * @params []
   * @returns java.lang.String
   * @updateTime 2019-05-01 10:35
   * @throws
   * @description 作用域。。有些转换类型仅限于某些作用域
   */
  String Scope();
}

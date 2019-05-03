package org.start2do.utils.token;
/** Created by IntelliJ IDEA. User: Lijie HelloBox@outlook.com Date: 2019/4/24 Time: 10:16 */

import lombok.Data;

import java.util.Date;

@Data
public class TokenPojo {
  /**
   * @Description: 用户ID
   *
   * @date 2019/4/23 14:06
   * @author Lijie HelloBox@outlook.com
   */
  private Integer id;
  /**
   * @Description: 用户组
   *
   * @date 2019/4/23 14:07
   * @author Lijie HelloBox@outlook.com
   */
  private String role;

  /**
   * @Description: 用户类型
   *
   * @date 2019/4/23 14:08
   * @author Lijie HelloBox@outlook.com
   */
  private Integer user_type;
  /**
   * @Description: 有效期至 登陆时间 yyyy-MM-dd
   *
   * @date 2019/4/23 14:08
   * @author Lijie HelloBox@outlook.com
   */
  private String valid_date;
  /**
   * @Description: 语言
   *
   * @date 2019/4/23 14:08
   * @author Lijie HelloBox@outlook.com
   */
  private String lang;
  /**
   * @Description: 登陆时间 yyyy-MM-dd
   *
   * @date 2019/4/23 14:09
   * @author Lijie HelloBox@outlook.com
   */
  private Date login_date;

  /**
   * @Description: 请求UA
   *
   * @date 2019/4/23 14:09
   * @author Lijie HelloBox@outlook.com
   */
  private String UA;
  /**
   * @Description: 移动设备IMME
   *
   * @date 2019/4/23 14:09
   * @author Lijie HelloBox@outlook.com
   */
  private String IMME;
  /**
   * @Description: 校验码
   *
   * @date 2019/4/23 14:08
   * @author Lijie HelloBox@outlook.com
   */
  private String sign;

  /**
   * @Description: 获取ASE加密密文
   *
   * @date 2019/4/23 16:10
   * @author Lijie HelloBox@outlook.com
   */
}

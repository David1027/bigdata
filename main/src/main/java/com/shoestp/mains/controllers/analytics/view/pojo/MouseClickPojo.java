package com.shoestp.mains.controllers.analytics.view.pojo;

import lombok.Data;

import java.util.Date;

/** Created by IntelliJ IDEA. User: lijie@shoestp.cn Date: 2019/5/20 Time: 14:05 */
@Data
public class MouseClickPojo {
  private Integer x;
  private Integer y;
  private ScrollPojo scroll;
  private Date date;
}

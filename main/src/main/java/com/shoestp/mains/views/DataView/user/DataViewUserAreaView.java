package com.shoestp.mains.views.DataView.user;

import javax.persistence.Column;
import lombok.Data;

/**
 * @description: 用户性别前端展示类
 * @author: lingjian @Date: 2019/5/13 16:02
 */
@Data
public class DataViewUserAreaView {
  /** 地域名称 */
  @Column(name = "area")
  private String area;
  /** 地域访客数 */
  @Column(name = "area_count")
  private Integer areaCount;
}

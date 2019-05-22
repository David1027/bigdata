package com.shoestp.mains.entitys.DataView.user;

import java.util.Date;

import javax.persistence.*;

import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import com.shoestp.mains.enums.user.VisitorTypeEnum;

import lombok.Data;

/**
 * @description: 用户表
 * @author: lingjian
 * @create: 2019/5/8 9:02
 */
@Data
@Entity
@Table(name = "data_view_user_area")
public class DataViewUserArea {
  @Id @GeneratedValue private Integer id;
  /** 地域名称 */
  @Column(name = "area")
  private String area;
  /** 地域访客人数 */
  @Column(name = "area_count")
  private Integer areaCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

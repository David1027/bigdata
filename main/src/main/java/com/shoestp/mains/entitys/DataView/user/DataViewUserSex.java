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
@Table(name = "data_view_user_sex")
public class DataViewUserSex {
  @Id @GeneratedValue private Integer id;
  /** 男性人数 */
  @Column(name = "man_sex_count")
  private Integer manSexCount;
  /** 女性人数 */
  @Column(name = "woman_sex_count")
  private Integer womanSexCount;
  /** 未知人数 */
  @Column(name = "unknown_sex_count")
  private Integer unknownSexCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

package com.shoestp.mains.entitys.dataview.user;

import com.shoestp.mains.enums.user.SexEnum;
import java.util.Date;
import javax.persistence.*;
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
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
  /** 性别：MAN-男，WOMAN-女，UNKNOWN-未知 */
  @Enumerated(EnumType.STRING)
  @Column(name = "sex")
  private SexEnum sex;
  /** 性别人数 */
  @Column(name = "sex_count")
  private Integer sexCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}
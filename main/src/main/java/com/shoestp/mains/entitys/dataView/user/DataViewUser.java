package com.shoestp.mains.entitys.dataView.user;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @description: 用户表
 * @author: lingjian
 * @create: 2019/5/8 9:02
 */
@Data
@Entity
@Table(name = "data_view_user")
public class DataViewUser {
  @Id @GeneratedValue private Integer id;
  /** 总访客数 */
  @Column(name = "visitor_count")
  private Integer visitorCount;
  /** 新用户数 */
  @Column(name = "new_visitor_count")
  private Integer newVisitorCount;
  /** 老用户数 */
  @Column(name = "old_visitor_count")
  private Integer oldVisitorCount;
  /** 总注册量 */
  @Column(name = "register_count")
  private Integer registerCount;
  /** 采购商数量 */
  @Column(name = "purchase_count")
  private Integer purchaseCount;
  /** 供应商数量 */
  @Column(name = "supplier_count")
  private Integer supplierCount;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

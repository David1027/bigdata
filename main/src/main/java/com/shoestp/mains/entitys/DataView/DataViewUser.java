package com.shoestp.mains.entitys.DataView;

import com.shoestp.mains.enums.user.RegisterTypeEnum;
import com.shoestp.mains.enums.user.SexEnum;
import com.shoestp.mains.enums.user.VisitorTypeEnum;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 用户表
 * @author: lingjian
 * @create: 2019/5/8 9:02
 */
@Data
@Entity
@Builder
@Table(name = "data_view_user")
public class DataViewUser {
  @Id @GeneratedValue private Integer id;
  /** 访客类型：NEWUSER-新用户，OLDUSER-老用户 */
  @Enumerated(EnumType.STRING)
  @Column(name = "visitor_type")
  private VisitorTypeEnum visitorType;
  /** 注册类型：PURCHASE-采购商，SUPPLIER-供应商 */
  @Enumerated(EnumType.STRING)
  @Column(name = "register_type")
  private RegisterTypeEnum registerType;
  /** 性别类型：MAN-男，WOMAN-女，UNKNOWN-未知 */
  @Enumerated(EnumType.STRING)
  @Column(name = "sex")
  private SexEnum sex;
  /** 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

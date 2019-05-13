package com.shoestp.mains.entitys.MetaData;

import com.shoestp.mains.enums.inquiry.InquiryTypeEnum;
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

/** * 从鞋帽港推送过来的数据 */
@Data
@Entity
@Table(name = "inquiry_info")
public class InquiryInfo {
  @Id @GeneratedValue private Integer id;
  /** * 询盘类型 */
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private InquiryTypeEnum type;
  /** * 询盘的ID */
  @Column(name = "inquiry_id")
  private Integer inquiryId;
  /** * 创建时间 */
  @Column(name = "create_time")
  private Date createTime;
}

package com.shoestp.mains.entitys.dataview.inquirynew;

import com.shoestp.mains.entitys.dataview.inquirynew.enums.InquiryTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Data view inquiry.
 *
 * <p>*
 *
 * @author lijie
 * @date 2019 /09/12
 * @since
 * @description: 询盘表
 * @author: lingjian
 * @create: 2019 /5/8 9:43
 */
@Data
@Entity
@Table(name = "data_view_inquiry_new")
public class DataViewInquiryNew {
  /** The Id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** The Name. 名称 */
  @Column(name = "name")
  private String name;

  /** The Image. 显示的图片*/
  @Column(name = "image")
  private String image;

  /** The Type. */
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private InquiryTypeEnum type;
  /** 访客数 */
  @Column(name = "pv")
  private Long pv;
  /** 询盘数 */
  @Column(name = "uv")
  private Long uv;
  /** 询盘人数 */
  @Column(name = "inquiry_count")
  private Long inquiryCount;
  /** 统计时间,即该记录生成的时间 */
  @Column(name = "statistical_time")
  private LocalDateTime statisticalTime;
}

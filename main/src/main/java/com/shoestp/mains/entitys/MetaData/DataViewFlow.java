package com.shoestp.mains.entitys.MetaData;

import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

/**
 * @description: 流量表
 * @author: lingjian
 * @create: 2019/5/8 9:51
 */
@Data
@Entity
@Builder
public class DataViewFlow {
  @Id @GeneratedValue private Integer id;
  /** 设备类型：PC-电脑端，WAP-移动端 */
  @Enumerated(EnumType.STRING)
  private DeviceTypeEnum deviceType;
  /** 来源类型：INTERVIEW-自主访问，GOOGLE-谷歌，BAIDU-百度，OTHER-其他 */
  @Enumerated(EnumType.STRING)
  private SourceTypeEnum sourceType;
  /** 来源渠道 */
  private String sourcePage;
  /** 访问具体页面 */
  private String accessPage;
  /** 访客数 */
  private Integer visitorCount;
  /** 浏览量 */
  private Integer viewCount;
  /** 点击率 */
  private Double clickRate;
  /** 跳失率 */
  private Double jumpRate;
  /** 平均停留时长 */
  private Double averageStayTime;
  /** 创建时间 */
  private Date createTime;
}

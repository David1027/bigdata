package com.shoestp.mains.views.sellerdataview.user;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 实时访客前端展示类
 * @author: lingjian
 * @create: 2019/5/24 14:50
 */
@Data
public class RealVisitorView {
  /** 供应商ID-usr_main表的ID */
  private Integer supplierId;
  /** 国家 */
  private String country;
  /** 省份 */
  private String province;
  /** 访客名称 */
  private String visitorName;
  /** 访问页面数 */
  private Integer pageCount;
  /** 询盘数 */
  private Integer inquiryCount;
  /** 用户关键词 */
  private String keyWords;
  /** 社交媒体 */
  private Boolean facebook;
  /** 社交媒体 */
  private Boolean google;
  /** 社交媒体 */
  private Boolean linkedin;
  /** 社交媒体 */
  private Boolean twitter;
  /** 创建时间 */
  private String createTime;
}

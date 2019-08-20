package com.shoestp.mains.views.dataview.real;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoestp.mains.entitys.metadata.enums.RegisterTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import lombok.Data;

/**
 * @description: 实时访客前端展示类
 * @author: lingjian
 * @create: 2019/8/19 16:42
 */
@Data
public class RealVisitorView {
  /** 编号 */
  private Integer id;
  /** * 页面URL */
  private String url;
  /** 流量来源 */
  private SourceTypeEnum sourceType;
  /** 流量来源名称 */
  private String sourceTypeName;
  /** * 访客来自于 */
  private String referer;
  /** 访客位置id */
  private Integer countryId;
  /** 访客位置名称 */
  private String countryName;
  /** 访客关联UserInfo表访客类型 */
  private RegisterTypeEnum type;
  /** 访客类型名称 */
  private String typeName;
  /** 创建时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
}

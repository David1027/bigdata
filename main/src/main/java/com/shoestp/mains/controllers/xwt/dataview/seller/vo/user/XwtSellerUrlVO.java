package com.shoestp.mains.controllers.xwt.dataview.seller.vo.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoestp.mains.enums.xwt.OProductRoleEnum;

import lombok.Data;

/**
 * @description: 访客访问页面VO
 * @author: lingjian
 * @create: 2020/1/10 14:33
 */
@Data
public class XwtSellerUrlVO {

  /** url */
  private String url;
  /** 创建时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
}

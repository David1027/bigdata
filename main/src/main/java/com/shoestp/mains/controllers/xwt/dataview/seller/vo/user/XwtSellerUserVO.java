package com.shoestp.mains.controllers.xwt.dataview.seller.vo.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shoestp.mains.enums.xwt.OProductRoleEnum;

import lombok.Data;

/**
 * @description: 实时访客VO
 * @author: lingjian
 * @create: 2020/1/10 14:33
 */
@Data
public class XwtSellerUserVO {

  /** 店铺产品角色 */
  private OProductRoleEnum productRole;
  /** 店铺id */
  private Integer enterpriseId;
  /** url */
  private String url;
  /** 用户信息表 */
  private Integer memberInfoId;
  /** 用户名称 */
  private String memberName;
  /** 是否新用户 */
  private Boolean isNewMember;
  /** 国家 */
  private Integer countryId;
  /** 国家名称 */
  private String countryName;
  /** 省份-中国 */
  private Integer provinceId;
  /** 省份名称 */
  private String provinceName;
  /** 访问页面数 */
  private Long viewCount;
  /** 创建时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
}

package com.shoestp.mains.views.DataView.real;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shoestp.mains.utils.dateUtils.CustomDoubleSerialize;
import lombok.Data;

/**
 * @description: 实时前端展示类
 * @author: lingjian
 * @create: 2019/5/8 9:40
 */
@Data
public class IndexView {
  /** 跳失率 */
  @JsonSerialize(using = CustomDoubleSerialize.class)
  private Double jumpRate;
}

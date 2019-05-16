package com.shoestp.mains.schedulers.googleApi;

import com.shoestp.mains.enums.flow.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;

import lombok.Data;

@Data
public class DataView {
  private DeviceTypeEnum deviceType;
  private SourceTypeEnum sourceType;
  private String sourcePage;
  private Integer visitorCount;
}
package com.shoestp.mains.views.dataview.metadata;

import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum;
import com.shoestp.mains.enums.flow.SourceTypeEnum;
import lombok.Data;

@Data
public class DataView {
  private DeviceTypeEnum deviceType;
  private SourceTypeEnum sourceType;
  private String sourcePage;
  private Integer visitorCount;
}

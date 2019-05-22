package com.shoestp.mains.trigger.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TriggerPojo {
  private String key;
  private String secret;
}

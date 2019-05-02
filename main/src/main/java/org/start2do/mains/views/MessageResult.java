package org.start2do.mains.views;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResult {
  private Integer code;
  private String msg;
  private String result;
}

package org.start2do.utils.tokenutils.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckTokenPojo {
  private String UA;
  private String IMME;
}

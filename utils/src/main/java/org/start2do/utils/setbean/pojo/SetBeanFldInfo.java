package org.start2do.utils.setbean.pojo;

import lombok.Data;

@Data
public class SetBeanFldInfo {
  private String name;
  private Class type;
  private String setMethod;
  private String getMethod;
  private boolean auto = true;
}

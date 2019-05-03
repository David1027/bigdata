package org.start2do.utils.typeSafeUtils.pojo;

import lombok.Data;

@Data
public class TypeSafeResult {
  private Class setType;
  private Object setValue;
}

package org.start2do.utils.typesafeutils.pojo;

import lombok.Data;

@Data
public class TypeSafeResult {
  private Class setType;
  private Object setValue;
}

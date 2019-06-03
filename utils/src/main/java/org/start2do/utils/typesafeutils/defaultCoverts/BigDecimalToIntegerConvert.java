package org.start2do.utils.typesafeutils.defaultcoverts;

import org.start2do.utils.typeSafe.TypeSafe;
import org.start2do.utils.typeSafe.TypeSafeConvert;

import java.math.BigDecimal;

@TypeSafe
public class BigDecimalToIntegerConvert implements TypeSafeConvert<BigDecimal, Integer> {

  @Override
  public Integer convert(BigDecimal source) {
    return source.intValue();
  }
}

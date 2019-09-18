package org.start2do.utils.typesafeutils.defaultcoverts;

import org.start2do.utils.typesafe.TypeSafe;
import org.start2do.utils.typesafe.TypeSafeConvert;

import java.math.BigDecimal;

@TypeSafe
public class BigDecimalToLongConvert implements TypeSafeConvert<BigDecimal, Long> {

  @Override
  public Long convert(BigDecimal source) {
    return source.longValue();
  }
}

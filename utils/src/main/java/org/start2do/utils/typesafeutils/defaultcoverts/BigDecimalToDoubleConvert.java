package org.start2do.utils.typesafeutils.defaultcoverts;

import org.start2do.utils.typesafe.TypeSafe;
import org.start2do.utils.typesafe.TypeSafeConvert;

import java.math.BigDecimal;

@TypeSafe
public class BigDecimalToDoubleConvert implements TypeSafeConvert<BigDecimal, Double> {

  @Override
  public Double convert(BigDecimal source) {
    return source.doubleValue();
  }
}

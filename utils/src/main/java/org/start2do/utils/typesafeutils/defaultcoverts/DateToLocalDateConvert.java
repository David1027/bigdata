package org.start2do.utils.typesafeutils.defaultcoverts;

import org.start2do.utils.typesafe.TypeSafe;
import org.start2do.utils.typesafe.TypeSafeConvert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@TypeSafe
public class DateToLocalDateConvert implements TypeSafeConvert<Date, LocalDate> {

  @Override
  public LocalDate convert(Date source) {
    return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
